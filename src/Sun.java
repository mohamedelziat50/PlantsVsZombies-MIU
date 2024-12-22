import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Random;


public class Sun extends Characters implements Runnable
{
    public Sun()
    {
        // Initialize the Peashooter image
        elementImage = new ImageView(new Image("images/others/sun.png"));
        elementImage.setFitWidth(90);
        elementImage.setFitHeight(85);
        elementImage.setPreserveRatio(true);

    }

    @Override
    public void takeDamage(int damage)
    {
    }

    public Sun(int x,int y)
    {
        super(x, y);

        elementImage = new ImageView(new Image("images/others/sun.png"));
        elementImage.setFitWidth(90);
        elementImage.setFitHeight(85);
        elementImage.setPreserveRatio(true);

    }

    @Override
    public void appear(Pane root)
    {
        Platform.runLater(() -> {
            if (elementImage != null) {
                root.getChildren().add(elementImage);
                // System.out.println("sun appears.");
            }
        });
    }

    @Override
    public void run() {
        Random random = new Random();

        try
        {
            // New thread, slight delay in case of initialization requirements
            Thread.sleep(20);

            // Has to be synchronized to manage the sun spawning and GUI updates
            synchronized (this)
            {
                while (true)
                {
                    // Delay for sun spawning
//                    Thread.sleep(3000);

                    // Platform.runLater for UI changes
                    Platform.runLater(() -> {
                        try {
                            // Set random position for the sun
                            double x = 227 + random.nextDouble() * 680; // Horizontal range
                            double startY = -50; // Start above the screen
                            double stopY = 400 + random.nextDouble() * 100; // Stop in lower part

                            // Position the sun
                            getElementImage().setLayoutX(x);
                            getElementImage().setLayoutY(startY);

                            // Add the sun to the root pane
                            Yard.root.getChildren().add(getElementImage());

                            // Create the falling animation
                            Timeline dropAnimation = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(getElementImage().layoutYProperty(), startY)),
                                    new KeyFrame(Duration.seconds(7), new KeyValue(getElementImage().layoutYProperty(), stopY))
                            );

                            // Set the sun to be collectible
                            setCollectible(Yard.root);

                            // On animation finish, schedule removal if not collected
                            dropAnimation.setOnFinished(e -> {
                                Timeline stayTimeline = new Timeline(
                                        new KeyFrame(Duration.seconds(3), event -> Yard.root.getChildren().remove(getElementImage()))
                                );
                                stayTimeline.play();
                            });

                            dropAnimation.play();
                        } catch (Exception ex) {
                            System.out.println("Exception during sun animation: " + ex.getMessage());
                        }
                    });
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Sun thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }



    public void setCollectible(Pane root)
    {
        elementImage.setOnMouseClicked(event ->
        {
            // Play the sun collection sound
            sunCollectedAudio();

            // Collection animation
            Timeline collectAnimation = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(elementImage.layoutXProperty(), elementImage.getLayoutX())),
                    new KeyFrame(Duration.ZERO, new KeyValue(elementImage.layoutYProperty(), elementImage.getLayoutY())),
                    new KeyFrame(Duration.seconds(1), new KeyValue(elementImage.layoutXProperty(), 220)), // Move to yard counter
                    new KeyFrame(Duration.seconds(1), new KeyValue(elementImage.layoutYProperty(), 12))  // Move to yard counter
            );

            collectAnimation.setOnFinished(event2 -> root.getChildren().remove(elementImage)); // Remove after collection
            collectAnimation.play();

            // Increment the counter and update the label
            Yard.sunCounter += 25;
            Yard.label.setText(String.valueOf(Yard.sunCounter));
        });
    }

    public void sunCollectedAudio() {
    try {
        String path = getClass().getResource("/music/sun pickup.mp3").toExternalForm();
        Media media = new Media(path);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.5);

        mediaPlayer.play();
    } catch (Exception e) {
        System.out.println("Error playing sun collecting sound: " + e.getMessage());
    }
}


    @Override
    public void disappear(Pane root)
    {

    }

    @Override
    public void action() {

    }
}
