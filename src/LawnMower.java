import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LawnMower extends MainElements {
    private boolean isActive;
    private int row;

    public LawnMower(int row) {
        this.row = row;
        this.isActive = false;

        // Set the lawnmower image
        this.elementImage = new ImageView(new Image("images/plants/LawnMower.png"));
        this.elementImage.setFitWidth(70); // Adjust the size as needed
        this.elementImage.setFitHeight(50);
    }

    public ImageView getElementImage() {
        return elementImage;
    }

    @Override
    public void appear(Pane root) {

    }

    @Override
    public void disappear(Pane root) {

    }

    public void activate(AnchorPane root) {
        if (!isActive) {
            isActive = true;
            // Start a new thread or animation to move the lawnmower
            new Thread(() -> {
                elementImage.setImage(new Image("images/plants/animatedLawnMower.gif"));
                moveLawnMower(root);
            }).start();
        }
    }

    private void moveLawnMower(AnchorPane root) {
        double[] layoutX = new double[2];
        layoutX[0] = elementImage.getLayoutX();

        // Move the lawnmower across the screen
        while (layoutX[0] < 950) { // Move until off-screen (adjust as needed)
            layoutX[0] += 10; // Speed of the lawnmower

            // Update position on the UI thread
            Platform.runLater(() -> {
                elementImage.setLayoutX(layoutX[0]);
            });

            // Check for collision with zombies (only for the moving lawnmower)
            for (Zombie zombie : Yard.zombies) {
                if (zombie.isAlive()) {
                    // Create a smaller bounding box for collision detection
                    double lawnMowerLeft = elementImage.getLayoutX() + 10; // Adjust bounds by adding a margin
                    double lawnMowerRight = elementImage.getLayoutX() + elementImage.getFitWidth() - 10; // Adjust bounds by subtracting a margin
                    double lawnMowerTop = elementImage.getLayoutY() + 10;
                    double lawnMowerBottom = elementImage.getLayoutY() + elementImage.getFitHeight() - 30;

                    // Check if this specific lawnmower intersects with the zombie
                    if (zombie.getElementImage().getBoundsInParent().intersects(lawnMowerLeft, lawnMowerTop, lawnMowerRight - lawnMowerLeft, lawnMowerBottom - lawnMowerTop)) {
                        zombie.setAlive(false); // Kill the zombie
                        Platform.runLater(() -> {
                            zombie.disappear(root); // Remove zombie from screen
                        });
                        break; // Exit the loop to prevent multiple collisions with neighboring lawnmowers
                    }
                }
            }

            try {
                Thread.sleep(50); // Adjust speed of movement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Remove lawnmower from the yard once it is off-screen
        Platform.runLater(() -> {
            root.getChildren().remove(elementImage); // Remove lawnmower from the scene
        });
    }



    public boolean isActive() {
        return isActive;
    }
}
