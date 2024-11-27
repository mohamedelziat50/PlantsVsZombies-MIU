import java.util.Random;

public class Yard
{
    private  final int ROWS = 5, COLUMNS = 9;
    private int zombieSpawnInterval = 60;
    private Characters[][] grid;
    private LawnMower[] lawnMowers;


    /* constructor, to initialize the 2d array of type Characters, in which plants and zombies inherit from.
    also is used to make instance of the lawn mowers at the beginning of each row.*/
    public Yard()
    {
        // Initialize Characters 2D Array to keep a-hold of Zombies, Plants, LawnMower, and possibly peas.
        grid = new Characters[ROWS][COLUMNS];

        // Intiliaze Lawn Mowers Object for each row.
        lawnMowers = new LawnMower[ROWS];
        for (int i = 0; i < ROWS; i++)
            lawnMowers[i] = new LawnMower();
    }

    // Get character at the specific position inside the grid.
    public Characters getCharacter(int row, int col)
    {
        // Validate position before returning character inside the cell.
        if (isValidPosition(row, col))
            return grid[row][col];

        // Return Null for now, will be used in GUI.
        return null;
    }


    // placePlant checks if the current cell ur pointing at is empty, if it is, it places the desired plant.
    public void placePlant(Plant plant, int row, int col)
    {
        if (isValidPosition(row, col) && grid[row][col] == null)
        {
            grid[row][col] = plant;
            System.out.println("Plant Placed Successfully.");
        }
        else
            System.out.println("Failed to place plant, one already exists at this cell.");

    }

    /* spawns a zombie, used setZombieSpawnInterval in seconds to detect how much would it
     take to spawn another zombie */
    public void spawnZombie(Zombie zombie, int col) throws InterruptedException
    {
        Random random = new Random();
        while(true)
        {
            Thread.sleep(zombieSpawnInterval * 1000);
            int zombieSpawnRow = random.nextInt(5);
            grid[zombieSpawnRow][COLUMNS-1] = zombie;
            System.out.println("zombie placed at row" + zombieSpawnRow);
        }
    }


    // shovel is used to remove plants from the grid
    public boolean shovel(int row, int col)
    {
        if (isValidPosition(row, col) && grid[row][col] != null)
        {
            if(grid[row][col] instanceof Plant){
                grid[row][col] = null;
                return true;
            }
        }
        return false;
    }

    /* a method made to check if the current cell ur trying to place a plant at lies between the
     interval of rows and columns in the 2d array */
    public boolean isValidPosition(int row, int col)
    {
        return row >= 0 && row < ROWS && col >= 0 && col < COLUMNS && grid[row][col] == null;
    }

    public void moveLawnMower()
    {
        LawnMower lawnMower=new LawnMower();
        int x = lawnMower.getX();
        for (int col = 0; col < COLUMNS-1; col++)
        {
            if (grid[x][col+1] instanceof Zombie) {
                grid[x][col+1] = null; // Remove zombie from the grid
            }

            lawnMower.setY(lawnMower.getY()+1);
        }

        lawnMower.disappear();
    }

    public boolean activateLawnMower(int row)
    {
        if (row >= 0 && row < ROWS && lawnMowers[row] != null && grid[row][1] instanceof Zombie)
        {
            moveLawnMower();
            lawnMowers[row] = null;
            return true;
        }
        return false;
    }

}