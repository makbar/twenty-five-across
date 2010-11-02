package twentyfiveacross.ejbs;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id String username;
    String name;
    String password;
    int rating;
    short status; /* 1 = Registered, 2 = Banned, 3 = Deleted */
}
