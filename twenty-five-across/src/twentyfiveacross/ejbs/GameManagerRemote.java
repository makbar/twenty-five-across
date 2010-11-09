package twentyfiveacross.ejbs;
import javax.ejb.Remote;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.ejb.Stateless;

@WebService
@Remote
public interface GameManagerRemote {
	@WebMethod public Game[] listGames();
	
	@WebMethod public Game newGame();
}
