import java.io.Serializable;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Pea extends Characters implements Serializable, Runnable
{
    // Reference to check whether it's alive or not
    private Peashooter parent;
    protected int damage;
    
    public Pea(int damage, Peashooter parent)
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

                /*
                    // Check for collisions with zombies (example logic)
                    Zombie target = checkForCollision();
                    if (target != null) {
                        target.takeDamage(damage);
                        disappear((Pane) elementImage.getParent()); // Remove pea on collision
                        return; // Stop further movement
                    }
                */

                // Slow down the while loop (PEA MOVEMENT SPEED), otherwise a lot of lag happens when the pea moves!
                Thread.sleep(50);
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
        return new DefaultZombie();
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
