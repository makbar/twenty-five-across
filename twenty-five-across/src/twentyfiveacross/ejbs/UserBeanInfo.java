package twentyfiveacross.ejbs;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface UserBeanInfo {
	public boolean create(UserInfo bean) throws Exception;
	public boolean update(UserInfo bean);
	public boolean delete(UserInfo bean);
	public UserInfo find(String username);
	public List<String> findAll();
}
