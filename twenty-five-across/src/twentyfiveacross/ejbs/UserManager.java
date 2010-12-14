/**
 * Name:        UserManager.java
 * Description: Session bean implementation of a remote interface that specifies
 *              the business logic necessary to manage user accounts.
 * Author:      Team 25 Across
 */

package twentyfiveacross.ejbs;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;

@Stateless
public class UserManager implements UserManagerRemote {

    public final static short MAX_INPUT_LEN = 40;
    public final static short STATUS_ACTIVE = 0;
    public final static short STATUS_BANNED = 1;
    public final static short RATING_MIN = 1;
    public final static short RATING_MAX = 5;
    public final static short RATING_NULL = 0;
    public final static String ROOT = "root";

	InitialContext ctx;
	UserInfoBean bean;

	public UserManager() {
		try {
			init();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

	/**
	 *  Initializing function that gets a reference to the user info bean.
	 */
	public void init() throws Exception {
		ctx = new InitialContext();
		bean = (UserInfoBean) ctx.lookup("twentyfiveacross.ejbs.UserBeanInfo");
	}

	/**
	 * Checks whether a given input is acceptable to be used in a function.
	 * @param input  The input value to check
	 * @return  <code>true</code> if the input is acceptable <br>
	 *          <code>false</code> otherwise
	 */
	public boolean isSane(String input)	{
		if (null == input) {
			return false;
		}
		if (input.length() > MAX_INPUT_LEN) {
			return false;
		}
		return input.matches("[a-zA-Z0-9 ]+");
	}

	/**
     * Tries to create a new user account in the database via the bean.
     * @param username  The new account's username
     * @param name  The name of the user this account belongs to
     * @param pw  The hashed representation of the account's password
     * @return  <code>true</code> if the account was created successfully <br>
     *          <code>false</code> otherwise
     */
	public boolean createUser(String username, String name, String pw)
			throws Exception {
		if (!isSane(username) || !isSane(name) || !isSane(pw)) {
			return false;
		}
		boolean test = bean.create(new UserInfo(username, pw, RATING_MIN,
		                                        STATUS_ACTIVE, name));
		return test;
	}

	/**
     * Checks whether a user with the given credentials can log in.
     * @param username  The user's username
     * @param pw  The hashed representation of the user's password
     * @return  <code>true</code> if authentication was successful <br>
     *          <code>false</code> otherwise
     */
	public boolean checkLogin(String username, String pw) throws Exception {
		if (!isSane(username) || !isSane(pw)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (null == u)
			return false;
		return pw.equals(u.getPassword());
	}

	/**
     * Checks whether a user with the given user has been banned.
     * @param username  The user's username
     * @return  <code>true</code> if the user was banned <br>
     *          <code>false</code> otherwise
     */
	public boolean checkBan(String username) throws Exception {
		if (!isSane(username)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (null == u)
			return false;
		return (STATUS_BANNED == u.getStatus());
	}

	/**
     * Updates the name of a given user identified by a username.
     * @param username  The user's username
     * @param name  The name of the user this account belongs to
     * @param managerToken  Security token that only allows an administrator
     *                      to perform the actions in this method
     * @return  <code>true</code> if the update was successful <br>
     *          <code>false</code> otherwise
     */
	public boolean updateDisplayName(String username, String name, String managerToken)
			throws Exception {
		if (!isSane(username) || !isSane(name)) {
			return false;
		}
		if (!checkLogin(ROOT,managerToken)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (null == u)
			return false;
		u.setDisplayname(name);
		return bean.update(u);
	}

    /**
     * Updates the password of a given user identified by a username.
     * @param username  The user's username
     * @param newPw  The user's new password
     * @param managerToken  Security token that only allows an administrator
     *                      to perform the actions in this method
     * @return  <code>true</code> if the update was successful <br>
     *          <code>false</code> otherwise
     */
	public boolean updateUserPw(String username, String newPw,
	        String managerToken) throws Exception {
		if (!isSane(username) || !isSane(newPw)) {
			return false;
		}
		if (!checkLogin(ROOT,managerToken)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (null == u)
			return false;
		u.setPassword(newPw);
		return bean.update(u);
	}

	/**
     * Tries to ban a user as identified by the given username.
     * @param username  The user's username
     * @param managerToken  Security token that only allows an administrator
     *                      to perform the actions in this method
     * @return  <code>true</code> if the banning succeeded<br>
     *          <code>false</code> otherwise
     */
	public boolean banUser(String username, String managerToken)
	        throws Exception {
		if (!isSane(username)) {
			return false;
		}
		if (!checkLogin(ROOT,managerToken) || "root".equals(username)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (null == u)
			return false;
		u.setStatus(STATUS_BANNED);
		return bean.update(u);
	}

	/**
     * Tries to un-ban a user as identified by the given username.
     * @param username  The user's username
     * @param managerToken  Security token that only allows an administrator
     *                      to perform the actions in this method
     * @return  <code>true</code> if the un-banning succeeded<br>
     *          <code>false</code> otherwise
     */
	public boolean unBanUser(String username, String managerToken)
	        throws Exception {
		if (!isSane(username)) {
			return false;
		}
		if (!checkLogin(ROOT,managerToken)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (null == u)
			return false;
		u.setStatus(STATUS_ACTIVE);
		return bean.update(u);
	}

	/**
     * Tries to increase the rating of the user identified by the given username.
     * @param username  The user's username
     * @param managerToken  Security token that only allows an administrator
     *                      to perform the actions in this method
     * @return  <code>true</code> if the rating was increased successfully<br>
     *          <code>false</code> otherwise
     */
	public boolean incUserRating(String username, String managerToken)
	        throws Exception {
		if (!isSane(username)) {
			return false;
		}
		if (!checkLogin("root",managerToken)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (u == null)
			return false;
		int rating = u.getRating();
		if (rating < RATING_MAX) {
			u.setRating(rating + 1);
		}
		return bean.update(u);
	}

	/**
     * Tries to decrease the rating of the user identified by the given username.
     * @param username  The user's username
     * @param managerToken  Security token that only allows an administrator
     *                      to perform the actions in this method
     * @return  <code>true</code> if the rating was decreased successfully<br>
     *          <code>false</code> otherwise
     */
	public boolean decUserRating(String username, String managerToken)
	        throws Exception {
		if (!isSane(username)) {
			return false;
		}
		if (!checkLogin("root",managerToken)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (u == null)
			return false;
		int rating = u.getRating();
		if (rating > RATING_MIN) {
			u.setRating(rating - 1);
		}
		return bean.update(u);
	}

	/**
     * Tries to delete the account of the user identified by the given username.
     * @param username  The user's username
     * @param managerToken  Security token that only allows an administrator
     *                      to perform the actions in this method
     * @return  <code>true</code> if the deletion was successful<br>
     *          <code>false</code> otherwise
     */
	public boolean deleteUser(String username, String managerToken)
	        throws Exception {
		if (!isSane(username)) {
			return false;
		}
		if (!checkLogin("root",managerToken)) {
			return false;
		}
		UserInfo u = bean.find(username);
		if (u == null)
			return false;
		return bean.delete(u);
	}

	/**
     * Gets a list of all the regular users in the database via the bean.
     * @return  The list of usernames of all regular users in the database
     */
	public List<String> listUsers(String managerToken) throws Exception {
		return bean.findAll();
	}

	/**
     * Gets the rating for the user identified by the given username
     * @param username  The user's username
     * @return  The rating of given user
     */
	public int getRating(String username) throws Exception {
		if (!isSane(username)) {
			return RATING_NULL;
		}
		UserInfo u = bean.find(username);
		if (u == null)
			return RATING_NULL;
		return u.getRating();
	}
}
