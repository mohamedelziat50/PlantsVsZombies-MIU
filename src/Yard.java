import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Yard extends Thread
{
    public static final int ROWS = 5, COLUMNS = 9, WIDTH = 1278, HEIGHT = 650;
    private int zombieSpawnInterval;
    private Characters[][] grid;
    private LawnMower[] lawnMowers;
    public static AnchorPane root;
    public static int sunCounter = 50;
    public static Label label = new Label("50");

    // Used for collision handling
    public static volatile ArrayList<Zombie> zombies = new ArrayList<>();

    /* constructor, to initialize the 2d array of type Characters, in which plants and zombies inherit from.
    also is used to make instance of the lawn mowers at the beginning of each row.*/
    public Yard()
    {
        root = new AnchorPane();
        zombieSpawnInterval=5;
        // Initialize Characters 2D Array to keep a-hold of Zombies, Plants, LawnMower, and possibly peas.
        grid = new Characters[ROWS][COLUMNS];

        /*
        THIS CODE IS REDUNDANT, WE INTILAIZE LAWNS MOWERS IN THE generateLawnMowers FUNCTION

        // Initialize Lawn Mowers Object for each row.
        lawnMowers = new LawnMower[ROWS];

        for (int i = 0; i < ROWS; i++)
            lawnMowers[i] = new LawnMower();

         */
    }

    /*
        METHODS

        ANYTHING THAT ACCESSES A SHARED RESOURCE
        IS NOW A SYNCHRONIZED METHOD (AT THE MOMENT IS THE grid[][] array)
        in placePlant, removePlant, getPlantAt, spawnZombie, isValidPosition
     */

    /* a method made to check if the current cell ur trying to place a plant at lies between the
     interval of rows and columns in the 2d array */
    public boolean isValidPosition(int row, int col)
    {
        return row >= 0 && row < ROWS && col >= 0 && col < COLUMNS && grid[row][col] == null;
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
    public synchronized void placePlant(Plant plant, AnchorPane root, int row, int col)
    {
        // Place the plant only if the position is valid: 1. no plant is on the cell, and 2. inside the yard
        if (isValidPosition(row, col))
        {
            // The grid is used to keep track whether the grid cell is taken up by a plant.
            grid[row][col] = plant;

            // Call the plants' subclass over-ridden appear function.
            plant.appear(root);

            // Audio for placing a plant.
            plantPlacedAudio();

            // For tracing
            System.out.println("Plant Placed Successfully at [" + row + "]" + "[" + col + "]");

            // Create a plant object thread, in order to intitate it's action!
            // plant.setAlive(true); -> No need i added it into appear of plant super class
            Thread plantThread = new Thread(plant);
            plantThread.start();
        }
        else
            System.out.println("Failed to place plant, one already exists at this cell.");
    }

    public synchronized Plant getPlantAt(int row, int col)
    {
        // Ensure the grid position contains a Plant instance
        if (grid[row][col] instanceof Plant)
            return (Plant) grid[row][col]; // Cast to Plant if it's an instance of Plant

        return null; // Return null if no plant found at the given coordinates
    }

    // shovel is used to remove plants from the grid
    public synchronized void removePlant(AnchorPane root, int row, int col)
    {
        // Find the plant in the specified grid cell
        Plant plantToRemove = getPlantAt(row, col); // Helper method to find the plant

        if (plantToRemove != null)
        {
            grid[row][col].disappear(root); // Now disappear removes from the root directly! (Notice changes in "Plant" class)
            grid[row][col] = null; // Clear the grid cell

            System.out.println("Plant removed at row: " + row + ", col: " + col);
        }
        else
            System.out.println("No plant found at row: " + row + ", col: " + col);

    }

    /* spawns a zombie, used setZombieSpawnInterval in seconds to detect how much would it
     take to spawn another zombie */
    public  void spawnZombie() throws InterruptedException
    {
        int[] specificNumbers = {158, 231, 308, 386, 468}; // Predefined Y positions for zombie spawn
        int minx = 957; // Minimum X position
        int maxx = 1202; // Maximum X position
        Random random = new Random();



        while (true)
        {
            Thread.sleep(zombieSpawnInterval * 1000); // Wait before spawning a new zombie
            int randomIndex = random.nextInt(specificNumbers.length); // Generate a random index for Y position
            int y = specificNumbers[randomIndex];
            int x = random.nextInt((maxx - minx) + 1) + minx; // Generate random X position within the defined range

            int z=random.nextInt((4-1)+1)+1;
            Zombie zombie;
            if(z==1){
                zombie = new DefaultZombie(x, y);
            }

            else if(z==2){
            zombie=new HelmetZombie(x,y);
            }
            else if(z==3){
                zombie=new ConeZombie(x,y-10);
            }
            else {
                zombie=new FootballZombie(x,y);
            }



           // Create a new zombie at the random position
            zombie.setAlive(true);

            // Added to be used with collision handling (with pea)
            Yard.zombies.add(zombie);

            if(zombie instanceof ConeZombie){
                zombie.appear(root, x, y); // Place the zombie on the yard

            }
            else{
                zombie.appear(root,x,y);
            }



            System.out.println("Zombie placed at x: " + x + ", y: " + y);
            new Thread(() -> {
                while (zombie.isAlive())
                {
                    zombie.move();

                    try {
                        Thread.sleep(100); // Control the speed of the zombie movement
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    @Override
    public void run()
    {
        try {
            spawnZombie();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

        // lawnMower.disappear(); commented out for now
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
        // Set AnchorPane size
        root.setPrefSize(WIDTH, HEIGHT);

        // Create ImageView for the yard background
        generateYardImageView(root);

        // Create Grid Pane
        GridPane yardGrid = generateGridPane(root);

        // Later we should add conditions based on level 1, 2, 3: A function to load the specific cards whether unlocked or locked and use gray cards

        // Generate cards on root pane // LEVEL 1 IS HARDCODED FOR NOW
        generateCards(3, root, yardGrid); // Grid is passed as parameter because button are used in eventHandling

        // Generate lawnmowers on root pane
        generateLawnMowers(root);

        label.setLayoutX(50); // Positioning the label
        label.setLayoutY(50);
        root.getChildren().add(label);

        generateSunCounter();

        // Create the scene and set it on the primary stage
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Sun sun = new Sun();
        sun.appear(root);
        Main.primaryStage.setScene(scene);
        this.start();
    }

    public void plantPlacedAudio() {
        try {
            String path = getClass().getResource("/music/plantplaced.mp3").toExternalForm();
            System.out.println("Path: " + path);
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(1.0);

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing planting sound: " + e.getMessage());
        }
    }

    public void plantSelectedAudio() {
        try {
            String path = getClass().getResource("/music/plant selected.mp3").toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing planting sound: " + e.getMessage());
        }
    }

    private void generateYardImageView(AnchorPane root)
    {
        ImageView yardImageView = new ImageView(new Image("images/others/Yard.png"));
        yardImageView.setFitWidth(WIDTH);
        yardImageView.setFitHeight(HEIGHT);

        // No need to preserve ratio, to make yard larger
        yardImageView.setPreserveRatio(false);

        // Add yard image view to the root pane
        root.getChildren().add(yardImageView);
    }

    private GridPane generateGridPane(AnchorPane root)
    {
        GridPane yardGrid = new GridPane();
        yardGrid.setPrefSize(680, 411);
        yardGrid.setLayoutX(227);
        yardGrid.setLayoutY(180);
        yardGrid.setGridLinesVisible(true);
        yardGrid.setVisible(false);

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

        // Returning yardGrid because it's used by other methods.
        return yardGrid;
    }

    // Later will add a parameter for level number and conditions inside.
    private void generateCards(int levelNumber, AnchorPane root, GridPane yardGrid)
    {
        // Create ImageView for the Wooden Box that holds the cards
        ImageView woodenBox = new ImageView(new Image("images/others/woodenBox(1).png"));
        // Specify its size
        woodenBox.setFitWidth(528);
        woodenBox.setFitHeight(320);
        // Specify its placement
        woodenBox.setLayoutX(227);
        woodenBox.setLayoutY(14);
        // Preserve Ratio, otherwise a corrupted image appears
        woodenBox.setPreserveRatio(true);
        root.getChildren().add(woodenBox);

        // SHOVEL CARD (Used card class as a workaround)
        Card SHOVELCARD=new Card(
                "images/cards/shovel.png",
                "images/others/shovel.png",
                null,
                0
        ); // NULL is used as a workaround to avoid creating a shovel class
        SHOVELCARD.cardImageViewSetProperties(681,14,80,88,true,true);
        SHOVELCARD.draggingImageViewSetProperties(61,70,true,false);
        SHOVELCARD.hoverImageViewSetProperties(61,70,true,false);
        SHOVELCARD.addToYard(root,yardGrid,this);

        // PEASHOOTER CARD
        Card PEASHOOTERCARD = new Card(
                "images/cards/peashooterCard.png",
                "images/plants/peashooter.png",
                Peashooter.class,
                100
        );
        PEASHOOTERCARD.cardImageViewSetProperties(304, 21, 47, 71, true, true);
        PEASHOOTERCARD.draggingImageViewSetProperties(61, 74, true, false);
        PEASHOOTERCARD.hoverImageViewSetProperties(61, 74, true, false);
        PEASHOOTERCARD.addToYard(root, yardGrid, this);

        // SUNFLOWER CARD
        Card SUNFLOWERCARD = new Card(
                "images/cards/sunflowerCard.png",
                "images/plants/sunflower.png",
                Sunflower.class,
                50
        );
        SUNFLOWERCARD.cardImageViewSetProperties(358, 21, 47, 66, true, true);
        SUNFLOWERCARD.draggingImageViewSetProperties(61, 66, true, false);
        SUNFLOWERCARD.hoverImageViewSetProperties(61, 66, true, false);
        SUNFLOWERCARD.addToYard(root, yardGrid, this);

        // POTATO CARD
        Card POTATOCARD = new Card(
                "images/cards/potatoCard.png",
                "images/plants/potato.png",
                Potato.class,
                50
        );
        POTATOCARD.cardImageViewSetProperties(413, 21, 47, 66, true, true);
        POTATOCARD.draggingImageViewSetProperties(59, 66, true, false);
        POTATOCARD.hoverImageViewSetProperties(59, 66, true, false);
        POTATOCARD.addToYard(root, yardGrid, this);

        // CHERRY CARD
        Card CHERRYCARD = new Card(
                "images/cards/cherryCard.png",
                "images/plants/cherry.png",
                Cherry.class,
                150
        );
        CHERRYCARD.cardImageViewSetProperties(466, 21, 47, 66, true, true);
        CHERRYCARD.draggingImageViewSetProperties(80, 70, true, false);
        CHERRYCARD.hoverImageViewSetProperties(80, 70, true, false);

        // ICED PEA CARD
        Card ICEDPEACARD = new Card(
                "images/cards/icedpeashooterCard.png",
                "images/plants/icedpeashooter.png",
                IcedPea.class,
                150
        );
        ICEDPEACARD.cardImageViewSetProperties(520, 21, 47, 66, true, true);
        ICEDPEACARD.draggingImageViewSetProperties(90, 70, true, false);
        ICEDPEACARD.hoverImageViewSetProperties(90, 70, true, false);

        // JALAPENO CARD
        Card JALAPENOCARD = new Card(
                "images/cards/jalapenoCard.png",
                "images/plants/jalapeno.png",
                Jalepeno.class,
                125
        );
        JALAPENOCARD.cardImageViewSetProperties(573, 21, 47, 66, true, true);
        JALAPENOCARD.draggingImageViewSetProperties(41, 78, true, false);
        JALAPENOCARD.hoverImageViewSetProperties(41, 78, true, false);

        // REPEATER CARD
        Card REPEATERCARD = new Card(
                "images/cards/repeaterCard.png",
                "images/plants/repeater.png",
                Repeater.class,
                200
        );
        REPEATERCARD.cardImageViewSetProperties(627, 21, 47, 66, true, true);
        REPEATERCARD.draggingImageViewSetProperties(61, 107, true, false);
        REPEATERCARD.hoverImageViewSetProperties(61, 107, true, false);

        // LOCKED CARDS
        Card cherryLockedCard = new Card("images/lockedCards/cherryLockedCard.png");
        cherryLockedCard.cardImageViewSetProperties(468, 21, 47, 66, true, true);

        Card snowpeaLockedCard = new Card("images/lockedCards/snowpeaLockedCard.png");
        snowpeaLockedCard.cardImageViewSetProperties(520, 21, 47, 66, true, true);

        Card jalapenoLockedCard = new Card("images/lockedCards/jalapenoLockedCard.png");
        jalapenoLockedCard.cardImageViewSetProperties(573, 21, 47, 66, true, true);

        Card repeaterLockedCard = new Card("images/lockedCards/repeaterLockedCard.png");
        repeaterLockedCard.cardImageViewSetProperties(626, 21, 47, 66, true, true);

        // Switch the level number to display correct cards for each level
        switch(levelNumber)
        {
            case 1:
                // Add all locked cards to the root pane.
                root.getChildren().addAll(cherryLockedCard.getCardImageView(), snowpeaLockedCard.getCardImageView(), jalapenoLockedCard.getCardImageView(), repeaterLockedCard.getCardImageView());
                break;
            case 2:
                // Only last 2 cards are not unlocked
                root.getChildren().addAll(jalapenoLockedCard.getCardImageView(), repeaterLockedCard.getCardImageView());
                CHERRYCARD.addToYard(root, yardGrid, this);
                ICEDPEACARD.addToYard(root, yardGrid, this);

                break;
            case 3:
                // All cards are unlocked in level 3
                System.out.println("All cards are unlocked in " + levelNumber);
                CHERRYCARD.addToYard(root, yardGrid, this);
                ICEDPEACARD.addToYard(root, yardGrid, this);
                JALAPENOCARD.addToYard(root, yardGrid, this);
                REPEATERCARD.addToYard(root, yardGrid, this);
                break;
        }
    }

    private void generateLawnMowers(AnchorPane root)
    {
        // Create Lawn mowers on the screen.
        lawnMowers = new LawnMower[ROWS];
        for (int i = 0; i < ROWS; i++)
        {
            // Create instance
            lawnMowers[i] = new LawnMower();
            lawnMowers[i].getElementImage().setLayoutX(155);
        }

        // Add lawnmowers to the root pane.
        lawnMowers[0].getElementImage().setLayoutY(186);
        lawnMowers[1].getElementImage().setLayoutY(268);
        lawnMowers[2].getElementImage().setLayoutY(342);
        lawnMowers[3].getElementImage().setLayoutY(419);
        lawnMowers[4].getElementImage().setLayoutY(501);

        root.getChildren().addAll(lawnMowers[0].getElementImage(),lawnMowers[1].getElementImage(),lawnMowers[2].getElementImage(),lawnMowers[3].getElementImage(),lawnMowers[4].getElementImage());
    }

    public void generateSunCounter()
    {
        // Make sure sunCounterLabel is initialized and positioned properly
        // Only set the initial label if it's not set yet
            Yard.label.setText(String.valueOf(sunCounter)); // Initial value of sun counter
            Yard.label.setStyle("-fx-font-size: 20px; -fx-text-fill: black; -fx-font-weight: bold;"); // Style the label
            Yard.label.setLayoutX(248); // Position on the X-axis
            Yard.label.setLayoutY(65); // Position on the Y-axis


        // Add the label to the root pane to ensure it's visible on the screen
        if (!root.getChildren().contains(Yard.label))
        {
            root.getChildren().add(Yard.label); // Add the label to the scene (root pane)
        }
    }

}