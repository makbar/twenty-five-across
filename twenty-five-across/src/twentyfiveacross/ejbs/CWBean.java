package twentyfiveacross.ejbs;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="crosswords")
@NamedQueries({
    @NamedQuery(name = "getCw", query = "SELECT c FROM CWBean c WHERE c.count <= ALL (SELECT c2.count FROM CWBean c2)"),
    @NamedQuery(name = "incCount", query = "UPDATE CWBean c SET c.count = :count WHERE c.cwId = :id")
})
public class CWBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private int cwId;
	private int count;
	private byte[] cw;
	
	public int getCwId() {
		return cwId;
	}
	public void setCwId(int cwId) {
		this.cwId = cwId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public byte[] getCw() {
		return cw;
	}
	public void setCw(byte[] cw) {
		this.cw = cw;
	}

}
