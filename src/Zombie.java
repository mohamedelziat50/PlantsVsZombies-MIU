import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Zombie extends Characters
{
    protected int attackPower;
    protected int speed;
    // ADD WAITING TIME FOR ATTACKING

    public Zombie() {}

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Zombie(int attackPower,int speed, int health)
    {
        this.attackPower = attackPower;
        this.speed = speed;
        this.health = health;
    }

    public double getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Used in collision handling
    public boolean isColliding(ImageView pea)
    {
        // Shrink bounds for more precise collision detection
        double margin = 50; // Adjust as needed
        var zombieBounds = elementImage.getBoundsInParent(); // var -> specify any datatype you want, no need to explicitly declare it
        var peaBounds = pea.getBoundsInParent();

        var adjustedZombieBounds = new javafx.geometry.BoundingBox(
                zombieBounds.getMinX() + margin,
                zombieBounds.getMinY() + margin,
                zombieBounds.getWidth() - 2 * margin,
                zombieBounds.getHeight() - 2 * margin
        );

        return adjustedZombieBounds.intersects(peaBounds);
    }


    public void move()
    {
        synchronized (this)
        {
            // Synchronize this zombie's movement to avoid interference
            if (isAlive())
            {
                // Only move if the zombie is alive
                Platform.runLater(() -> {
                    // Update the zombie's position on the UI thread
                    elementImage.setLayoutX(elementImage.getLayoutX() - speed); // Move left by 1 unit
                });

                // Simulate movement delay to avoid excessive CPU usage
                try {
                    Thread.sleep(50); // Adjust this value for desired movement speed
                } catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                    System.err.println("Zombie movement thread interrupted");
                }
            }
        }


    }

    public void takeDamage(int damage)
    {
        health -= damage;
        System.out.println("Zombie damaged!");
        if (health <= 0)
        {
            // Set the volatile thread flag to be false
            setAlive(false);

            Platform.runLater(() -> {
                disappear(Yard.root); // Remove zombie from the screen
            });

            synchronized (Yard.zombies)
            {
                Yard.zombies.remove(this); // Remove from the list
            }
        }
    }

    @Override
    public abstract void action();

    @Override
    public abstract void appear(Pane root);

    public void appear(Pane root,int x,int y)
    {
        Platform.runLater(() -> {
            elementImage.setLayoutX(x);
            elementImage.setLayoutY(y);

            root.getChildren().add(elementImage);
        });
    }


    @Override
    public void disappear(Pane root)
    {
        System.out.println("Zombie died!");
        elementImage.setImage(new Image("images/zombies/dyingDefaultZombie.gif"));


        double gifDurationInSeconds = 2; // Replace with the actual duration of the GIF

        // Create a PauseTransition to wait for the GIF to finish
        PauseTransition pause = new PauseTransition(Duration.seconds(gifDurationInSeconds));
        pause.setOnFinished(event -> {
            root.getChildren().remove(elementImage);
            System.out.println("Zombie removed from the screen!");
        });

        // Start the pause
        pause.play();
    }
}