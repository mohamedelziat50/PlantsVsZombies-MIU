import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Yard extends Thread
{
    public static Level parentLevel;

    // YARD CONSTANT VARIABLES
    public static final int ROWS = 5, COLUMNS = 9, WIDTH = 1278, HEIGHT = 650;

    // IMPORTANT ARRAYS FOR GAMEPLAY TRACKING OF PLANTS & LAWNMOWERS
    public static volatile Characters[][] grid; // Used Placement of plants
    private LawnMower[] lawnMowers;

    // Used for collision handling between plants/peas & zombies
    public static volatile ArrayList<Zombie> zombies = new ArrayList<>(); // Collision With peas
    public static volatile ArrayList<Plant> plants = new ArrayList<>(); // Collision with plants (zombies side)
    public static volatile ArrayList<Pea> peas = new ArrayList<>(); // Collision with plants (zombies side)
    private ArrayList<ImageView> staticZombies = new ArrayList<>();

    // Variables specific to each level!
    public static volatile boolean gameOn = true;
    private static int zombieSpawnInterval;
    private double levelDuration; // Total duration for the level (in seconds)
    public static double timeLeft;
    public static int sunCounter;
    private Timeline timeline; // Declare timeline as a class-level variable


    // GUI-related variables
    public static AnchorPane root;
    public static Label label; // Related for sun counter
    private ProgressBar levelProgressBar;  // The progress bar to track level duration

    /* constructor, to initialize the 2d array of type Characters, in which plants and zombies inherit from.
    also is used to make instance of the lawn mowers at the beginning of each row.*/
    public Yard(Level parentLevel)
    {
        // Parent level
        this.parentLevel = parentLevel;

        // Root pane that has everything on it
        root = new AnchorPane();

        // Zombie Spawn Interval used in spawnZombie()
        // zombieSpawnInterval = 4 ;
        zombieSpawnInterval= 17;

        // Initialize Characters 2D Array to keep a-hold of Zombies, Plants, LawnMower, and possibly peas.
        grid = new Characters[ROWS][COLUMNS];

        // Clear previous plants/zombies arraylists in case started multiple levels in the same run.
        plants.clear();
        zombies.clear();

        // Level specific stuff
       // levelDuration = parentLevel.getDurationInSeconds();
        timeLeft = 3 * 60;// the game is 4 minutes (4sec*60=4min) for those who don't know
        sunCounter=50;

        // 50 doesn't matter, the sun counter replaces it
        label = new Label("50");
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

            // Add x and y to be used with collision handling
            plant.setX(row);
            plant.setY(col);

            // Also add the ArrayList of plants to handle collision
            synchronized (plants)
            {
                plants.add(plant);
            }

            // Call the plants' subclass over-ridden appear function.
            plant.appear(root);

            // If it's a Sunflower, start producing suns
            if (plant instanceof Sunflower) {
                Sunflower sunflower = (Sunflower) plant;  // Safe cast
                sunflower.startSunProduction(root);  // Pass both parameters
            }


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
            synchronized (plants)
            {
                // Remove from the plants list
                plants.remove(plantToRemove);
            }

            grid[row][col].disappear(root); // Now disappear removes from the root directly! (Notice changes in "Plant" class)
            grid[row][col] = null; // Clear the grid cell
            shovelPlantAudio();

            System.out.println("Plant removed at row: " + row + ", col: " + col);
        }
        else
            System.out.println("No plant found at row: " + row + ", col: " + col);

    }

    /* spawns a zombie, used setZombieSpawnInterval in seconds to detect how much would it
     take to spawn another zombie */
    public void spawnZombie() throws InterruptedException
    {
        int[] specificNumbers = {134, 207, 298, 376, 468}; // Predefined Y positions for zombie spawn
        int minx = 957; // Minimum X position
        int maxx = 1202; // Maximum X position
        Random random = new Random();

        int minSpawnInterval = 2; // Minimum spawn interval in seconds
        int spawnIntervalDecreaseRate = 1; // Amount to decrease spawn interval per minute
        long startTime = System.currentTimeMillis();

        while (gameOn&&timeLeft>0)
        {
            // Decrease the spawn interval dynamically over time
            long elapsedMinutes = (System.currentTimeMillis() - startTime) / 45000; // Calculate elapsed minutes
            zombieSpawnInterval = Math.max(minSpawnInterval, zombieSpawnInterval - (int) (elapsedMinutes * spawnIntervalDecreaseRate));

            try {
                Thread.sleep(zombieSpawnInterval * 1000); // Wait before spawning a new zombie
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!gameOn||timeLeft<0)
            {
                zombieSpawnInterval= 17;
                break;
            }

            int randomIndex = random.nextInt(specificNumbers.length); // Generate a random index for Y position
            int y = specificNumbers[randomIndex];
            int x = random.nextInt((maxx - minx) + 1) + minx; // Generate random X position within the defined range

            Zombie zombie = null;
            // Check the level and set the zombie type based on the level number
            if (this.parentLevel.getLevelNumber() == 1) {
                // Level 1: Choose between DefaultZombie or HelmetZombie
                int z = random.nextInt(2) + 1; // Random number between 1 and 2
                if (z == 1) {
                    zombie = new DefaultZombie(x, y);
                } else {
                    zombie = new ConeZombie(x, y);
                }
            } else if (this.parentLevel.getLevelNumber() == 2) {
                // Level 2: Choose between DefaultZombie, HelmetZombie, or ConeZombie
                int z = random.nextInt(3) + 1; // Random number between 1 and 3
                if (z == 1) {
                    zombie = new DefaultZombie(x, y);
                } else if (z == 2) {
                    zombie = new HelmetZombie(x, y);
                } else {
                    zombie = new ConeZombie(x, y);
                }
            } else if (this.parentLevel.getLevelNumber() == 3) {
                // Level 3: Choose between DefaultZombie, HelmetZombie, ConeZombie, or FootballZombie
                int z = random.nextInt(4) + 1; // Random number between 1 and 4
                if (z == 1) {
                    zombie = new DefaultZombie(x, y);
                } else if (z == 2) {
                    zombie = new HelmetZombie(x, y);
                } else if (z == 3) {
                    zombie = new ConeZombie(x, y);
                } else {
                    zombie = new FootballZombie(x, y);
                }
            }

            // If zombie is still null (though it shouldn't happen with above conditions), assign a default zombie
            if (zombie == null) {
                zombie = new DefaultZombie(x, y);
            }

            // Create a new zombie at the random position
            zombie.setAlive(true);

            // Added to be used with collision handling (with pea)
            zombies.add(zombie);

            // Run the zombie appearance and audio in the UI thread
            Zombie finalZombie = zombie;
            Platform.runLater(() -> {
                if(gameOn)
                {
                    finalZombie.appear(root, x, y);
                    zombieSpawnAudio();
                }
            });

            System.out.println("Zombie placed at x: " + x + ", y: " + y);

            // Create a new thread for the zombie movement
            Zombie finalZombie1 = zombie;
            new Thread(() -> {
                while (gameOn && finalZombie1.isAlive()) {
                    finalZombie1.move();

                    // Check if this specific lawnmower intersects with the zombie
                    for (int i = 0; i < ROWS; i++) {
                        if (lawnMowers[i] != null && !lawnMowers[i].isActive()) {
                            // Get the bounds of the lawnmower
                            double lawnMowerLeft = lawnMowers[i].elementImage.getLayoutX();
                            double lawnMowerRight = lawnMowers[i].elementImage.getLayoutX() + lawnMowers[i].elementImage.getFitWidth();
                            double lawnMowerTop = lawnMowers[i].elementImage.getLayoutY();
                            double lawnMowerBottom = lawnMowers[i].elementImage.getLayoutY() + lawnMowers[i].elementImage.getFitHeight();

                            // Get the bounds of the zombie
                            double zombieCenterY = finalZombie1.getElementImage().getLayoutY() + (finalZombie1.getElementImage().getFitHeight() / 2);

                            // Check if the zombie is within the bounds of this lawnmower's row
                            if (zombieCenterY >= lawnMowerTop && zombieCenterY <= lawnMowerBottom &&
                                    finalZombie1.getElementImage().getBoundsInParent().intersects(
                                            lawnMowerLeft,
                                            lawnMowerTop,
                                            lawnMowerRight - lawnMowerLeft,
                                            lawnMowerBottom - lawnMowerTop
                                    )) {
                                System.out.println("Zombie intersected with lawnmower at row: " + i);
                                lawnMowers[i].activate(root); // Activate the lawnmower
                                break; // Exit the loop to ensure only one lawnmower is activated
                            }
                        }
                    }

                    try {
                        Thread.sleep(20); // Control the speed of the zombie movement
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void resetGame()
    {
        // Reset game state variables
        gameOn = true;
        zombieSpawnInterval= 15;
        sunCounter= 50;
        timeLeft= 4 * 60;

            // Clear all plants and set them inactive
            plants.forEach(plant -> {
                if(plants!=null){
                    Platform.runLater(() -> {
                    plant.disappear(root);
                    });
                }
            });
            plants.clear();


                peas.forEach(pea -> {
                    if(pea!=null){
                        Platform.runLater(() -> {
                        pea.disappear(root);
                        });

                    }
                });

                peas.clear();
                // Clear all peas


            // Clear all zombies and set them inactive
            zombies.forEach(zombie -> {
                if(zombie!=null)
                {
                    Platform.runLater(() -> {
                        zombie.disappear(root);
                    });
                }

            });
            zombies.clear();

        //Making sure that all the indexes in the grid pane is empty now
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLUMNS;j++){
                if(grid[i][j]!=null){
                    System.out.println("Plant Removed");
                    grid[i][j].disappear(root);
                    grid[i][j]=null;
                }

            }
        }

        // Reset other game-related elements
        root.getChildren().clear(); // Remove all nodes from the yard
        root = new AnchorPane();    // Reinitialize root
    }


    public static void gameOver()
    {
        gameOn = false;

        Platform.runLater(() -> {
            for(int i=0;i<ROWS;i++){
                for(int j=0;j<COLUMNS;j++){
                    if(grid[i][j]!=null){
                        System.out.println("Plant removed");
                        grid[i][j].disappear(root);
                        grid[i][j]=null;
                    }
                }
            }

            LoadingScreen.show(MainGUI.primaryStage);
        });

        System.out.println("You Lost");
        System.out.println("Game has ended, all zombie spawns and threads should stop");
    }

    public static void gameWin()
    {
        gameOn = false;

        Platform.runLater(() -> {
            //  resetGame(); // Reset the game state
            for(int i=0;i<ROWS;i++){
                for(int j=0;j<COLUMNS;j++){
                    if(grid[i][j]!=null){
                        System.out.println("game reset");
                        grid[i][j].disappear(root);
                        grid[i][j]=null;
                    }
                }
            }

            LoadingScreen.show(MainGUI.primaryStage);
        });

        System.out.println("You Won");
        System.out.println("Game has ended, all zombie spawns and threads should stop");
    }

    public static void startNewGame()
    {
        resetGame(); // Clear the game state first

        // Initialize root if not already done
        root = new AnchorPane();
        Yard.root = root; // Make root globally accessible if necessary

    }



    @Override
    public void run()
    {
        try {
            spawnZombie();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void createLevelDurationBar(AnchorPane root)
    {
        // Load the background image
        Image backgroundImage = new Image("images/yard-related/progressBar.png");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(180);  // Set the width of the background image
        backgroundImageView.setFitHeight(30); // Set the height of the background image
        backgroundImageView.setLayoutX(720);   // Adjust X position
        backgroundImageView.setLayoutY(603);   // Adjust Y position

        // Create the progress bar
        levelProgressBar = new ProgressBar(1.0);  // Start with full progress
        levelProgressBar.setPrefWidth(137);
        levelProgressBar.setPrefHeight(11);
        levelProgressBar.setStyle("-fx-accent: green; -fx-background-color: transparent;");
        levelProgressBar.setLayoutX(725);  // Match the layout to align with the background
        levelProgressBar.setLayoutY(612);

        // Add the components to the root layout
        root.getChildren().addAll(backgroundImageView, levelProgressBar);
    }


    // Start the level timer and update the progress bar
    public void startLevelTimer()
    {
        // Initialize the timeline
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (timeLeft > 0 && Yard.gameOn)
            {
                timeLeft -= 1;  // Decrease the remaining time by 1 second
                System.out.println("Time left: " + timeLeft);
                levelProgressBar.setProgress(levelProgressBar.getProgress() - 0.01);  // Update the progress bar
            }
            else {
                if(timeLeft <= 0)
                    gameWin(); // Call the loading screen for the main menu


                // Level is over, stop the timeline
                timeline.stop();

                // Handle level completion logic here
                System.out.println("Level Completed!");

            }
        }));

        // Set the timeline to repeat indefinitely (so it updates every second)
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline (the countdown)
        timeline.play();
    }

    private void readySetPlant() {
        // Create a semi-transparent rectangle overlay
        Rectangle overlay = new Rectangle(WIDTH, HEIGHT);
        overlay.setFill(Color.BLACK);
        overlay.setOpacity(0.5);

        // Load images for "READY", "SET", and "PLANT"
        ImageView readyImage = new ImageView(new Image("images/others/Ready.png"));
        ImageView setImage = new ImageView(new Image("images/others/Set.png"));
        ImageView plantImage = new ImageView(new Image("images/others/Plant.png"));
        readyImage.setPreserveRatio(true);
        setImage.setPreserveRatio(true);
        plantImage.setPreserveRatio(true);

        // Set the size of the images
        readyImage.setFitWidth(400);
        readyImage.setFitHeight(130);
        setImage.setFitWidth(400);
        setImage.setFitHeight(130);
        plantImage.setFitWidth(400);
        plantImage.setFitHeight(130);

        // Set initial position of the images (centered)
        readyImage.setLayoutX((WIDTH / 2 - readyImage.getFitWidth() / 2) + 60);
        readyImage.setLayoutY(HEIGHT / 2 - readyImage.getFitHeight() / 2);

        setImage.setLayoutX((WIDTH / 2 - setImage.getFitWidth() / 2) + 60);
        setImage.setLayoutY(HEIGHT / 2 - setImage.getFitHeight() / 2);

        plantImage.setLayoutX((WIDTH / 2 - plantImage.getFitWidth() / 2) + 60);
        plantImage.setLayoutY(HEIGHT / 2 - plantImage.getFitHeight() / 2);

        // Add the overlay and "READY" image to the screen
        root.getChildren().addAll(overlay, readyImage);

        // Show "READY" for 1.5 seconds
        PauseTransition readyPause = new PauseTransition(Duration.seconds(1.5));
        readyPause.setOnFinished(event -> {
            root.getChildren().remove(readyImage);
            root.getChildren().add(setImage);

            // Show "SET" for 1.5 seconds
            PauseTransition setPause = new PauseTransition(Duration.seconds(1.5));
            setPause.setOnFinished(setEvent -> {
                root.getChildren().remove(setImage);
                root.getChildren().add(plantImage);

                // Show "PLANT!" for 1.5 seconds
                PauseTransition plantPause = new PauseTransition(Duration.seconds(1.5));
                plantPause.setOnFinished(goEvent -> {
                    // Create shake and zoom effects
                    Timeline shakeTimeline = new Timeline();
                    Random random = new Random();

                    // Generate random shake and scale
                    for (int i = 0; i < 20; i++) { // 20 keyframes for a more dramatic effect
                        double randomX = random.nextDouble() * 20 - 10; // Random X offset (-10 to 10)
                        double randomY = random.nextDouble() * 20 - 10; // Random Y offset (-10 to 10)
                        double randomScale = 1 + random.nextDouble() * 0.2; // Random scale (1 to 1.2)
                        double randomAngle = random.nextDouble() * 20 - 10; // Random rotation (-10 to 10 degrees)

                        KeyFrame keyFrame = new KeyFrame(
                                Duration.millis(i * 50), // Every 50ms
                                new KeyValue(plantImage.layoutXProperty(), (WIDTH / 2 - plantImage.getFitWidth() / 2) + 60 + randomX),
                                new KeyValue(plantImage.layoutYProperty(), (HEIGHT / 2 - plantImage.getFitHeight() / 2) + randomY),
                                new KeyValue(plantImage.scaleXProperty(), randomScale),
                                new KeyValue(plantImage.scaleYProperty(), randomScale),
                                new KeyValue(plantImage.rotateProperty(), randomAngle)
                        );
                        shakeTimeline.getKeyFrames().add(keyFrame);
                    }

                    // When the shake is done, remove the "PLANT!" image and overlay
                    shakeTimeline.setOnFinished(shakeEvent -> {
                        root.getChildren().removeAll(plantImage, overlay);
                        Sun sun = new Sun();
                        sun.appear(root);
                    });

                    shakeTimeline.play();
                });
                plantPause.play();
            });
            setPause.play();
        });
        readyPause.play();
    }


    private void hugeWaveText() {
        ImageView waveImage = new ImageView(new Image("images/others/HugeWave.gif"));
        waveImage.setFitWidth(500);
        waveImage.setFitHeight(120);
        waveImage.setLayoutX(WIDTH / 2 - waveImage.getFitWidth() / 2);
        waveImage.setLayoutY(HEIGHT / 2 - waveImage.getFitHeight() / 2);
        root.getChildren().add(waveImage);
        PauseTransition wavePause = new PauseTransition(Duration.seconds(1.5));
        wavePause.setOnFinished(event -> {
            root.getChildren().remove(waveImage);
        });
        wavePause.play();
    }

    public static void preloadZombies()
    {
        for (int i = 0; i < 8; i++)
        {
            ImageView zombie = new ImageView("images/yardStaticZombies/" + i + ".gif");
            // You can cache these images here if needed, like storing them in an ArrayList or a Map
        }
    }

    // Method to show static zombie images
    public void showStaticZombies()
    {
        Platform.runLater(() -> {
            // Clear any existing zombie images to prevent duplicates
            staticZombies.forEach(zombie -> root.getChildren().remove(zombie));
            staticZombies.clear();

            // Add static zombie images
            for (int i = 0; i < 8; i++)
            {
                ImageView zombie = new ImageView(new Image("images/yardStaticZombies/" + i  + ".gif")); // Replace with actual paths
                zombie.setFitWidth(127); // Adjust dimensions
                zombie.setFitHeight(164);
                zombie.setPreserveRatio(true);
                int offsetY = 163;

                if(i==0)
                    zombie.setLayoutY(130); // Staggered layout for better visuals
                else if(i==1)
                    zombie.setLayoutY(170); // Staggered layout for better visuals
                else if(i==2)
                    zombie.setLayoutY(210); // Staggered layout for better visuals
                else if(i==3)
                    zombie.setLayoutY(250); // Staggered layout for better visuals
                else if(i==4)
                    zombie.setLayoutY(290); // Staggered layout for better visuals
                else if(i==5)
                    zombie.setLayoutY(330); // Staggered layout for better visuals
                else if(i==6)
                    zombie.setLayoutY(370); // Staggered layout for better visuals
                else if(i==7)
                    zombie.setLayoutY(410); // Staggered layout for better visuals


                if(i%2==0)
                {
                    zombie.setLayoutX(950);
                }
                else
                {
                    zombie.setLayoutX(1060);
                }

                staticZombies.add(zombie);
                root.getChildren().add(zombie);
            }
        });
    }

    // Method to remove static zombies
    private void removeStaticZombies() {
        Platform.runLater(() -> {
            staticZombies.forEach(zombie -> root.getChildren().remove(zombie));
            staticZombies.clear();
        });
    }


    private void zoomAndReveal()
    {
        // Initial freeze for 2 seconds
        PauseTransition initialFreeze = new PauseTransition(Duration.seconds(2));

        // Animation to zoom into the left side
        Timeline zoomToRight = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(root.scaleXProperty(), 1),
                        new KeyValue(root.scaleYProperty(), 1),
                        new KeyValue(root.layoutXProperty(), 0)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(root.scaleXProperty(), 1.5), // Zoom in
                        new KeyValue(root.scaleYProperty(), 1.5),
                        new KeyValue(root.layoutXProperty(), -WIDTH * 0.25) // Focus on the left
                )
        );

        // Animation to pan to the right while staying zoomed
        Timeline zoomToLeft = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(root.layoutXProperty(), -WIDTH * 0.25)
                ),
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(root.layoutXProperty(), WIDTH * 0.25) // Pan to the right
                )
        );

        // Freeze on the zoomed-in right position for 4 seconds
        PauseTransition freezeOnRight = new PauseTransition(Duration.seconds(4.5));
        zoomToLeft.setOnFinished(event -> removeStaticZombies()); // Remove zombies during this pause

        // Animation to zoom out and show the whole scene
        Timeline zoomOut = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(root.scaleXProperty(), 1.5),
                        new KeyValue(root.scaleYProperty(), 1.5),
                        new KeyValue(root.layoutXProperty(), WIDTH * 0.25)
                ),
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(root.scaleXProperty(), 1), // Zoom out
                        new KeyValue(root.scaleYProperty(), 1),
                        new KeyValue(root.layoutXProperty(), 0) // Reset layout to the original
                )
        );


        // Sequence the animations
        SequentialTransition sequence = new SequentialTransition(
                initialFreeze,
                zoomToRight,
                freezeOnRight,
                zoomToLeft,
                zoomOut
        );

        // On completion, call the readySetPlant method
        sequence.setOnFinished(event -> readySetPlant());

        // Start the sequence
        sequence.play();
    }

    // Added function called to display the yard when the level starts.
    public void displayYard()
    {
        showStaticZombies();
        startNewGame();
        zoomAndReveal();

        // Set AnchorPane size
        root.setPrefSize(WIDTH, HEIGHT);

        // Create ImageView for the yard background
        generateYardImageView(root);

        //Create progress bar
        if (levelProgressBar != null)
            root.getChildren().add(levelProgressBar);

        // This will not be used but just leave it
        createLevelDurationBar(root);

        // Create Grid Pane
        GridPane yardGrid = generateGridPane(root);

        // Later we should add conditions based on level 1, 2, 3: A function to load the specific cards whether unlocked or locked and use gray cards

        // Generate cards on root pane // LEVEL 1 IS HARDCODED FOR NOW
        generateCards(parentLevel.getLevelNumber(), root, yardGrid); // Grid is passed as parameter because button are used in eventHandling

        // Generate lawnmowers on root pane
        generateLawnMowers(root);

        // Generate the dynamic label which holds the sun counter for the play
        label.setLayoutX(50); // Positioning the label for the sun counter
        label.setLayoutY(50);
        root.getChildren().add(label);

        generateSunCounter();

        zombiesArrivalAudio();

        startLevelTimer();

        // Create the scene and set it on the primary stage
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setCursor(new ImageCursor(new Image("images/others/cursor.png")));

        MainGUI.primaryStage.setScene(scene);
        // Added this to center on screen once switched
        MainGUI.primaryStage.centerOnScreen();


        this.start();
    }

    public void plantPlacedAudio() {
        try {
            String path = getClass().getResource("/music/plant placed.mp3").toExternalForm();
            System.out.println("Path: " + path);
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing planting sound: " + e.getMessage());
        }
    }

    public void shovelPlantAudio() {
        try {
            String path = getClass().getResource("/music/shovel plant.mp3").toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing shovel sound: " + e.getMessage());
        }
    }

    public void zombiesArrivalAudio() {
        try {
            String path = getClass().getResource("/music/zombies arrive.mp3").toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing zombie sound: " + e.getMessage());
        }
    }

    private static MediaPlayer zombieSpawnMediaPlayer; // Keep a reference to the MediaPlayer for zombie spawn sound

    public void zombieSpawnAudio() {
        try {
            String[] audioPaths = {
                    getClass().getResource("/music/zombie s1.mp3").toExternalForm(),
                    getClass().getResource("/music/zombie s2.mp3").toExternalForm(),
                    getClass().getResource("/music/zombie s3.mp3").toExternalForm()
            };

            // Randomly select one of the audio paths
            Random random = new Random();
            int randomIndex = random.nextInt(audioPaths.length); // Random index between 0 and 2
            String selectedAudioPath = audioPaths[randomIndex];
            Media media = new Media(selectedAudioPath);

            // Create a new MediaPlayer for the selected audio
            zombieSpawnMediaPlayer = new MediaPlayer(media);
            zombieSpawnMediaPlayer.setVolume(0.3); // Adjust volume as needed
            zombieSpawnMediaPlayer.play(); // Play the selected audio

        } catch (Exception e) {
            System.out.println("Error playing zombie spawn sound: " + e.getMessage());
        }
    }

    public void zombieWaveAudio() {
        try {
            String path = getClass().getResource("/music/zombie wave.mp3").toExternalForm();
            System.out.println("Path: " + path);
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing zombie wave sound: " + e.getMessage());
        }
    }

    private void generateYardImageView(AnchorPane root)
    {
        ImageView yardImageView = new ImageView(new Image("images/yard-related/Yard.png"));

        switch(parentLevel.getLevelNumber())
        {
            case 2:
                yardImageView = new ImageView(new Image("images/yard-related/nightYard.png"));
                break;
            case 3:
                yardImageView = new ImageView(new Image("images/yard-related/ChristmasYard.png"));

        }

        // Christmas yard has specific dimensions so there has to be specific dimensions
        if(parentLevel.getLevelNumber() == 3)
        {
            yardImageView.setLayoutX(-238);
            yardImageView.setLayoutY(0);
            yardImageView.setFitWidth(1766);
            yardImageView.setFitHeight(666);
            // Add yard image view to the root pane
            root.getChildren().add(yardImageView);
        }
        else if(yardImageView != null)
        {
            yardImageView.setFitWidth(WIDTH);
            yardImageView.setFitHeight(HEIGHT);

            // No need to preserve ratio, to make yard larger
            yardImageView.setPreserveRatio(false);

            // Add yard image view to the root pane
            root.getChildren().add(yardImageView);
        }
        else
        {
            System.err.println("Yard Image View not loaded correctly.");
            System.exit(1);
        }
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
        ImageView woodenBox = new ImageView(new Image("images/yard-related/woodenBox-photoshop.png"));
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
                "images/plants/icedpeashooter.gif",
                IcedPeashooter.class,
                175
        );
        ICEDPEACARD.cardImageViewSetProperties(520, 21, 47, 66, true, true);
        ICEDPEACARD.draggingImageViewSetProperties(73, 78, true, false);
        ICEDPEACARD.hoverImageViewSetProperties(73, 78, true, false);


        Card TORCHWOODCARD = new Card(
                "images/cards/torchwoodCard.png",
                "images/plants/torchWood.png",
                TorchWood.class,
                175
        ); // NULL is used as a workaround to avoid creating a shovel class
        TORCHWOODCARD.cardImageViewSetProperties(573,21,47,66,true,true);
        TORCHWOODCARD.draggingImageViewSetProperties(73,78,true,false);
        TORCHWOODCARD.hoverImageViewSetProperties(64,78,true,false);

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

        Card torchWoodLockedCard = new Card("images/lockedCards/torchWoodLockedCard.png");
        torchWoodLockedCard.cardImageViewSetProperties(573, 21, 47, 66, true, true);

        Card repeaterLockedCard = new Card("images/lockedCards/repeaterLockedCard.png");
        repeaterLockedCard.cardImageViewSetProperties(626, 21, 47, 66, true, true);

        // Switch the level number to display correct cards for each level
        switch(levelNumber)
        {
            case 1:
                PEASHOOTERCARD.addToYard(root, yardGrid, this);
                SUNFLOWERCARD.addToYard(root, yardGrid, this);
                POTATOCARD.addToYard(root, yardGrid, this);

                // Add all locked cards to the root pane.
                root.getChildren().addAll(cherryLockedCard.getCardImageView(), snowpeaLockedCard.getCardImageView(), torchWoodLockedCard.getCardImageView(), repeaterLockedCard.getCardImageView());
                break;
            case 2:
                PEASHOOTERCARD.addToYard(root, yardGrid, this);
                SUNFLOWERCARD.addToYard(root, yardGrid, this);
                POTATOCARD.addToYard(root, yardGrid, this);
                CHERRYCARD.addToYard(root, yardGrid, this);
                ICEDPEACARD.addToYard(root, yardGrid, this);

                // Only last 2 cards are not unlocked
                root.getChildren().addAll(torchWoodLockedCard.getCardImageView(), repeaterLockedCard.getCardImageView());
                break;
            case 3:
                // All cards are unlocked in level 3
                System.out.println("All cards are unlocked in " + levelNumber);

                // Set the imageview to be christmas as a workaround and for show only
                PEASHOOTERCARD.setCardImageView(new ImageView("images/cards/PeaShooterCard_christmas.png"));
                PEASHOOTERCARD.cardImageViewSetProperties(305, 21, 44, 62, true, true);

                PEASHOOTERCARD.addToYard(root, yardGrid, this);
                CHERRYCARD.addToYard(root, yardGrid, this);
                ICEDPEACARD.addToYard(root, yardGrid, this);
                TORCHWOODCARD.addToYard(root, yardGrid, this);
                REPEATERCARD.addToYard(root, yardGrid, this);

                // Add Christmas cards
                // SUNFLOWER CHRISTMAS CARD
                Card SUNFLOWERCARD_CHRISTMAS = new Card(
                        "images/cards/sunflowerCard_christmas.png",
                        "images/plants/sunflower.png",
                        Sunflower_Christmas.class,
                        50
                );
                SUNFLOWERCARD_CHRISTMAS.cardImageViewSetProperties(360, 22, 44, 66, true, true);
                SUNFLOWERCARD_CHRISTMAS.draggingImageViewSetProperties(61, 66, true, false);
                SUNFLOWERCARD_CHRISTMAS.hoverImageViewSetProperties(61, 66, true, false);
                SUNFLOWERCARD_CHRISTMAS.addToYard(root, yardGrid, this);

                Card POTATO_CHRISTMAS = new Card(
                        "images/cards/potatoCard_christmas.png",
                        "images/plants/potato.png",
                        Potato_Christmas.class,
                        50
                );
                POTATO_CHRISTMAS.cardImageViewSetProperties(415, 21, 44, 66, true, true);
                POTATO_CHRISTMAS.draggingImageViewSetProperties(61, 66, true, false);
                POTATO_CHRISTMAS.hoverImageViewSetProperties(61, 66, true, false);
                POTATO_CHRISTMAS.addToYard(root, yardGrid, this);

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
            lawnMowers[i] = new LawnMower(i);
            lawnMowers[i].getElementImage().setLayoutX(157);
        }

//        lawnMowers[1].elementImage.setLayoutX(160);
//        lawnMowers[2].elementImage.setLayoutX(140);
//        lawnMowers[3].elementImage.setLayoutX(150);


        // Add lawnmowers to the root pane.
        lawnMowers[0].getElementImage().setLayoutY(167 );
        lawnMowers[1].getElementImage().setLayoutY(251);
        lawnMowers[2].getElementImage().setLayoutY(339);
        lawnMowers[3].getElementImage().setLayoutY(425);
        lawnMowers[4].getElementImage().setLayoutY(514);

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