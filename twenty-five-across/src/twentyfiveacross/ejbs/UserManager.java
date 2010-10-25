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
}
