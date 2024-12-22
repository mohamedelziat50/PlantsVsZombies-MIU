import java.io.Serializable;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class snowPea extends Characters implements Serializable, Runnable
{
    // Reference to check whether it's alive or not
    private Plant parent;
    protected int damage;

    private final double SLOW_DURATION = 5.0; // Duration in seconds
    private final double SLOW_FACTOR = 0.25;   // Factor to reduce the zombie's speed

    public snowPea(int damage, Plant parent)
    {
        this.damage = damage;
        this.parent = parent; // Reference to the Peashooter
        elementImage = new ImageView(new Image("images/projectiles/snowPea.png"));
        elementImage.setFitWidth(23);
        elementImage.setFitHeight(66);
        elementImage.setPreserveRatio(true);
    }

    // This function runs the pea object, inside it: moves the pea & handles collisions with zombies through the checkForZombieCollision() through out it's life time
    @Override
    public void run()
    {
        try
        {
            // New thread just delay in case any loading is required
            Thread.sleep(20);

            // Has to be synchronized in order to generate peas only while the plant is alive
            synchronized (this)
            {
                while (parent.isAlive() && elementImage.getLayoutX() < Yard.WIDTH)
                {
                    // Increment the pea's position has to be in the Platform.runlater since it changes the GUI (root pane)
                    Platform.runLater(() -> {
                        try
                        {
                            elementImage.setLayoutX(elementImage.getLayoutX() + 0.75); // Move the pea 0.75 pixel to be slower than normal pea
                        }
                        catch (Exception ex)
                        {
                            System.out.println("Exception: " + ex);
                        }
                    });

                    // Check for collision with a zombie
                    Zombie target = checkForZombieCollision();
                    if (target != null)
                    {
                        // Do damage
                        target.takeDamage(damage);

                        // And slow down the zombie
                        // Slow down the zombie
                        slowDownZombie(target);

                        // Remove pea
                        disappear(Yard.root);

                        // Stop further movement
                        return;
                    }

                    // Slow down the while loop (PEA MOVEMENT SPEED), otherwise a lot of lag happens when the pea moves!
                    Thread.sleep(3);
                }
            }

            // If it reached out of bounds or plant died, make it disappear
            disappear(Yard.root);

        }
        catch (InterruptedException e) {
            System.out.println("Snow Pea thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    // This function checks whether the current pea thread collided with a zombie through the zombie's isColliding function
    private Zombie checkForZombieCollision() {
        synchronized (Yard.zombies) {
            for (Zombie zombie : Yard.zombies) {
                if (zombie.isAlive() && zombie.isColliding(elementImage)) {
                    peaHitsZombieAudio();
                    return zombie; // Return the first alive zombie it collides with
                }
            }
        }
        return null; // No collision
    }

    private void slowDownZombie(Zombie target)
    {
        // Check if the zombie is already slowed
        if (!target.isSlowed())
        {
            Thread slowDownThread = new Thread(() -> {
                double originalSpeed = target.getSpeed();

                // Reduce speed
                target.setSpeed(originalSpeed * SLOW_FACTOR);

                // Mark the zombie as slowed (volatile flag)
                target.setSlowed(true);

                // GUI for slowed zombie
                markZombieAsSlowed(target);


                try {
                    Thread.sleep((long) (SLOW_DURATION * 1000)); // Wait for the slow duration
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Slow-down thread interrupted");
                }
                finally
                {
                    target.setSpeed(originalSpeed); // Restore speed
                    target.setSlowed(false); // Remove the slowed status
                    restoreZombieAppearance(target); // Restore original appearance
                }
            });

            slowDownThread.setDaemon(true);
            slowDownThread.start();
        }
    }

    private void markZombieAsSlowed(Zombie zombie) {
        Platform.runLater(() -> {
            zombie.getElementImage().setOpacity(0.85); // Make the zombie darker
        });
    }

    private void restoreZombieAppearance(Zombie zombie) {
        Platform.runLater(() -> {
            zombie.getElementImage().setOpacity(1.0); // Restore original opacity
        });
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
    public void takeDamage(int damage)
    {
        // Leave empty. (Has to be over-ridden)
    }

    @Override
    public void appear(Pane root)
    {
        Platform.runLater(() -> {
            if (elementImage != null) {
                root.getChildren().add(elementImage);
                // System.out.println("Pea appears.");
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
                // System.out.println("Pea disappears.");
            }
        });
    }
}
