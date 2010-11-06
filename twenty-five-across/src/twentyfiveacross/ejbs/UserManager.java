package twentyfiveacross.ejbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import com.sun.rowset.CachedRowSetImpl;

/**
 * Session Bean implementation class UserManager
 */
@Stateless
public class UserManager implements UserManagerRemote {

    InitialContext ic;
    EJBContext ejbContext;
	String mysqlUserName = "tfacross";
	String mysqlPassword = "tfacross";
	String mysqlDatabase = "tfacross";
	String mysqlUrl = "jdbc:mysql://localhost:3306/" + mysqlDatabase;
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
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
		ic = new InitialContext();
		//ejbContext = (EJBContext) ic.lookup("java:comp/EJBContext");
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(mysqlUrl, mysqlUserName, mysqlPassword);
	}

    public boolean createUser(String username, String name)
            throws Exception {
		String mysqlQuery = "INSERT INTO userinfo (UserName, Password, Rating, Status, DisplayName) VALUES (?,SHA(?),?,?,?)";
		stmt = con.prepareStatement(mysqlQuery);
		stmt.setString(1, username);
		stmt.setString(2, "password");
		stmt.setInt(3, 1); // Rating = 1 to 5
		stmt.setInt(4, 1); // Status = 0-banned, 1-registered
		stmt.setString(5, name);

		int rows = stmt.executeUpdate();
		
		if(rows>0)
			return true;
		else
			return false;
    }

    public boolean createUser(String username, String name, String pw)
    throws Exception {
		String mysqlQuery = "INSERT INTO userinfo (UserName, Password, Rating, Status, DisplayName) VALUES (?,SHA(?),?,?,?)";
		stmt = con.prepareStatement(mysqlQuery);
		stmt.setString(1, username);
		stmt.setString(2, pw);
		stmt.setInt(3, 1); // Rating = 1 to 5
		stmt.setInt(4, 1); // Status = 0-banned, 1-registered
		stmt.setString(5, name);

		int rows = stmt.executeUpdate();
		
		if(rows>0)
			return true;
		else
			return false;
    }

	public boolean login(String username, String pw) throws Exception {
		String mysqlQuery = "SELECT UserName FROM userinfo WHERE UserName = ? AND Password = SHA( ? )";
		stmt = con.prepareStatement(mysqlQuery);
		stmt.setString(1,username);
		stmt.setString(2,pw);
		
		ResultSet rs = stmt.executeQuery();

		if (rs.first())
			return true;
		else
			return false;
	}
    
    public boolean register(HttpServletRequest req)
    throws Exception {
    	String name = req.getParameter("name");
    	String username = req.getParameter("_U");
    	String password = req.getParameter("_P");
    	if (username!=null && password!=null)
    		return createUser(name,username,password);
    	else if(password==null)
    		return createUser(name,username);
    	else
    	return false;
    }


    public boolean updateUser(String username, String name, int roleType)
            throws Exception {
        return false;
    }

    public boolean updateUserPw(String username, String oldPw,
            String newPw) throws Exception {
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
