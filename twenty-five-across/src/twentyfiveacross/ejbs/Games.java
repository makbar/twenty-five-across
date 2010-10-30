package twentyfiveacross.ejbs;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Games {
    @Id int gameId;
    short status; //1 = Finished, 2 = Active
    short accessType; //1 = Public, 2 = Private
}
