import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
        elementImage = new ImageView(new Image("images/plants/sun.png"));
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
                    Thread.sleep(3000); // spawn kol 3 seconds

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



                        // pressing on the sun logic and animation
                        sun.getElementImage().setOnMouseClicked(event ->
                        {
                            // pressing on the sun logic and animation
                            Timeline collectAnimation = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(sun.getElementImage().layoutXProperty(), sun.getElementImage().getLayoutX())),
                                    new KeyFrame(Duration.ZERO, new KeyValue(sun.getElementImage().layoutYProperty(), sun.getElementImage().getLayoutY())),
                                    new KeyFrame(Duration.seconds(1), new KeyValue(sun.getElementImage().layoutXProperty(), 220)), // Yard X position
                                    new KeyFrame(Duration.seconds(1), new KeyValue(sun.getElementImage().layoutYProperty(), 12))  // Yard Y position
                            );

                            collectAnimation.setOnFinished(event2 -> root.getChildren().remove(sun.getElementImage())); // remove after collection
                            collectAnimation.play();

                            Yard.sunCounter+=25;
                            Yard.label.setText(String.valueOf(Yard.sunCounter));

                            // Stop the drop animation once clicked
                            dropAnimation.stop();
                        });

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

    @Override
    public void disappear(Pane root)
    {

    }

}
