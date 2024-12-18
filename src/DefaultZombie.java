import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DefaultZombie extends Zombie
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public DefaultZombie()
    {
        super(10, 2, 100);
        elementImage=new ImageView(new Image("images/zombies/walking-plants-vs-zombies.gif"));
        elementImage.setFitHeight(132);
        elementImage.setFitWidth(187);
    }

    // Added to be used when spawning a zombie on the yard
    public DefaultZombie(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
    }

    @Override
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

    @Override
    public void action()
    {

    }

    @Override
    public void appear(Pane root) {

    }
@Override
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
        root.getChildren().remove(elementImage);
    }
}
