package twentyfiveacross.ejbs;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;

/**
 * Session Bean implementation class UserManager
 */
@Stateless(name="UserManager",mappedName="UserManager")
public class UserManager implements UserManagerRemote {

	InitialContext ctx;
	UserBeanInfo bean;

	public UserManager() {
		try {
			init();
		} catch (Exception e) {
			//
		}
	}

	public void init() throws Exception {
		ctx = new InitialContext();
		bean = (UserBeanInfo) ctx.lookup("ejb/UserInfoJNDI");
	}

	public String sayHi(String name) {
		return "Hi " + name;
	}

	public boolean createUser(String username, String name, String pw)
			throws Exception {
		return bean.create(new UserInfo(username, pw, 1, 0, name));
	}

	public boolean checkLogin(String username, String pw) throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		return pw.equals(u.getPassword());
	}

	public boolean checkBan(String username) throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		return (u.getStatus()==1);
	}

	public boolean updateDisplayName(String username, String name)
			throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		u.setDisplayname(name);
		return bean.update(u);
	}

	public boolean updateUserPw(String username, String newPw) throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		u.setPassword(newPw);
		return bean.update(u);
	}

	public boolean banUser(String username) throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		u.setStatus(1);
		return bean.update(u);
	}

	public boolean unBanUser(String username) throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		u.setStatus(0);
		return bean.update(u);
	}

	public boolean incUserRating(String username) throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		int rating = u.getRating();
		if (rating < 5) {
			u.setRating(rating + 1);
		}
		return bean.update(u);
	}

	public boolean decUserRating(String username) throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		int rating = u.getRating();
		if (rating > 1) {
			u.setRating(rating - 1);
		}
		return bean.update(u);
	}

	public boolean deleteUser(String username) throws Exception {
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		return bean.delete(u);
	}
	
	public List<String> listUsers() throws Exception {
		return bean.findAll();
	}

}
