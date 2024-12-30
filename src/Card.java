import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Card
{
    private String cardImagePath;       // Path to the card image
    private String draggingImagePath;   // Path to the dragging PNG
    private String plantGifPath;        // Path to the plant GIF (optional)
    private Class<? extends Plant> plantType; // Class type of the Plant
    private int cost;

    private boolean onCooldown = false;
    private final int cooldownTime = 10 * 1000; // Cooldown duration in milliseconds
    private Rectangle cooldownOverlay; // Mask for the visual effect




    private ImageView cardImageView;    // Card ImageView for dragging
    private ImageView draggingImageView; // Temporary image for dragging
    private ImageView hoverImageView;

    // Constructor used for locked cards
    public Card(String cardImagePath)
    {
        this.cardImagePath = cardImagePath;
        this.cardImageView = new ImageView(new Image(cardImagePath));
    }

    // Constructor used for unlocked cards
    public Card(String cardImagePath, String draggingImagePath, Class<? extends Plant> plantType, int cost)
    {
        this.cardImagePath = cardImagePath;
        this.draggingImagePath = draggingImagePath;
        this.plantType = plantType;
        this.cost = cost;

        this.cardImageView = new ImageView(new Image(cardImagePath));
        this.draggingImageView = new ImageView(new Image(draggingImagePath));
        // Hovering image is the same as the dragging image.
        this.hoverImageView = new ImageView(new Image(draggingImagePath));
    }

    public void cardImageViewSetProperties(int layoutX, int layoutY, int fitWidth, int fitHeight, boolean preserveRatio, boolean setVisible)
    {
        // Configure card properties
        cardImageView.setLayoutX(layoutX);
        cardImageView.setLayoutY(layoutY);

        cardImageView.setFitWidth(fitWidth);
        cardImageView.setFitHeight(fitHeight);

        cardImageView.setPreserveRatio(preserveRatio);
        cardImageView.setVisible(setVisible);
    }

    public void draggingImageViewSetProperties(int fitWidth, int fitHeight, boolean preserveRatio, boolean setVisible)
    {
        draggingImageView.setFitWidth(fitWidth);
        draggingImageView.setFitHeight(fitHeight);

        draggingImageView.setPreserveRatio(preserveRatio);
        draggingImageView.setVisible(setVisible);
    }

    // Create a semi-transparent hover image
    public void hoverImageViewSetProperties(int fitWidth, int fitHeight, boolean preserveRatio, boolean setVisible)
    {
        hoverImageView.setFitWidth(fitWidth);
        hoverImageView.setFitHeight(fitHeight);

        hoverImageView.setPreserveRatio(preserveRatio);
        hoverImageView.setVisible(setVisible);
        hoverImageView.setOpacity(0.5); // Semi-transparent
    }

    public void addToYard(AnchorPane root, GridPane yardGrid, Yard yard)
    {
        // Add card to the root pane
        root.getChildren().add(cardImageView);


        cardImageView.setOnMousePressed(event ->
        {
            if (yard.sunCounter < cost)
            {
                cardUnavailableAudio();
                System.out.println("Not enough sun to select this card.");

                // Optionally change cursor to indicate that selection is not allowed
                // root.setStyle("-fx-cursor: not-allowed;"); // Change the cursor style

                return;
            }

            // Placed the handling into a thread, since it updates the root pane continuously
            cardSelectedAudio();
            new Thread(() -> {
                try
                {
                    // Simulate a delay or background processing.
                    Thread.sleep(20); // If you increase, then the click read will be delayed

                    /*
                    JavaFX UI updates must happen on the JavaFX Application Thread.
                    If another thread (like a background thread) tries to modify the UI directly, it can cause errors.
                    Platform.runLater safely schedules UI updates on the JavaFX Application Thread: Run this code on the JavaFX Application Thread when it's safe to do so
                    You don’t need Platform.runLater if all your code runs on event handlers, which are already on the JavaFX Application Thread. Only use it when you create separate threads.
                     */

                    // Update UI safely after background work
                    Platform.runLater(() -> {
                        // Activate dragging
                        draggingImageView.setLayoutX(event.getSceneX() - 30);
                        draggingImageView.setLayoutY(event.getSceneY() - 35);
                        draggingImageView.setVisible(true);
                        root.getChildren().add(draggingImageView);

                        // Add hoverImageView to the root, but keep it initially hidden
                        hoverImageView.setVisible(false);

                        if (!root.getChildren().contains(hoverImageView))
                            root.getChildren().add(hoverImageView);
                    });
                } catch (InterruptedException e) {
                    System.out.println("Exception: " + e);
                }
            }).start();

            event.consume(); // Consume the event to avoid propagation
        });


        // Update dragging and hover behavior
        cardImageView.setOnMouseDragged(event ->
        {
            if (yard.sunCounter < cost)
            {
                // If the sun counter is not sufficient, prevent dragging
                return;
            }

            new Thread(() ->
            {
                try
                {
                    // Simulate a delay or background processing.
                    Thread.sleep(20); // If you increase, then the dragging will be delayed

                    // Update UI safely after background work
                    Platform.runLater(() -> {
                        if (draggingImageView.isVisible())
                        {
                            // Update dragging image position
                            draggingImageView.setLayoutX(event.getSceneX() - 30);
                            draggingImageView.setLayoutY(event.getSceneY() - 35);

                            // Track the closest grid cell and update hover image position
                            double closestDistance = Double.MAX_VALUE;
                            Button closestButton = null;

                            for (Node node : yardGrid.getChildren()) {
                                if (node instanceof Button button) {
                                    // Get button bounds
                                    Bounds buttonBounds = button.localToScene(button.getBoundsInLocal());

                                    // Calculate distance to the center of the button
                                    double centerX = buttonBounds.getMinX() + buttonBounds.getWidth() / 2;
                                    double centerY = buttonBounds.getMinY() + buttonBounds.getHeight() / 2;
                                    double distance = Math.hypot(centerX - event.getSceneX(), centerY - event.getSceneY());

                                    // Check if this button is closer than the previously tracked one
                                    if (distance < closestDistance) {
                                        closestDistance = distance;
                                        closestButton = button;
                                    }
                                }
                            }

                            if (closestButton != null) {
                                // Get the row and column indices
                                int row = GridPane.getRowIndex(closestButton);
                                int col = GridPane.getColumnIndex(closestButton);

                                // Check if the cell is empty by verifying no plant exists at this cell
                                if (yard.isValidPosition(row, col)) {
                                    // Get button bounds for positioning the hover image
                                    Bounds buttonBounds = closestButton.localToScene(closestButton.getBoundsInLocal());

                                    // Center the hover image on this button
                                    hoverImageView.setLayoutX(buttonBounds.getMinX() + buttonBounds.getWidth() / 2 - hoverImageView.getFitWidth() / 2);
                                    hoverImageView.setLayoutY(buttonBounds.getMinY() + buttonBounds.getHeight() / 2 - hoverImageView.getFitHeight() / 2);

                                    // Ensure opacity is set for hover image
                                    hoverImageView.setOpacity(0.5);  // Make sure opacity is consistent
                                    hoverImageView.setVisible(true); // Show the hover image
                                } else {
                                    // Hide hover image if the cell is occupied
                                    hoverImageView.setVisible(false);
                                }
                            } else {
                                // Hide hover image if no valid grid cell is nearby
                                hoverImageView.setVisible(false);
                            }
                        }

                    });
                } catch (InterruptedException e) {
                    System.out.println("Exception: " + e);
                }
            }).start();

            event.consume();
        });

        // Drop the plant when the mouse is released
        cardImageView.setOnMouseReleased(event ->
        {
            if (yard.sunCounter < cost)
            {
                // If the sun counter is not sufficient, prevent placing the plant
                return;
            }

            new Thread(() -> {
                try {
                    // Simulate a delay or background processing
                    Thread.sleep(20); // If increased, there will be a delay when placing the plant.

                    // Update UI safely after background work
                    Platform.runLater(() -> {
                        // Check if the drop is within any button
                        for (Node node : yardGrid.getChildren()) {
                            if (node instanceof Button) {
                                Button button = (Button) node;

                                // Get button bounds on screen
                                Bounds buttonBounds = button.localToScene(button.getBoundsInLocal());

                                // Check if the drop point is within this button
                                if (buttonBounds.contains(event.getSceneX(), event.getSceneY())) {
                                    // Calculate the button's center position
                                    double centerX = buttonBounds.getMinX() + buttonBounds.getWidth() / 2;
                                    double centerY = buttonBounds.getMinY() + buttonBounds.getHeight() / 2;

                                    // Hide & remove the draggingImageView
                                    draggingImageView.setVisible(false);
                                    root.getChildren().remove(draggingImageView);

                                    if (plantType == null) {
                                        System.out.println("Shovel used at (" + GridPane.getRowIndex(button) + ", " + GridPane.getColumnIndex(button) + ")");


                                        // Call a method to remove the plant and its image
                                        yard.removePlant(root, GridPane.getRowIndex(button), GridPane.getColumnIndex(button));
                                    } else {
                                        // Instantiate the plant dynamically using reflection
                                        try {
                                            // Create the plant instance
                                            Plant plant = plantType.getDeclaredConstructor(int.class, int.class).newInstance((int) centerX, (int) centerY);

                                            if(yard.grid[GridPane.getRowIndex(button)][GridPane.getColumnIndex(button)] == null)
                                            {
                                                yard.sunCounter -= plant.getCost();
                                                yard.label.setText(String.valueOf(yard.sunCounter));

                                                startCooldown();
                                            }

                                            // Place the plant in the yard and display it
                                            yard.placePlant(plant, root, GridPane.getRowIndex(button), GridPane.getColumnIndex(button));



                                        } catch (Exception e) {
                                            System.out.println("An exception occurred: " + e);
                                        }
                                    }

                                    // Hide and remove the hover image
                                    hoverImageView.setVisible(false);
                                    root.getChildren().remove(hoverImageView);

                                    return; // Exit after placing the plant
                                }
                            }
                        }

                        // If not dropped on a button, remove the dragging image and hide hover image
                        draggingImageView.setVisible(false);
                        root.getChildren().remove(draggingImageView);
                        hoverImageView.setVisible(false); // Hide hover image if drop fails
                        root.getChildren().remove(hoverImageView); // Remove hover image from scene
                    });
                } catch (InterruptedException e)
                {
                    System.out.println("Exception: " + e);
                }
            }).start();

            event.consume();
        });
    }

    public ImageView getCardImageView()
    {
        return cardImageView;
    }

    public void setCardImageView(ImageView cardImageView)
    {
        this.cardImageView = cardImageView;
    }


    private void startCooldown()
    {
        onCooldown = true;

        cardImageView.setDisable(true);  // Disable the card to prevent interactions

        // Initialize the overlay if not already created
        if (cooldownOverlay == null)
        {
            cooldownOverlay = new Rectangle(cardImageView.getFitWidth(), cardImageView.getFitHeight());
            cooldownOverlay.setFill(Color.BLACK);
            cooldownOverlay.setOpacity(0.7); // Semi-transparent

            // Bind overlay position to the card's layout properties
            cooldownOverlay.setLayoutX(cardImageView.getLayoutX());
            cooldownOverlay.setLayoutY(cardImageView.getLayoutY());

            // Add the overlay to the parent if not already added
            if (!Yard.root.getChildren().contains(cooldownOverlay))
            {
                Yard.root.getChildren().add(cooldownOverlay);
            }
        }

        // Reset overlay height and make it visible
        cooldownOverlay.setHeight(cardImageView.getFitHeight());
        cooldownOverlay.setVisible(true);

        Timeline cooldownTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(cooldownOverlay.heightProperty(), cardImageView.getFitHeight())),
                new KeyFrame(Duration.millis(cooldownTime), new KeyValue(cooldownOverlay.heightProperty(), 0))
        );

        // When the animation finishes, reset the state
        cooldownTimeline.setOnFinished(event -> {
            onCooldown = false; // Cooldown is over
            cooldownOverlay.setVisible(false); // Hide the overlay

            cardImageView.setDisable(false); // Allow interactions again

        });

        // Start the animation
        cooldownTimeline.play();
    }



    public void cardSelectedAudio() {
        try {
            String path = getClass().getResource("/music/card selected.mp3").toExternalForm();
            System.out.println("Path: " + path);
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.02);

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing card sound: " + e.getMessage());
        }
    }

    public void cardUnavailableAudio() {
        try {
            String path = getClass().getResource("/music/card unavailable.mp3").toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3);

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing unavailable card sound: " + e.getMessage());
        }
    }
}