package twentyfiveacross.ejbs;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.ejb.*;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.ejb.Stateful;

import org.glassfish.gmbal.logex.StackTrace;

import crosswordsage.Crossword;
import crosswordsage.SolveState;

@Singleton(name="GameManager")
public class GameManager implements GameManagerRemote {
	// Allows users to look up games, create a new one
	
	private Hashtable<Integer, Game> games;
	
	public GameManager() {
		games = new Hashtable<Integer, Game>();
	}
	
	public String[] listGames() {
		// Probably need locking in here
		Enumeration <Game> gameEnum = games.elements();
		String[] gamesArr = new String[games.size()];
		
		int c=0;
		for(; gameEnum.hasMoreElements();) {
			gamesArr[c++] = "" + gameEnum.nextElement().gameId;
		}
		System.err.println("Oh hi, I have " + gamesArr.length + " games!\n" + gamesArr.toString());
		return gamesArr;
	}
	
	public int newGame() {
		Game g = new Game();
		games.put(g.gameId, g);
		System.err.println("Creating new game, I have " + games.size() + " games!\n" + games.toString());
		return g.gameId;
	}

	@Override
	public Crossword getCrossword(int gameId) {
		Game g = games.get(gameId);
		return g.getCrossword();
	}

	@Override
	public SolveState getSolveState(int gameId) {
		Game g = games.get(gameId);
		return g.getSolveState();
	}

	@Override
	public Boolean setLetter(int gameId, int x, int y, String letter) {
		Game g = games.get(gameId);
		return g.setLetter(x, y, letter);
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
		