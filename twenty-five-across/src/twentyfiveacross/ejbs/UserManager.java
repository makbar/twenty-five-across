package twentyfiveacross.ejbs;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class UserManager
 */
@Stateless
public class UserManager implements UserManagerRemote {
    public String sayHi(String name) {
        return "Hi " + name + "!";
    }

    public boolean createUser(String username, String name)
            throws Exception {
        return false;
    }

    public boolean createUser(String username, String name, String pw)
            throws Exception {
        return false;
    }

    public boolean updateUser(String username, String name, int roleType)
            throws Exception {
        return false;
    }

    public boolean updateUserPw(String username, byte[] oldPwHash,
            byte[] newPwHash) throws Exception {
        return false;
    }

    public boolean banUser(String username)
            throws Exception {
        return false;
    }

    public boolean unBanUser(String username)
            throws Exception {
        return false;
    }

    public boolean deleteUser(String username)
            throws Exception {
        return false;
    }
}
