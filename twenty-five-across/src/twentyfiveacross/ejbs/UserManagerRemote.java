package twentyfiveacross.ejbs;

import java.util.List;

import javax.ejb.Remote;
@Remote
public interface UserManagerRemote {

	public void init() throws Exception;

	public String sayHi(String name);

	public boolean createUser(String username, String name, String pw)
			throws Exception;

	public boolean checkLogin(String username, String pw) throws Exception;

	public boolean updateDisplayName(String username, String name)
			throws Exception;

	public boolean updateUserPw(String username, String newPw) throws Exception;

	public boolean checkBan(String username) throws Exception;

	public List<String> listUsers() throws Exception;

	public boolean banUser(String username) throws Exception;

	public boolean unBanUser(String username) throws Exception;

	public boolean incUserRating(String username) throws Exception;

	public boolean decUserRating(String username) throws Exception;

	public boolean deleteUser(String username) throws Exception;
}
