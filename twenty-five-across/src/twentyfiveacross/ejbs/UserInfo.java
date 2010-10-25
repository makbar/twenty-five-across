package twentyfiveacross.ejbs;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class UserInfo {
    String name;
    @Id String username;
    String password;
    int rating;
    short status; /* 1 = Registered, 2 = Banned, 3 = Deleted */
}
