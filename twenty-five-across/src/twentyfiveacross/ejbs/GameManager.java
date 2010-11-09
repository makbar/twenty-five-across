package twentyfiveacross.ejbs;
import javax.ejb.*;
import javax.jws.WebService;

@WebService(endpointInterface = "twentyfiveacross.ejbs.GameManagerRemote")
@Stateless(name="GameManagerBean")
public class GameManager implements GameManagerRemote {
	// Allows users to look up games, create a new one
	
	public Game[] listGames() {
		Game[] gameslist = new Game[1];
		gameslist[0] = new Game();
		return gameslist;
	}
	
	public Game newGame() {
		return new Game();
	}
}
