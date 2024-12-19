import java.io.Serializable;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Pea extends Characters implements Serializable, Runnable
{
    // Reference to check whether it's alive or not
    private Plant parent;
    protected int damage;
    
    public Pea(int damage, Plant parent)
    {
        this.damage = damage;
        this.parent = parent; // Reference to the Peashooter
        elementImage = new ImageView(new Image("images/projectiles/pea.png"));
        elementImage.setFitWidth(23);
        elementImage.setFitHeight(66);
        elementImage.setPreserveRatio(true);
    }

    @Override
    public void run()
    {
        try
        {
            // New thread just delay in case any loading is required
            Thread.sleep(20);

            while (parent.isAlive() && elementImage.getLayoutX() < Yard.WIDTH)
            {
                // Increment the pea's position has to be in the Platform.runlater since it changes the GUI (root pane)
                Platform.runLater(() -> {
                    try
                    {
                        elementImage.setLayoutX(elementImage.getLayoutX() + 2);
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Exception: " + ex);
                    }
                });

                // Check for collision
                Zombie target = checkForCollision();

                if (target != null)
                {
                    // Do damage
                    target.takeDamage(damage);
                    System.out.println("Pea touched a zombie");

                    disappear(Yard.root); // Remove pea
                    return; // Stop further movement
                }

                // Slow down the while loop (PEA MOVEMENT SPEED), otherwise a lot of lag happens when the pea moves!
                Thread.sleep(5);
            }

            // If it reached out of bounds or plant died, make it disappear
                Platform.runLater(() -> {
                    disappear(Yard.root);
                });

        }
        catch (InterruptedException e) {
            System.out.println("Pea thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private Zombie checkForCollision()
    {
        synchronized (Yard.zombies)
        {
            for (Zombie zombie : Yard.zombies)
            {
                if (zombie.isColliding(elementImage))
                {
                    peaHitsZombieAudio();
                    // Return the first zombie it collides with
                    return zombie;
                }
            }
        }
        return null; // No collision
    }

    public void peaHitsZombieAudio() {
    try {
        String path = getClass().getResource("/music/pea hits zombie.mp3").toExternalForm();
        Media media = new Media(path);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.7);
        mediaPlayer.play();
    } catch (Exception e) {
        System.out.println("Error playing pea hit sound: " + e.getMessage());
    }
}

    public int getDamage() {
        return damage;}

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void shot(Zombie zombie)
    {
        this.x+=5; // for when the peashooter starts shooting peas
        if(zombie.getX()==this.x)
        {
            zombie.takeDamage(this.damage);
            // disappear(); comented out for now
        }
    }

    @Override
    public void action()
    {

    }

    @Override
    public void appear(Pane root)
    {
        Platform.runLater(() -> {
            if (elementImage != null) {
                root.getChildren().add(elementImage);
                System.out.println("Pea appears.");
            }
        });
    }

    @Override
    public void disappear(Pane root)
    {
        // Familiar code!
        Platform.runLater(() -> {
            if (elementImage != null) {
                root.getChildren().remove(elementImage);
                System.out.println("Pea disappears.");
            }
        });
    }
}
