package twentyfiveacross.ejbs;

import java.util.List;

import javax.ejb.Stateless;

import javax.jws.WebService;
import javax.naming.InitialContext;

/**
 * Session Bean implementation class UserManager
 */
@Stateless//(name="UserManager", mappedName="UserManager")
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
		bean = (UserBeanInfo) ctx.lookup("twentyfiveacross.ejbs.UserBeanInfo");
	}

	public String sayHi(String name) {
		return "Hi " + name;
	}
	
	public boolean isSane(String input)
	{
		if(input==null){
			return false;
		}
		if(input.length()>40){
			return false;
		}
		return input.matches("[a-zA-Z0-9 ]+");
	}

	public boolean createUser(String username, String name, String pw)
			throws Exception {
		if(!isSane(username)||!isSane(name)||!isSane(pw)){
			return false;
		}
		boolean test = bean.create(new UserInfo(username, pw, 1, 0, name));
		return test;
	}

	public boolean checkLogin(String username, String pw) throws Exception {
		if(!isSane(username)||!isSane(pw)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		return pw.equals(u.getPassword());
	}

	public boolean checkBan(String username) throws Exception {
		if(!isSane(username)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		return (u.getStatus()==1);
	}

	public boolean updateDisplayName(String username, String name, String managerToken)
			throws Exception {
		if(!isSane(username)||!isSane(name)){
			return false;
		}
		if(!checkLogin("root",managerToken)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		u.setDisplayname(name);
		return bean.update(u);
	}

	public boolean updateUserPw(String username, String newPw, String managerToken) throws Exception {
		if(!isSane(username)||!isSane(newPw)){
			return false;
		}
		if(!checkLogin("root",managerToken)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		u.setPassword(newPw);
		return bean.update(u);
	}

	public boolean banUser(String username, String managerToken) throws Exception {
		if(!isSane(username)){
			return false;
		}
		if(!checkLogin("root",managerToken) || "root".equals(username)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		u.setStatus(1);
		return bean.update(u);
	}

	public boolean unBanUser(String username, String managerToken) throws Exception {
		if(!isSane(username)){
			return false;
		}
		if(!checkLogin("root",managerToken)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		u.setStatus(0);
		return bean.update(u);
	}

	public boolean incUserRating(String username, String managerToken) throws Exception {
		if(!isSane(username)){
			return false;
		}
		if(!checkLogin("root",managerToken)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		int rating = u.getRating();
		if (rating < 5) {
			u.setRating(rating + 1);
		}
		return bean.update(u);
	}

	public boolean decUserRating(String username, String managerToken) throws Exception {
		if(!isSane(username)){
			return false;
		}
		if(!checkLogin("root",managerToken)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		int rating = u.getRating();
		if (rating > 1) {
			u.setRating(rating - 1);
		}
		return bean.update(u);
	}

	public boolean deleteUser(String username, String managerToken) throws Exception {
		if(!isSane(username)){
			return false;
		}
		if(!checkLogin("root",managerToken)){
			return false;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return false;
		return bean.delete(u);
	}
	
	public List<String> listUsers(String managerToken) throws Exception {
		return bean.findAll();
	}

	public int getRating(String username) throws Exception {
		if(!isSane(username)){
			return 0;
		}
		UserInfo u = bean.find(username);
		if(u==null)
			return 0;
		return u.getRating();
	}

}
