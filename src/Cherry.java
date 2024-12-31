import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

public class Cherry extends Plant
{
    public Cherry()
    {
        super(150, 20, 300); // Dummy values for health, damage, and cost
    }

    public Cherry(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;

        // Initialize the Cherry image with the "placing" animation
        elementImage = new ImageView(new Image("images/plants/cherry-place.gif"));
        elementImage.setFitWidth(80);
        elementImage.setFitHeight(70);
        elementImage.setPreserveRatio(true);

        // Set the position for the image
        elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) + 5);
        elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 25);
    }

    @Override
    public void run()
    {
        // Step 1: Wait for 1 second (simulate "placing" animation)
        PauseTransition waitToExplode = new PauseTransition(Duration.seconds(1));

        waitToExplode.setOnFinished(event -> {
            // Step 2: Switch to the explosion animation
            Platform.runLater(() -> {
                elementImage.setImage(new Image("images/plants/cherry-explode.gif"));
                elementImage.setFitWidth(227);
                elementImage.setFitHeight(250);

                // GIF Isn't exact so need to minus it to get it centered like cherry
                elementImage.setLayoutX(elementImage.getLayoutX() - 70);
                elementImage.setLayoutY(elementImage.getLayoutY() - 55);
            });

            // Step 3: Trigger explosion effect
            explode();
            cherryBombAudio();

            // Step 4: Remove the cherry from the grid after 1 second
            PauseTransition removeCherry = new PauseTransition(Duration.seconds(3));
            removeCherry.setOnFinished(e -> Platform.runLater(() -> disappear(Yard.root)));
            removeCherry.play();
        });
        waitToExplode.play();
    }

    private void explode()
    {
        // Create a list to store zombies that need to be killed
        // You can't modify the synchronized list while it's being looped, otherwise a concurrency error arises.
        ArrayList<Zombie> zombiesToKill = new ArrayList<>();

        synchronized (Yard.zombies)
        {
            // Loop through all zombies and check proximity (1 cell distance around the cherry)
            for (Zombie zombie : Yard.zombies)
            {
                double zombieX = zombie.elementImage.getLayoutX();
                double zombieY = zombie.elementImage.getLayoutY();

                double cherryX = elementImage.getLayoutX();
                double cherryY = elementImage.getLayoutY();

                // Check if the zombie is within one cell (120 px around it - random testing number)
                if (Math.abs(zombieX - cherryX) <= 120 && Math.abs(zombieY - cherryY) <= 120)
                    zombiesToKill.add(zombie); // Add to list of zombies to kill
            }
        }

        // Now, outside the synchronized block, apply the changes to the zombies
        for (Zombie zombie : zombiesToKill) {
            zombie.takeDamage(Integer.MAX_VALUE); // Kill the zombie immediatly
        }

        // Remove cherry from the plants list and grid
        removeFromListsAndGrid();
    }

    // Helper method to remove the cherry from the plants list and grid
    private void removeFromListsAndGrid()
    {
        synchronized (Yard.plants)
        {
            // Remove cherry from the plants list
            Yard.plants.remove(this);
        }

        synchronized (Yard.grid)
        {
            // Remove cherry from the grid
            Yard.grid[this.getX()][this.getY()] = null;
        }
    }

    public void cherryBombAudio() {
        try {
            // Path to the sun collected sound
            String path = getClass().getResource("/music/cherry bomb.mp3").toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.5);
            javafx.application.Platform.runLater(() -> mediaPlayer.play());

        } catch (Exception e) {
            System.out.println("Error playing cherry bomb sound: " + e.getMessage());
        }
    }


    @Override
    public void takeDamage(int damage) {
        // Cherry does not take damage
    }

    @Override
    public void action() {
        // Cherry has no action; it's an instant effect plant
    }
}