package twentyfiveacross.ejbs;
import javax.ejb.Remote;
import javax.servlet.http.HttpServletRequest;

@Remote
public interface UserManagerRemote {
	
	public void init()
			throws Exception;
	
    public String sayHi(String name);

    public boolean register(HttpServletRequest req)
    		throws Exception;

    public boolean createUser(String username, String name)
            throws Exception;

    public boolean createUser(String username, String name, String pw)
            throws Exception;

    public boolean updateUser(String username, String name, int roleType)
            throws Exception;

    public boolean updateUserPw(String username, byte[] oldPwHash,
            byte[] newPwHash) throws Exception;

    public boolean banUser(String username)
            throws Exception;

    public boolean unBanUser(String username)
            throws Exception;

    public boolean deleteUser(String username)
            throws Exception;
}
