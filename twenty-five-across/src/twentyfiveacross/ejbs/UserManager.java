package twentyfiveacross.ejbs;

import java.sql.DriverManager;
import java.sql.Statement;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Session Bean implementation class UserManager
 */
@Stateless
public class UserManager implements UserManagerRemote {

	String mysqlUserName = "tfacross";
	String mysqlPassword = "tfacross";
	String mysqlDatabase = "tfacross";
	String mysqlUrl = "jdbc:mysql://localhost:3306/" + mysqlDatabase;
	java.sql.Connection con;
	java.sql.Statement stmt;
	java.sql.ResultSet rs;
	CachedRowSetImpl result;

	public UserManager()
	{
        try
        {
        	init();
        }
        catch(Exception e)
        {
        	//
        }
	}
	
	public void init() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(mysqlUrl, mysqlUserName, mysqlPassword);
		stmt = con.createStatement();
	}

	public String sayHi(String name) {
        return "Hi " + name + "!";
    }

    public boolean createUser(String username, String name)
            throws Exception {
		String mysqlQuery = "INSERT INTO userinfo VALUES (";
		mysqlQuery += "\'" + username + "\',";
		mysqlQuery += "\'" + "password" + "\',";
		mysqlQuery += 1 + ",";
		mysqlQuery += "\'" + "Registered" + "\'";
		mysqlQuery += ");";
		int rows = stmt.executeUpdate(mysqlQuery);
		if(rows>0)
			return true;
		else
			return false;
    }

    public boolean createUser(String username, String name, String pw)
    throws Exception {
		String mysqlQuery = "INSERT INTO userinfo (UserName, Password, Rating, Status) VALUES (";
		mysqlQuery += "'" + username + "',";
		mysqlQuery += "'" + pw + "',";
		mysqlQuery += "'" + 1 + "',";
		mysqlQuery += "'" + "Registered" + "'";
		mysqlQuery += ");";
		
		int rows = stmt.executeUpdate(mysqlQuery);
		
		if(rows>0)
			return true;
		else
			return false;
    }

    public boolean register(HttpServletRequest req)
    throws Exception {
    	String username = req.getParameter("_U");
    	String password = req.getParameter("_P");
    	if (username!=null && password!=null)
    		return createUser("",username,password);
    	else if(password==null)
    		return createUser("",username);
    	else
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
