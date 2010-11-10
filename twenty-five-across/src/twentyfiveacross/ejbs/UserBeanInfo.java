package twentyfiveacross.ejbs;

import javax.ejb.Remote;

@Remote
public interface UserBeanInfo {
	public boolean create(UserInfo bean);
	public boolean update(UserInfo bean);
	public boolean delete(UserInfo bean);
	public UserInfo find(String username);
}
