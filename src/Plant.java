import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

// A plant is now a thread
public abstract class Plant extends Characters implements Runnable
{
    protected int cost;

    public Plant() {}

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Plant(int cost, double waitingTime, int health)
    {
        super(health, waitingTime);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public abstract void takeDamage(int damage);

    // Will be over-ridden by subclasses different actions
    public abstract void run();

    @Override
    public abstract void action();


    @Override
    public void appear(Pane root)
    {
        Platform.runLater(() -> {
            if (elementImage != null) {
                root.getChildren().add(elementImage);
                System.out.println("Plant appears.");
            }
        });

        // Alive-flag used with threads.
        setAlive(true);
    }

    @Override
    public void disappear(Pane root)
    {
        Platform.runLater(() -> {
            elementImage.setVisible(false);
            if (elementImage != null)
            {
                root.getChildren().remove(elementImage);
                System.out.println("Plant appears.");
            }
        });

        // Make the thread flag to be false to stop the plant!
        setAlive(false);
    }
}
