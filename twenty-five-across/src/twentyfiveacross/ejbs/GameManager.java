package twentyfiveacross.ejbs;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.ejb.*;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Stateful;

import org.glassfish.gmbal.logex.StackTrace;

import crosswordsage.Crossword;
import crosswordsage.SolveState.SquareState;

@Singleton(name="GameManager", mappedName="ejb/SimpleBeanJNDI")
public class GameManager implements GameManagerRemote {
	// Allows users to look up games, create a new one
	
	@PersistenceContext(name="persistence_ctx")
	EntityManager em;
	
	private Hashtable<Integer, Game> games;
	
	/*public GameManager() {
		games = new Hashtable<Integer, Game>();
	}*/
	
	public String[] listGames() {
		List games = em.createNamedQuery("findAllGames").getResultList();
		String[] gamesArr = new String[games.size()];
		int c=0;
		for(Iterator iter = games.iterator(); iter.hasNext();) {
			Game game = (Game) iter.next();
			gamesArr[c++] = " "	+ game.getGameId();
		}
		return gamesArr;
		
		/*// Probably need locking in here
		Enumeration <Game> gameEnum = games.elements();
		String[] gamesArr = new String[games.size()];
		
		int c=0;
		for(; gameEnum.hasMoreElements();) {
			gamesArr[c++] = "" + gameEnum.nextElement().gameId;
		}
		System.err.println("Oh hi, I have " + gamesArr.length + " games!\n" + gamesArr.toString());
		return gamesArr;*/
	}
	
	public int newGame() {
		
		Game g = (Game)em.createNamedQuery("getNewGame").getSingleResult();
		Crossword cw = getCrossword(g.getGameId());
		int width = cw.getWidth();
		int height = cw.getHeight();
		ArrayList squares = new ArrayList();
		for (int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				SquareUnit square = new SquareUnit();
				square.setGame(g);
				square.setPosx(width);
				square.setPosy(height);
				squares.add(square);
				em.persist(square);
			}
		}
		g.setSquares(squares);
		g.setAccessType(2);
		//ss = new SolveState(cw.getWidth(), cw.getHeight());
		em.persist(g);
		return g.getGameId();
		
		/*games.put(g.gameId, g);
		System.err.println("Creating new game, I have " + games.size() + " games!\n" + games.toString());
		return g.gameId;*/
	}

	@Override
	public Crossword getCrossword(int gameId) {
		Crossword cw = null;
		Game g = em.find(Game.class, gameId);
		byte[] byteObject = g.getCw();
		ByteArrayInputStream bais;
		ObjectInputStream in;
		try {
			bais = new ByteArrayInputStream(byteObject);
			in = new ObjectInputStream(bais);
			cw = (Crossword) in.readObject();
			in.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return cw;
	}

	@Override
	public Collection<SquareUnit> getSolveState(int gameId) {
		Game g = em.find(Game.class, gameId);
		return g.getSquares();
	}

	@Override
	public Boolean setLetter(int gameId, int x, int y, String letter) {
		if(!letter.matches("[a-zA-Z]")) {
			System.err.println("User entered bad character: " + (int) letter.charAt(0));
			return false;
		}
		Game g = em.find(Game.class, gameId);
		if(g.getStatus() == 1) {/*Game finished */
			System.err.println("setLetter called on a finished puzzle");
			return false;
		}
		em.createNamedQuery("setSquare")
		.setParameter("gameId", gameId)
		.setParameter("letter", letter)
		.setParameter("posx", x)
		.setParameter("posy", y);
		
		return true;
	}

	@Override
	public String getSolveStateString(int gameId) {
		Collection<SquareUnit> squares = getSolveState(gameId);
		String buf = "";
		for(Iterator iter = squares.iterator(); iter.hasNext();) {
			SquareUnit s = (SquareUnit)iter.next();
			buf = buf + "\nx: " + s.getPosx() + " y: " + s.getPosy() + " l: " + s.getLetter();
		}
		return buf;
	}
}


/*
Some code that was supposed to return an interface but doesn't seem to?
		// actually, we want to create a new game instance on the server side, then
		// return an interface to the game?
		GameRemote interfaceRef = null;
		
		Class remoteInterface = null;
        for(Class interface_: Game.class.getInterfaces()) {
            if(interface_.isAnnotationPresent(Remote.class))
                remoteInterface = interface_;
        }
        if(remoteInterface == null)
            throw new  IllegalArgumentException(
                "Game requires a remote interface.  This *really* shouldn't happen");
		Stateful e = Game.class.getAnnotation(Stateful.class);
		String jndiName = e.mappedName();
		try {
			interfaceRef = (GameRemote) ctx.lookup(jndiName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		games.add(interfaceRef);
		
		return interfaceRef;
		*/
		