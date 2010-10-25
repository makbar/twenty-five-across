package twentyfiveacross.ejbs;
import javax.ejb.Remote;

@Remote
public interface UserManagerRemote {
    public String sayHi(String name);
}
