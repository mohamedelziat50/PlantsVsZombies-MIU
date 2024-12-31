import java.util.ArrayList;
import java.io.Serializable;

public class Player implements Serializable
{
    /*
        MEMBER VARIABLES
     */
    // Username and Password
    private String username, password;
    private int lastUnlockedLevel;    //unlockedLevels added

    /*
        CONSTRUCTORS
     */
    public Player(String username, String password)
    {
        this.username = username;
        this.password = password;
        lastUnlockedLevel = 1;
    }

    /*
        GETTERS & SETTERS
     */

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
    public int getLastUnlockedLevel(){
        return lastUnlockedLevel;
    }
    public void setLastUnlockedLevel(int lastUnlockedLevel) {
        this.lastUnlockedLevel = lastUnlockedLevel;
    }


    /*
        METHODS
     */

}

/*
    Notes for UML:

    Having all plant's health and waiting time will be redundant stored inside the class, utilize yard or level.
    The Yard class or Level can manage plant placements and interactions.
    The Player class doesn't need to store this data directly.
    For example, the Yard class could hold a List<Plant> representing plants currently placed in the yard.

    Our way: Aggregation: If the MainMenu "owns" the Player object but the Player can exist independently
     (e.g., multiple menus accessing the same user object), aggregation makes sense.

    Composition: If the Player object is tightly bound to the MainMenu and cannot exist without it
    (e.g., created when MainMenu is initialized and destroyed alongside it), use composition.

    The Player object doesn’t directly deal with gameplay, it only tracks progress through the List<Level>.
 */