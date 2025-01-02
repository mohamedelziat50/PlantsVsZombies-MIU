import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    // Removed unlockedPlants/Zombies ArrayLists since they're not used.

    private Yard currentYard;

    /*
        CONSTRUCTORS
     */
    // Normal Constructor

    public Level(int levelNumber, int durationInSeconds)
    {
        this.levelNumber = levelNumber;
        this.durationInSeconds = durationInSeconds;
    }

    /*
        METHODS
     */

    // Once Player selects a level, this function will be called to display the game and grid of the Yard.
    // Thus it should return a scene with the yard and everything on it! To be called in Main
    public void startLevel()
    {
        // Stop and pick new soundtrack before displaying Yard
        SoundtrackPlayer.stopTrack();
        if(levelNumber == 1)
            SoundtrackPlayer.playInGametrack1();
        else if(levelNumber == 2)
            SoundtrackPlayer.playInGametrack2();
        else if(levelNumber == 3)
            SoundtrackPlayer.playInGametrack3();

        currentYard = new Yard(this);
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