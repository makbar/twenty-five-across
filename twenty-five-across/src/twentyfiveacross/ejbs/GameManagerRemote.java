package twentyfiveacross.ejbs;
import java.util.Collection;

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
	public String[] listGames();
	
	public int newGame();
	
    public Crossword getCrossword(int gameId);
    
    public Collection<SquareUnit> getSolveState(int gameId);
    
    public Boolean setLetter(int gameId, int x, int y, String letter);

	public String getSolveStateString(int gameId);
}
