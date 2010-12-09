/**
 * All game-related tasks, including allowing users
 * to look up games, create a new one, etc.
 */
package twentyfiveacross.ejbs;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import crosswordsage.Crossword;

@Singleton
public class GameManager implements GameManagerRemote {
	
	
	@PersistenceContext(name="persistence_ctx")
	EntityManager em;
	
	public String[] listGames() throws Exception {
		List games = em.createNamedQuery("findAllGames").getResultList();
		if(games.size() == 0)
			return null;
		String[] gamesArr = new String[games.size()];
		int c=0;
		for(Iterator iter = games.iterator(); iter.hasNext();) {
			Game game = (Game) iter.next();
			gamesArr[c++] = Integer.toString(game.getGameId());
		}
		return gamesArr;
	}
	
	public int newGame() throws Exception {
		
		CWBean cwb = (CWBean)em.createNamedQuery("getCw").setMaxResults(1).getSingleResult();
		em.createNamedQuery("incCount")
			.setParameter("count", cwb.getCount() + 1)
			.setParameter("id", cwb.getCwId())
			.executeUpdate();
		Game g = new Game();
		byte[] byteObj = cwb.getCw();
		Crossword cw = getCw(byteObj);
		int width = cw.getWidth();
		int height = cw.getHeight();
		ArrayList squares = new ArrayList();
		for (int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				SquareUnit square = new SquareUnit();
				square.setGame(g);
				square.setPosx(i);
				square.setPosy(j);
				squares.add(square);
				em.persist(square);
			}
		}
		g.setSquares(squares);
		g.setAccessType(2);
		g.setCw(byteObj);
		em.persist(g);
		em.flush();
		return g.getGameId();
	}
	
	@Override
	public Crossword getCrossword(int gameId) throws Exception{
		if(gameId <= 0)
			return null;
		Game g = em.find(Game.class, gameId);
		if(g == null || g.getStatus() != 2)
			return null;
		byte[] byteObject = g.getCw();
		return getCw(byteObject);
	}
	
	public Crossword getCw(byte[] byteObject) throws Exception{
		Crossword cw = null;
		ByteArrayInputStream bais;
		ObjectInputStream in;
			
		bais = new ByteArrayInputStream(byteObject);
		in = new ObjectInputStream(bais);
		cw = (Crossword) in.readObject();
		in.close();
		return cw;
	}

	@Override
	public Collection<SquareUnit> getSquares(int gameId) {
		if(gameId <= 0)
			return null;
		Game g = em.find(Game.class, gameId);
		if(g == null)// || g.getStatus() != 2)
			return null;
		return g.getSquares();
	}
	

	@Override
	public Boolean setLetter(int gameId, int x, int y, String letter, String username) throws Exception {
		if(!letter.matches("[a-zA-Z ]")) {
			System.err.println("User entered bad character: " + (int) letter.charAt(0));
			return false;
		}
		if(x <= 0 || y <= 0) {
			return false;
		}
		if(gameId <= 0)
			return null;
		Game g = em.find(Game.class, gameId);
		if(g == null)
			return null;
		try {
			Crossword cw = getCw(g.getCw());
			if(x > cw.getWidth() || y > cw.getHeight())
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(g.getStatus() == 1) {/*Game finished */
			System.err.println("setLetter called on a finished puzzle");
			return false;
		}
		
		em.createNamedQuery("setSquare")
		.setParameter("gameId", gameId)
		.setParameter("letter", letter)
		.setParameter("x", x)
		.setParameter("y", y)
		.setParameter("username", username)
		.executeUpdate();
		em.flush();
		
		return true;
	}

	@Override
	public String getSolveStateString(int gameId) {
		Collection<SquareUnit> squares = getSquares(gameId);
		String buf = "";
		if(squares == null)
			return buf;
		for(Iterator iter = squares.iterator(); iter.hasNext();) {
			SquareUnit s = (SquareUnit)iter.next();
			buf = buf + "\nx: " + s.getPosx() + " y: " + s.getPosy() + " l: " + s.getLetter();
		}
		return buf;
	}
	
	@Override
	public Map<String, String> getSolveState(int gameId) {
		Collection<SquareUnit> squares = getSquares(gameId);
		Map<String, String> ss = new HashMap<String, String>();
		if(squares == null)
			return ss;
		for(Iterator iter = squares.iterator(); iter.hasNext();) {
			SquareUnit s = (SquareUnit)iter.next();
			String key = s.getPosx() + " " + s.getPosy();
			ss.put(key, s.getLetter());
		}
		return ss;
	}
	
	@Override
	public Map<String, String> getSolvedSquares(int gameId) {
		Collection<SquareUnit> squares = getSquares(gameId);
		
		Map<String, String> ss = new HashMap<String, String>();
		if(squares == null)
			return ss;
		for(Iterator iter = squares.iterator(); iter.hasNext();) {
			SquareUnit s = (SquareUnit)iter.next();
			String key = s.getPosx() + " " + s.getPosy();
			ss.put(key, s.getUsername());
		}
		return ss;
	}
/*
	@Override
	public int getSolveStateSize(int gameId) {
		Collection<SquareUnit> squares = getSquares(gameId);
		return squares.size();
	}

	@Override
	public String getLetter(int gameId, int xpos, int ypos) {
		Collection<SquareUnit> ss = getSquares(gameId);
		for(Iterator iter = ss.iterator(); iter.hasNext();) {
			SquareUnit s = (SquareUnit)iter.next();
			if(s.getPosx() == xpos && s.getPosy() == ypos) {
				return s.getLetter();
			}			
		}
		return "";
	}*/
	
	@Override
	public int finishedUsers(int gameId) {
		Game g = em.find(Game.class, gameId);
		return g.getFinished();
	}

	@Override
	public void incFinished(int gameId) {
		Game g = em.find(Game.class, gameId);
		g.setFinished(g.getFinished() + 1);
		em.persist(g);
		em.flush();
		
	}

	@Override
	public void updateGameState(int gameId) {
		Game g = em.find(Game.class, gameId);
		g.setStatus(1);
		em.persist(g);
		em.flush();
		
	}

	@Override
	public void updateUserRating(String bestUser, int bestCount) {
		
		
	}
}