import java.util.List;

public class Level
{
    /*
        MEMBER VARIABLES
     */
    // Represents the level number
    private int levelNumber;

    // Represents the unlocked plants & zombies for each level object.
    private List<Plant> unlockedPlants;
    private List<Zombie> unlockedZombies;

    private Yard currentYard;

    /*
        CONSTRUCTORS
     */
    public Level(int levelNumber, List<Plant> unlockedPlants, List<Zombie> unlockedZombies, Yard currentYard)
    {
        this.levelNumber = levelNumber;
        this.unlockedPlants = unlockedPlants;
        this.unlockedZombies = unlockedZombies;
        this.currentYard = currentYard;
    }

    /*
        GETTERS & SETTERS
     */
    public int getLevelNumber() {
        return levelNumber;
    }
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }
    public List<Plant> getUnlockedPlants() {
        return unlockedPlants;
    }
    public void setUnlockedPlants(List<Plant> unlockedPlants) {
        this.unlockedPlants = unlockedPlants;
    }
    public List<Zombie> getUnlockedZombies() {
        return unlockedZombies;
    }
    public void setUnlockedZombies(List<Zombie> unlockedZombies) {
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
