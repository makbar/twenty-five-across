package twentyfiveacross.ejbs;
import javax.ejb.Remote;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.ejb.Stateless;

@WebService

@Remote
public interface UserManagerRemote {
	
	public void init()
			throws Exception;
	
    @WebMethod public String sayHi(String name);

    public boolean register(String username, String password)
    		throws Exception;

    public boolean createUser(String username, String name)
            throws Exception;

    public boolean createUserPw(String username, String name, String pw)
            throws Exception;

	public boolean login(String username, String pw)
			throws Exception;

		public boolean updateUser(String username, String name, int roleType)
            throws Exception;

    public boolean updateUserPw(String username, String oldPw,
            String newPw) throws Exception;

    public boolean banUser(String username)
            throws Exception;

    public boolean unBanUser(String username)
            throws Exception;

    public boolean deleteUser(String username)
            throws Exception;
}
