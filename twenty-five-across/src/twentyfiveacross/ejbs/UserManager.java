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

    //InitialContext ic;
    //EJBContext ejbContext;
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
		//ic = new InitialContext();
		//ejbContext = (EJBContext) ic.lookup("java:comp/EJBContext");
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(mysqlUrl, mysqlUserName, mysqlPassword);
	}

	public String sayHi(String name) {
		return "Hi " + name;
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

	public boolean login(HttpServletRequest req) throws Exception {
		if(req==null)
			return false;
    	String username = req.getParameter("_U");
    	String password = req.getParameter("_P");
    	if (username!=null && password!=null)
    		return checkLogin(username,password);
    	else
    		return false;
	}
    
	public boolean checkLogin(String username, String pw) throws Exception {
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
    
	public CachedRowSetImpl listUsers() throws Exception {
		String mysqlQuery = "SELECT UserName,Rating,Status,DisplayName FROM userinfo";
		stmt = con.prepareStatement(mysqlQuery);
		ResultSet rs = stmt.executeQuery();
		CachedRowSetImpl crs = new CachedRowSetImpl();
		crs.populate(rs);
		return crs;
	}
    
    public boolean register(HttpServletRequest req)
    throws Exception {
		if(req==null)
			return false;
    	String name = req.getParameter("name");
    	String username = req.getParameter("_U");
    	String password = req.getParameter("_P");
    	if (name!=null && username!=null && password!=null)
    		return createUser(username,name,password);
    	else if(name!=null && username!=null)
    		return createUser(username,name);
    	else
    	return false;
    }

    public boolean editUser(HttpServletRequest req)
    		throws Exception {
    	String name = req.getParameter("name");
    	String username = req.getParameter("_U");
    	String password = req.getParameter("_P");
    	String action = req.getParameter("action");
    	
    	if(action == null)
    		return false;
    	
    	if (action=="changeName" && name!=null && username!=null)
    		return updateUserName(username,name);
    	else if (action=="changePw" && password!=null && username!=null)
    		return updateUserPw(username, password);
    	else if (action=="ban" && username!=null)
    		return banUser(username);
    	else if (action=="unBan" && username!=null)
    		return unBanUser(username);
    	else if (action=="delete" && username!=null)
    		return deleteUser(username);
    	else
    		return false;    	
    }

    public boolean updateUserName(String username, String name)
            throws Exception {
		String mysqlQuery = "UPDATE userinfo SET DisplayName = ? WHERE UserName = ?";
		stmt = con.prepareStatement(mysqlQuery);
		stmt.setString(1, name);
		stmt.setString(2, username);

		int rows = stmt.executeUpdate();
		
		if(rows>0)
			return true;
		else
			return false;
    }

    public boolean updateUserPw(String username, String newPw) throws Exception {
		String mysqlQuery = "UPDATE userinfo SET Password = SHA(?) WHERE UserName = ?";
		stmt = con.prepareStatement(mysqlQuery);
		stmt.setString(1, newPw);
		stmt.setString(2, username);

		int rows = stmt.executeUpdate();
		
		if(rows>0)
			return true;
		else
			return false;
    }

    public boolean banUser(String username)
            throws Exception {
		String mysqlQuery = "UPDATE userinfo SET Status = 0 WHERE UserName = ?";
		stmt = con.prepareStatement(mysqlQuery);
		stmt.setString(1, username);

		int rows = stmt.executeUpdate();
		
		if(rows>0)
			return true;
		else
			return false;
    }

    public boolean unBanUser(String username)
            throws Exception {
		String mysqlQuery = "UPDATE userinfo SET Status = 1 WHERE UserName = ?";
		stmt = con.prepareStatement(mysqlQuery);
		stmt.setString(1, username);

		int rows = stmt.executeUpdate();
		
		if(rows>0)
			return true;
		else
			return false;
    }

    public boolean deleteUser(String username)
            throws Exception {
		String mysqlQuery = "DELETE FROM userinfo WHERE UserName = ?";
		stmt = con.prepareStatement(mysqlQuery);
		stmt.setString(1, username);

		int rows = stmt.executeUpdate();
		
		if(rows>0)
			return true;
		else
			return false;
    }
}
