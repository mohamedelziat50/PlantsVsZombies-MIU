import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LawnMower extends MainElements {
    private boolean isActive;
    private int row;

    public LawnMower(int row) {
        this.row = row;
        this.isActive = false;

        // Set the lawnmower image
        this.elementImage = new ImageView(new Image("images/zombies1/LawnCleaner.png"));
        this.elementImage.setFitWidth(76); // Adjust the size as needed
        this.elementImage.setFitHeight(70);
        elementImage.setPreserveRatio(true);
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
            lawnMowerAudio();
            // Start a new thread or animation to move the lawnmower
            new Thread(() -> {
                elementImage.setImage(new Image("images/zombies1/LawnCleaner1.png"));
                this.elementImage.setFitWidth(76); // Adjust the size as needed
                this.elementImage.setFitHeight(70);
                moveLawnMower(root);
            }).start();
        }
    }

    private synchronized void moveLawnMower(AnchorPane root) {
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

                // Create a smaller bounding box for collision detection
                double lawnMowerLeft = elementImage.getLayoutX();
                double lawnMowerRight = elementImage.getLayoutX() + elementImage.getFitWidth();
                double lawnMowerTop = elementImage.getLayoutY();
                double lawnMowerBottom = elementImage.getLayoutY() + elementImage.getFitHeight();

                // Get the bounds of the zombie
                double zombieCenterY = zombie.getElementImage().getLayoutY() + (zombie.getElementImage().getFitHeight() / 2);

                // Check if the zombie is within the bounds of this lawnmower's row
                if (zombie.isAlive()&&zombieCenterY >= lawnMowerTop && zombieCenterY <= lawnMowerBottom &&
                        zombie.getElementImage().getBoundsInParent().intersects(
                                lawnMowerLeft,
                                lawnMowerTop,
                                lawnMowerRight - lawnMowerLeft,
                                lawnMowerBottom - lawnMowerTop
                        )) {
                    zombie.setAlive(false); // Kill the zombie
                    Platform.runLater(() -> {
                        zombie.disappear(root); // Remove zombie from screen
                    });
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

    public void lawnMowerAudio() {
        try {
            // Path to the sun collected sound
            String path = getClass().getResource("/music/lawnmower.mp3").toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.4);
            javafx.application.Platform.runLater(() -> mediaPlayer.play());

        } catch (Exception e) {
            System.out.println("Error playing lawnMower sound: " + e.getMessage());
        }
    }

    public boolean isActive() {
        return isActive;
    }
}