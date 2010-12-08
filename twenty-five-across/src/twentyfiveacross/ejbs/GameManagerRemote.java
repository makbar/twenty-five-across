package twentyfiveacross.ejbs;
import java.util.Collection;
import java.util.Map;

import javax.ejb.Remote;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.ejb.Stateless;

import crosswordsage.Crossword;
//import crosswordsage.SolveState;

@Remote
public interface GameManagerRemote {
	public String[] listGames() throws Exception;
	
	public int newGame() throws Exception;
	
    public Crossword getCrossword(int gameId) throws Exception;
    
    //public void loadGames();
    
    public Collection<SquareUnit> getSquares(int gameId);
    
    public Boolean setLetter(int gameId, int x, int y, String letter, String username) throws Exception;

	public Map<String, String> getSolveState(int gameId);
	
	//public int getSolveStateSize(int gameId);

	//public String getLetter(int gameId, int xpos, int ypos);

	String getSolveStateString(int gameId);

	Map<String, String> getSolvedSquares(int gameId);
	
	public void incFinished(int currentGame);

	public int finishedUsers(int currentGame);

	public void updateGameState(int currentGame);
}
