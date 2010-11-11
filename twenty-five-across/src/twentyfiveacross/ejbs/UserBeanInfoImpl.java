package twentyfiveacross.ejbs;

//import java.util.Iterator;
//import java.util.List;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//import org.eclipse.persistence.annotations.Convert;

@Stateless(name="UserBeanInfo", mappedName="ejb/UserInfoJNDI") 
public class UserBeanInfoImpl implements UserBeanInfo {
	
	@PersistenceContext(name="persistence_ctx")
	EntityManager em;
	//public static final String RemoteJNDIName =  UserBeanInfoImpl.class.getSimpleName() + "/remote";
	
	@Override
	public boolean create(UserInfo bean)
	{
		try {
			if (em.find(UserInfo.class, bean.getUsername()) == null) {
				em.persist(bean);
				return true;
			}
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean update(UserInfo bean)
	{
		try {
			em.merge(bean);
			return true;				
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(UserInfo bean)
	{
		try {
			em.remove(em.merge(bean));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public UserInfo find(String username)
	{
		try {
			em.flush();
			return em.find(UserInfo.class, username);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<String> findAll()
	{
		try {
			return (List<String>) (em.createNativeQuery("SELECT UserName FROM userinfo").getResultList());
		} catch (Exception e) {
			return null;
		}
	}
	
}
