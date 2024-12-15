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
    private final int ROWS = 5, COLUMNS = 9;
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
    public void placePlant(Plant plant, int row, int col) {
        if (isValidPosition(row, col) && grid[row][col] == null) {
            grid[row][col] = plant;
            System.out.println("Plant Placed Successfully.");
        } else
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
    public boolean isValidPosition(int row, int col) {
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
    public void displayYard() {
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

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Button placeButton = new Button("Place");
                placeButton.setPrefSize(73, 79);
                placeButton.setOpacity(0.25);
                yardGrid.add(placeButton, col, row);
            }
        }

        // Add the grid to the root
        root.getChildren().add(yardGrid);

        // Card image
        final ImageView peashooterCard = new ImageView(new Image("images/cards/peashooterCard.png"));
        peashooterCard.setFitWidth(47);
        peashooterCard.setFitHeight(71);
        peashooterCard.setLayoutX(304);
        peashooterCard.setLayoutY(21);
        peashooterCard.setPreserveRatio(true);
        root.getChildren().add(peashooterCard);

        // PNG when dropping a plant
        final ImageView peashooterPNG = new ImageView(new Image("images/plants/peashooter.png"));
        peashooterPNG.setFitWidth(61);
        peashooterPNG.setFitHeight(74);
        peashooterPNG.setPreserveRatio(true);
        peashooterPNG.setVisible(false);

        // Start tracking the card drag
        peashooterCard.setOnMousePressed(event -> {
            peashooterPNG.setLayoutX(event.getSceneX() - 30);
            peashooterPNG.setLayoutY(event.getSceneY() - 35);
            peashooterPNG.setVisible(true); // Show the Peashooter image
            root.getChildren().add(peashooterPNG); // Add to the root pane
        });

        // Continuously update position during dragging
        peashooterCard.setOnMouseDragged(event -> {
            if (peashooterPNG.isVisible()) {
                peashooterPNG.setLayoutX(event.getSceneX() - 30);
                peashooterPNG.setLayoutY(event.getSceneY() - 35);
            }
        });

        // Drop the Peashooter when the mouse is released
        peashooterCard.setOnMouseReleased(event -> {
            // Check if the drop is within any button
            for (Node node : yardGrid.getChildren())
            {
                if (node instanceof Button)
                {
                    // Create a reference to current button node.
                    Button button = (Button) node;

                    // Get button bounds on screen
                    Bounds buttonBounds = button.localToScene(button.getBoundsInLocal());

                    // Check if the drop point is within this button
                    if (buttonBounds.contains(event.getSceneX(), event.getSceneY()))
                    {
                        // Calculate the button's center position
                        double centerX = buttonBounds.getMinX() + buttonBounds.getWidth() / 2;
                        double centerY = buttonBounds.getMinY() + buttonBounds.getHeight() / 2;

                        // Hide & remove the peashooterPNG
                        peashooterPNG.setVisible(false);
                        root.getChildren().remove(peashooterPNG);

                        // Instantiate a new Peashooter at the button's center
                        Peashooter peashooter = new Peashooter((int) centerX, (int) centerY);

                        // Call the display method for the new Peashooter
                        peashooter.appear(root); // Assuming display takes the root as a parameter to add itself
                        return; // Exit after placing the plant, redundant if we continue in loop.
                    }
                }
            }

            // If not dropped on a button, remove the Peashooter image
            peashooterPNG.setVisible(false);
            root.getChildren().remove(peashooterPNG);
        });

        // Create the scene and set it on the primary stage
        Scene scene = new Scene(root, 1278, 650);
        Main.primaryStage.setScene(scene);
    }
}