import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Card
{
    private String cardImagePath;       // Path to the card image
    private String draggingImagePath;   // Path to the dragging PNG
    private String plantGifPath;        // Path to the plant GIF (optional)
    private Class<? extends Plant> plantType; // Class type of the Plant

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
    public Card(String cardImagePath, String draggingImagePath, String plantGifPath, Class<? extends Plant> plantType)
    {
        this.cardImagePath = cardImagePath;
        this.draggingImagePath = draggingImagePath;
        this.plantGifPath = plantGifPath;
        this.plantType = plantType;

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

        // Set mouse events for dragging & dropping once clicking on a card
        boolean[] dropped = {false};
        hoverImageView.setVisible(false);
        root.getChildren().add(hoverImageView);

        // Once card is clicked
        cardImageView.setOnMousePressed(event -> {
            draggingImageView.setLayoutX(event.getSceneX() - 30);
            draggingImageView.setLayoutY(event.getSceneY() - 35);
            draggingImageView.setVisible(true);
            dropped[0] = false;
            root.getChildren().add(draggingImageView);
            event.consume();
        });

        // Lock a dragging image once held on a card
        cardImageView.setOnMouseDragged(event -> {
            if (draggingImageView.isVisible()) {
                draggingImageView.setLayoutX(event.getSceneX() - 30);
                draggingImageView.setLayoutY(event.getSceneY() - 35);
            }
        });

        /*

        // Handle hover effect on the grid buttons
        for (Node node : yardGrid.getChildren())
        {
            if (node instanceof Button) {
                Button button = (Button) node;

                // Show hover image when entering a button
                button.setOnMouseEntered(event -> {
                    if(!dropped[0] && root.getChildren().contains(draggingImageView))
                    {
                        Bounds buttonBounds = button.localToScene(button.getBoundsInLocal());
                        hoverImageView.setLayoutX(buttonBounds.getMinX() + buttonBounds.getWidth() / 2 - hoverImageView.getFitWidth() / 2);
                        hoverImageView.setLayoutY(buttonBounds.getMinY() + buttonBounds.getHeight() / 2 - hoverImageView.getFitHeight() / 2);
                        hoverImageView.setVisible(true);
                        System.out.println("Adding hover image");
                        System.out.println(event);
                    }
                });

                // Hide hover image when leaving a button, don't remove from scene will cause in errors and delays
                button.setOnMouseExited(event -> {
                    if(!dropped[0] && root.getChildren().contains(draggingImageView))
                    {
                        System.out.println("Removing hover image");
                        hoverImageView.setVisible(false);
                        System.out.println(event);
                    }


                });
            }
        }


         */

        // Drop the plant when the mouse is released
        cardImageView.setOnMouseReleased(event -> {
            // Check if the drop is within any button
            for (Node node : yardGrid.getChildren())
            {
                if (node instanceof Button) {
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

                        // Hide & remove the draggingImageView
                        // dropped[0] = true; fashalt eni a3melha
                        draggingImageView.setVisible(false);
                        root.getChildren().remove(draggingImageView);

                        // Instantiate the plant dynamically using reflection
                        try
                        {
                            // Since we don't know what type of plant the user will select.
                            Plant plant = plantType.getDeclaredConstructor(int.class, int.class).newInstance((int) centerX, (int) centerY);

                            // Place the plant in the yard and display it
                            yard.placePlant(plant, root, GridPane.getRowIndex(button), GridPane.getColumnIndex(button));
                        }
                        catch (Exception e)
                        {
                            System.out.println("An exception occured: " + e);
                            System.exit(1);
                        }

                        return; // Exit after placing the plant, redundant if we continue in loop.
                    }
                }
            }

            // If not dropped on a button, remove the dragging image
            // dropped[0] = true; fashalt
            draggingImageView.setVisible(false);
            root.getChildren().remove(draggingImageView);

            event.consume();
        });
    }

    public ImageView getCardImageView() {
        return cardImageView;
    }

    public void setCardImageView(ImageView cardImageView) {
        this.cardImageView = cardImageView;
    }
}
