import javafx.animation.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Sunflower extends Plant
{
    private boolean isSunProduced = false;

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Sunflower()
    {
        super(50, 15, 60);
    }

    // Added to be used when placing a plant on the yard
    public Sunflower(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;

        // Initialize the Sunflower image
        elementImage = new ImageView(new Image("images/plants/sunflower.gif"));
        elementImage.setFitWidth(73);
        elementImage.setFitHeight(70);
        elementImage.setPreserveRatio(true);

        // Set the position for the image
        elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) + 5);
        elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 15);
    }

    @Override
    public void takeDamage(int damage)
    {
        // Call the superclass implementation to apply damage
        super.takeDamage(damage);

        // Add any specific behavior for this subclass if needed
        System.out.println("Sunflower takes damage: " + damage + " Current health: " + this.health);
    }

    @Override
    public void run()
    {
    }

    @Override
    public void action()
    {

    }

    public void startSunProduction(AnchorPane root)
    {
        //to produce sun every 5 seconds
        Timeline sunProductionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> produceSun(root)),
                new KeyFrame(Duration.seconds(10), event -> produceSun(root))
        );
        sunProductionTimeline.setCycleCount(Timeline.INDEFINITE);
        sunProductionTimeline.play();
    }


    private void produceSun(AnchorPane root)
    {
        if(Yard.gameOver)
            return;

        if(this.isAlive())
        {
        // Prevent the sun from being produced multiple times
        if (isSunProduced) return; // If sun is already produced, return early

        // Set flag to true, preventing further sun production until reset
        isSunProduced = true;
            // Create a Timeline to reset the brightness after 0.5 seconds
            // Create a ColorAdjust to increase brightness
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.5); // Increase brightness
            elementImage.setEffect(colorAdjust);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(800), // Duration of 0.5 seconds
                            new KeyValue(colorAdjust.brightnessProperty(),0) // Reset effect to normal
                    )
            );
            timeline.setCycleCount(1); // Run the timeline only once
            timeline.play(); // Start the timeline

        // Create a new Sun instance at the Sunflower's location
        Sun newSun = new Sun((int) elementImage.getLayoutX(), (int) elementImage.getLayoutY());

        // Add the Sun to the root pane
        root.getChildren().add(newSun.getElementImage());

        // Animate the sun coming out of the sunflower (curved animation)
        Path path = new Path();

        // Start point: Sunflower's position
        MoveTo moveTo = new MoveTo(elementImage.getLayoutX() + 40, elementImage.getLayoutY());  // Slight offset for the sun

        // Curve endpoint: Slightly upward and to the right (adjust to your liking)
        ArcTo arcTo = new ArcTo();
        arcTo.setX(elementImage.getLayoutX() + 70); // Slightly to the right
        arcTo.setY(elementImage.getLayoutY() + 60); // Slightly upward
        arcTo.setRadiusX(20); // Horizontal radius of the arc
        arcTo.setRadiusY(20); // Vertical radius of the arc
        arcTo.setSweepFlag(true); // Sweep direction (clockwise)

        path.getElements().addAll(moveTo, arcTo);

        // Path transition for the sun animation
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(1)); // Adjust duration if necessary
        pathTransition.setPath(path);
        pathTransition.setNode(newSun.getElementImage());
        pathTransition.setCycleCount(1);

        // When the animation finishes, do NOT remove the sun, but make it clickable
        pathTransition.setOnFinished(event -> {
            // Allow clicking immediately after animation
            newSun.getElementImage().setOnMouseClicked(clickEvent -> {
                newSun.sunCollectedAudio(); // Play collection sound

                // Increment sun counter
                Yard.sunCounter += 25;
                Yard.label.setText(String.valueOf(Yard.sunCounter));

                // Remove the sun immediately when clicked (no collection animation)
                root.getChildren().remove(newSun.getElementImage());

                // Reset flag after sun is collected
                isSunProduced = false;
            });

            // Optional: If the sun isn't clicked, remove it after a certain amount of time (e.g., 8 seconds)
            PauseTransition removeSunDelay = new PauseTransition(Duration.seconds(8));
            removeSunDelay.setOnFinished(e -> {
                root.getChildren().remove(newSun.getElementImage());
                isSunProduced = false; // Reset flag after removal
            });
            removeSunDelay.play();
        });

        // Start the path transition (sun coming out animation)
        pathTransition.play();
        }
    }

}

