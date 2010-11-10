package twentyfiveacross.ejbs;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userinfo")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id String username;
    String password;
    int rating; /* 1 - low --- 5 - high */
    int status; /* 0 = Registered, 1 = Banned */
    String displayname;
    
    public UserInfo()
    {
    	this.username = null;
    }
    
    public UserInfo(String username,String password,int rating,int status,String displayname)
    {
    	setUsername(username);
    	setPassword(password);
    	setRating(rating);
    	setStatus(status);
    	setDisplayname(displayname);    	
    }

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
}
