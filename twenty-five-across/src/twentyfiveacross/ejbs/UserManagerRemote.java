package twentyfiveacross.ejbs;
import javax.ejb.Remote;
import javax.servlet.http.HttpServletRequest;

@Remote
public interface UserManagerRemote {
	
	public void init()
			throws Exception;
	
    public boolean register(HttpServletRequest req)
    		throws Exception;

    public boolean createUser(String username, String name)
            throws Exception;

    public boolean createUser(String username, String name, String pw)
            throws Exception;

	public boolean login(HttpServletRequest req)
			throws Exception;

	public boolean checkLogin(String username, String pw)
			throws Exception;

	public boolean updateUserName(String username, String name)
            throws Exception;

    public boolean updateUserPw(String username,
            String newPw) throws Exception;

    public boolean banUser(String username)
            throws Exception;

    public boolean unBanUser(String username)
            throws Exception;

    public boolean deleteUser(String username)
            throws Exception;
}
