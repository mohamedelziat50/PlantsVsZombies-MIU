import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Random;


public class Sun extends MainElements
{
    public Sun()
    {
        // Initialize the Peashooter image
        elementImage = new ImageView(new Image("images/others/sun.png"));
        elementImage.setFitWidth(90);
        elementImage.setFitHeight(85);
        elementImage.setPreserveRatio(true);

    }

    public Sun(int x,int y)
    {
        super(x, y);

        // Initialize the Peashooter image
        elementImage = new ImageView(new Image("images/others/sun.png"));
        elementImage.setFitWidth(90);
        elementImage.setFitHeight(85);
        elementImage.setPreserveRatio(true);

    }

    @Override
    public void appear(Pane root)
    {
        Random random = new Random();

        // sun spawning thread
        Thread sunThread = new Thread(() ->
        {
            while (true)
            {
                try
                {
                    // Spawn every 10 seconds
                    Thread.sleep(10000);

                    javafx.application.Platform.runLater(() ->
                    {
                        Sun sun = new Sun();

                        // Set random position
                        double x = 227 + random.nextDouble() * 680; // horizontal range
                        double startY = -50; // slightly above the screen
                        double stopY = 400 + random.nextDouble() * 100; // stop in lower part of the screen

                        sun.getElementImage().setLayoutX(x);
                        sun.getElementImage().setLayoutY(startY);

                        root.getChildren().add(sun.getElementImage());

                        // falling animation
                        Timeline dropAnimation = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(sun.getElementImage().layoutYProperty(), startY)),
                                new KeyFrame(Duration.seconds(7), new KeyValue(sun.getElementImage().layoutYProperty(), stopY)) // Stop in lower part
                        );

                        sun.setCollectible(root);

                        dropAnimation.setOnFinished(e ->
                        {
                            Timeline stayTimeline = new Timeline(
                                    new KeyFrame(Duration.seconds(3), event -> root.getChildren().remove(sun.getElementImage())) // disappear after three seconds if not collected at end position
                            );
                            stayTimeline.play();
                        });

                        dropAnimation.play();

                    });

                }
                catch (InterruptedException e)
                {
                    System.out.println("Sun spawning thread interrupted.");
                    return;
                }
            }
        });

        sunThread.setDaemon(true); // Thread stops when application closes
        sunThread.start();
    }

    public void setCollectible(Pane root) {
        // Add a flag to check if the sun has already been collected
        final boolean[] isCollected = {false}; // Use an array to allow modification inside the lambda

        elementImage.setOnMouseClicked(event -> {
            // Check if the sun has already been collected
            if (isCollected[0]) {
                return; // Exit if already collected
            }

            // Mark the sun as collected
            isCollected[0] = true;

            // Play the sun collection sound
            sunCollectedAudio();

            // Collection animation
            Timeline collectAnimation = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(elementImage.layoutXProperty(), elementImage.getLayoutX()),
                            new KeyValue(elementImage.layoutYProperty(), elementImage.getLayoutY()),
                            new KeyValue(elementImage.opacityProperty(), 1.0) // Start with full opacity
                    ),
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(elementImage.layoutXProperty(), 220), // Move to yard counter
                            new KeyValue(elementImage.layoutYProperty(), 12),  // Move to yard counter
                            new KeyValue(elementImage.opacityProperty(), 0)   // Fade out to 0 opacity
                    )
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
            mediaPlayer.setVolume(0.3);

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing sun collecting sound: " + e.getMessage());
        }
    }

    @Override
    public void disappear(Pane root)
    {

    }

}
