package twentyfiveacross.ejbs;

import javax.persistence.Entity;
import crosswordsage.*;
import javax.persistence.Id;
import javax.ejb.*;
import javax.jws.WebService;
import javax.jws.WebMethod;
import java.io.File;

@Entity
public class Game {
    @Id int gameId;
    short status; //1 = Finished, 2 = Active
    short accessType; //1 = Public, 2 = Private
    
    Crossword cw; // This is the crossword associated with the game
    
    Game() {
    	File f = new File("H:\\to sort\\Peyton's Documents on 'peytons.clockworkcrew.com' (P)\\school\\c10fall\\crosswords\\helloworld");
    	System.err.println("Loading crossword...");
    	cw = CrosswordFileHandler.readCrosswordFromFile(f);
    	System.err.println("Loaded crossword!");
    }
    
    @WebMethod public Crossword getCrossword() {
    	return cw;
    }
    
    public String toString() {
    	return "A " + cw.getWidth() + " x " + cw.getHeight() + " crossword.";
    }
}
