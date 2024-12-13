import javafx.scene.Scene;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable
{
    /*
        MEMBER VARIABLES
     */

    // Represents the level number, durationInSeconds, and zombie spawn interval for each specific level.
    private int levelNumber;
    private int durationInSeconds;

    // Represents the unlocked plants & zombies for each level object.
    private ArrayList<Plant> unlockedPlants;
    private ArrayList<Zombie> unlockedZombies;

    private Yard currentYard;

    /*
        CONSTRUCTORS
     */
    // Normal Constructor
    public Level(int levelNumber, int durationInSeconds, ArrayList<Plant> unlockedPlants, ArrayList<Zombie> unlockedZombies)
    {
        this.levelNumber = levelNumber;
        this.durationInSeconds = durationInSeconds;

        // Correctly copy the object into a new object, not references.
        this.unlockedPlants = new ArrayList<>(unlockedPlants);
        this.unlockedZombies = new ArrayList<>(unlockedZombies);
    }

    /*
        METHODS
     */

    // Once Player selects a level, this function will be called to display the game and grid of the Yard.
    // Thus it should return a scenee with the yard and everything on it! To be called in Main
    public void startLevel()
    {
        currentYard = new Yard();
        currentYard.displayYard();
    }

    // Once level is selected in MainMenu by the player, this method will initialize and prepare the Yard for the game,
    // such as populating it with unlocked plants and handling zombies.

    //
    /*
        GETTERS & SETTERS
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public ArrayList<Plant> getUnlockedPlants() {
        return unlockedPlants;
    }

    public void setUnlockedPlants(ArrayList<Plant> unlockedPlants) {
        this.unlockedPlants = unlockedPlants;
    }

    public ArrayList<Zombie> getUnlockedZombies() {
        return unlockedZombies;
    }

    public void setUnlockedZombies(ArrayList<Zombie> unlockedZombies) {
        this.unlockedZombies = unlockedZombies;
    }

    public Yard getCurrentYard() {
        return currentYard;
    }

    public void setCurrentYard(Yard currentYard) {
        this.currentYard = currentYard;
    }
}

/*
Notes:
    The Level class is responsible for loading gameplay data into the Yard and defining the rules for that level.
    The Level initializes a new Yard and populates it with plants and zombies based on the level’s data.
 */
