import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class Yard
{
    private  final int ROWS = 5, COLUMNS = 9;
    private int zombieSpawnInterval;
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

    // Added function called to display the yard when the level starts.
    public void displayYard()
    {
        // Create AnchorPane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1278, 650);

        // Create ImageView for the yard background
        ImageView yardImageView = new ImageView(new Image("images/others/Yard.png"));
        yardImageView.setFitWidth(1278);
        yardImageView.setFitHeight(650);

        // No need to preserve ratio, to make yard larger
        yardImageView.setPreserveRatio(false);

        root.getChildren().add(yardImageView);

        // Create GridPane for placing plants/zombies
        GridPane yardGrid = new GridPane();

        // prefSize refers to the GridPane's width and height! e.g prefWidth and Height
        yardGrid.setPrefSize(680, 411);

        // layout dimensions refer to the GridPane's placement on the screen!
        yardGrid.setLayoutX(227);
        yardGrid.setLayoutY(180);

        yardGrid.setGridLinesVisible(true);

        // Padding between buttons and each other
        yardGrid.setPadding(new Insets(10, 10, 10, 10));

        // Add Buttons to GridPane
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLUMNS; col++)
            {
                // Create Button Object
                Button placeButton = new Button("Place");
                // Specify the button's size inside the cell (Was the problem of not big enough Grid)
                placeButton.setPrefSize(73, 79);
                // Semi-transparent
                placeButton.setOpacity(0.25);
                yardGrid.add(placeButton, col, row);
            }
        }
        // Add GridPane to the AnchorPane
        root.getChildren().add(yardGrid);

        // Create ImageView for the Wooden Box that holds the cards
        ImageView woodenBox = new ImageView(new Image("images/others/woodenBox.png"));
        // Specify its size
        woodenBox.setFitWidth(528);
        woodenBox.setFitHeight(320);
        // Specify its placement
        woodenBox.setLayoutX(227);
        woodenBox.setLayoutY(14);
        // Preserve Ratio, otherwise a corrupted image appears
        woodenBox.setPreserveRatio(true);
        root.getChildren().add(woodenBox);

        // Create ImageView for the Peashooter card that holds the cards
        ImageView peashooterCard = new ImageView(new Image("images/cards/peashooterCard.png"));
        // Specify its size
        peashooterCard.setFitWidth(47);
        peashooterCard.setFitHeight(71);
        // Specify its placement
        peashooterCard.setLayoutX(304);
        peashooterCard.setLayoutY(21);
        // Preserve Ratio, otherwise a corrupted image appears
        peashooterCard.setPreserveRatio(true);
        root.getChildren().add(peashooterCard);

        // Create the scene and set it on the primary stage
        Scene scene = new Scene(root, 1278, 650);

        // Set the scene to display the yard!
        Main.primaryStage.setScene(scene);
    }

}