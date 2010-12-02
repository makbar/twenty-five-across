package twentyfiveacross.ejbs;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import static javax.persistence.CascadeType.ALL;

import crosswordsage.*;

import javax.persistence.Id;
import javax.ejb.*;
import javax.jws.WebService;
import javax.jws.WebMethod;
import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.Random;

@Entity
@Table(name="games")//TODO:  Correct name?
@NamedQueries({
    @NamedQuery(name = "findAllGames", query = "SELECT g FROM Game g WHERE g.accessType = 2")
})
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int PUZZLECORRECT = -1;
	public static final int USERRESIGNED = -2;
	
    private int gameId;
    private int status; //1 = Finished, 2 = Active
    private int accessType; //1 = Public, 2 = Private
    public String playingUser;
    public String resigningUser;
    private Collection<SquareUnit> squares;
    
	private int solvedState = 0;
	  // With this variable, if >= 0, it's the number of times we've checked the puzzle against
	  // the solution.  If < 0, it means that the 
	
    private byte[] cw; // This is the crossword associated with the game
    //private SolveState ss; // This contains the state of every square
    
    Game() {
    	gameId = (int) (Math.random() * (double)Integer.MAX_VALUE);
    	accessType = 2;
    	status = 2;
    	/*gameId = (int) (Math.random() * (double)Integer.MAX_VALUE);
    	File f = new File("helloworld2");
    	System.err.println("Loading crossword...");
    	cw = CrosswordFileHandler.readCrosswordFromFile(f);
    	System.err.println("Loaded crossword! id: " + gameId);
    	ss = new SolveState(cw.getWidth(), cw.getHeight());*/
    }
    
    @Id
    public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAccessType() {
		return accessType;
	}

	public void setAccessType(int accessType) {
		this.accessType = accessType;
	}

	@OneToMany(cascade = ALL, mappedBy = "game")
	public Collection<SquareUnit> getSquares() {
		return squares;
	}

	public void setSquares(Collection<SquareUnit> squares) {
		this.squares = squares;
	}

	public byte[] getCw() {
		return cw;
	}
	public void setCw(byte[] cw) {
		this.cw = cw;
	}
	
	public void resign(String user) {
		solvedState = USERRESIGNED;
		resigningUser = user;
	}
	
	/*
	public String toString() {
    	return "A " + cw.getWidth() + " x " + cw.getHeight() + " crossword.";
    }

	public SolveState getSolveState() {
		return ss;
	}
	
	

	public Boolean setLetter(int x, int y, String letter) {
		if(false) {//!letter.matches("/[A-Za-z0-9 ]/")
			System.err.println("User entered bad character: " + (int) letter.charAt(0));
			return false;
		}
		if(solvedState < 0) {
			System.err.println("setLetter called on a finished puzzle");
			return false;
		}
		ss.setState(x, y, letter);
		// TODO Auto-generated method stub
		return true;
	}*/
}
