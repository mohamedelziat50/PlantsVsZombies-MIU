import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class Yard
{
    private final int ROWS = 5, COLUMNS = 9, WIDTH = 1278, HEIGHT = 650;
    private int zombieSpawnInterval;
    private Characters[][] grid;
    private LawnMower[] lawnMowers;

    /* constructor, to initialize the 2d array of type Characters, in which plants and zombies inherit from.
    also is used to make instance of the lawn mowers at the beginning of each row.*/
    public Yard() {
        // Initialize Characters 2D Array to keep a-hold of Zombies, Plants, LawnMower, and possibly peas.
        grid = new Characters[ROWS][COLUMNS];

        // Intiliaze Lawn Mowers Object for each row.
        lawnMowers = new LawnMower[ROWS];
        for (int i = 0; i < ROWS; i++)
            lawnMowers[i] = new LawnMower();
    }

    // Get character at the specific position inside the grid.
    public Characters getCharacter(int row, int col) {
        // Validate position before returning character inside the cell.
        if (isValidPosition(row, col))
            return grid[row][col];

        // Return Null for now, will be used in GUI.
        return null;
    }


    // placePlant checks if the current cell ur pointing at is empty, if it is, it places the desired plant.
    public void placePlant(Plant plant, AnchorPane root, int row, int col)
    {
        // Place the plant only if the position is valid: 1. no plant is on the cell, and 2. inside the yard
        if (isValidPosition(row, col) && grid[row][col] == null)
        {
            // The grid is used to keep track whether the grid cell is taken up by a plant.
            grid[row][col] = plant;

            // Call the plants' subclass over-ridden appear function.
            plant.appear(root);

            // For tracing
            System.out.println("Plant Placed Successfully at [" + row + "]" + "[" + col + "]");
        }
        else
            System.out.println("Failed to place plant, one already exists at this cell.");

    }

    /* spawns a zombie, used setZombieSpawnInterval in seconds to detect how much would it
     take to spawn another zombie */
    public void spawnZombie(Zombie zombie, int col) throws InterruptedException {
        Random random = new Random();
        while (true) {
            Thread.sleep(zombieSpawnInterval * 1000);
            int zombieSpawnRow = random.nextInt(5);
            grid[zombieSpawnRow][COLUMNS - 1] = zombie;
            System.out.println("zombie placed at row" + zombieSpawnRow);
        }
    }


    // shovel is used to remove plants from the grid
    public boolean shovel(int row, int col) {
        if (isValidPosition(row, col) && grid[row][col] != null) {
            if (grid[row][col] instanceof Plant) {
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

    public void moveLawnMower() {
        LawnMower lawnMower = new LawnMower();
        int x = lawnMower.getX();
        for (int col = 0; col < COLUMNS - 1; col++) {
            if (grid[x][col + 1] instanceof Zombie) {
                grid[x][col + 1] = null; // Remove zombie from the grid
            }

            lawnMower.setY(lawnMower.getY() + 1);
        }

        lawnMower.disappear();
    }

    public boolean activateLawnMower(int row) {
        if (row >= 0 && row < ROWS && lawnMowers[row] != null && grid[row][1] instanceof Zombie) {
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
        root.setPrefSize(WIDTH, HEIGHT);

        // Create ImageView for the yard background
        ImageView yardImageView = new ImageView(new Image("images/others/Yard.png"));
        yardImageView.setFitWidth(WIDTH);
        yardImageView.setFitHeight(HEIGHT);

        // No need to preserve ratio, to make yard larger
        yardImageView.setPreserveRatio(false);

        root.getChildren().add(yardImageView);

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

        // Create GridPane for placing plants/zombies
        GridPane yardGrid = new GridPane();
        yardGrid.setPrefSize(680, 411);
        yardGrid.setLayoutX(227);
        yardGrid.setLayoutY(180);
        yardGrid.setGridLinesVisible(true);

        // Padding between buttons and the edge of grid(space)
        yardGrid.setPadding(new Insets(10, 10, 10, 10));

        // Place buttons inside for event handling
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLUMNS; col++)
            {
                Button placeButton = new Button("Place");
                placeButton.setPrefSize(73, 79);
                placeButton.setOpacity(0.25);
                yardGrid.add(placeButton, col, row);
            }
        }

        // Add the grid to the root
        root.getChildren().add(yardGrid);

        // Later we should add conditions based on level 1, 2, 3: A function to load the specific cards whether unlocked or locked and use gray cards
        // Testing for generic cards.

        // PEASHOOTER CARD
        Card PEASHOOTERCARD = new Card(
            "images/cards/peashooterCard.png",
            "images/plants/peashooter.png",
            "images/plants/peashooter.gif", // Optional
            Peashooter.class
        );

        PEASHOOTERCARD.cardImageViewSetProperties(304, 21, 47, 71, true, true);
        PEASHOOTERCARD.draggingImageViewSetProperties(61, 74, true, false);
        PEASHOOTERCARD.hoverImageViewSetProperties(61, 74, true, false);
        PEASHOOTERCARD.addToYard(root, yardGrid, this);

        // SUNFLOWER CARD
        Card SUNFLOWERCARD = new Card(
                "images/cards/sunflowerCard.png",
                "images/plants/sunflower.png",
                "images/plants/sunflower.gif", // Optional
                Sunflower.class
        );

        SUNFLOWERCARD.cardImageViewSetProperties(358, 21, 47, 66, true, true);
        SUNFLOWERCARD.draggingImageViewSetProperties(61, 66, true, false);
        SUNFLOWERCARD.hoverImageViewSetProperties(61, 66, true, false);
        SUNFLOWERCARD.addToYard(root, yardGrid, this);

        // POTATO CARD
        Card POTATOCARD = new Card(
                "images/cards/potatoCard.png",
                "images/plants/potato.png",
                "images/plants/potato.gif", // Optional
                Potato.class
        );

        POTATOCARD.cardImageViewSetProperties(413, 21, 47, 66, true, true);
        POTATOCARD.draggingImageViewSetProperties(59, 66, true, false);
        POTATOCARD.hoverImageViewSetProperties(59, 66, true, false);
        POTATOCARD.addToYard(root, yardGrid, this);

        // LOCKED CARDS
        Card cherryLockedCard = new Card("images/lockedCards/cherryLockedCard.png");
        cherryLockedCard.cardImageViewSetProperties(468, 21, 47, 66, true, true);
        Card snowpeaLockedCard = new Card("images/lockedCards/snowpeaLockedCard.png");
        snowpeaLockedCard.cardImageViewSetProperties(520, 21, 47, 66, true, true);
        Card jalapenoLockedCard = new Card("images/lockedCards/jalapenoLockedCard.png");
        jalapenoLockedCard.cardImageViewSetProperties(573, 21, 47, 66, true, true);
        Card repeaterLockedCard = new Card("images/lockedCards/repeaterLockedCard.png");
        repeaterLockedCard.cardImageViewSetProperties(626, 21, 47, 66, true, true);

        root.getChildren().addAll(cherryLockedCard.getCardImageView(), snowpeaLockedCard.getCardImageView(), jalapenoLockedCard.getCardImageView(), repeaterLockedCard.getCardImageView());

        // Create the scene and set it on the primary stage
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Main.primaryStage.setScene(scene);
    }
}