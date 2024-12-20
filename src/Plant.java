import javafx.application.Platform;
import javafx.scene.layout.Pane;

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

    // Will be over-ridden by subclasses different actions
    public abstract void run();

    @Override
    public abstract void action();

    @Override
    public void takeDamage(int damage)
    {
        health -= damage;

        if (health <= 0)
        {
            disappear(Yard.root); // Remove Plant from the screen

            synchronized (Yard.grid)
            {
                Yard.grid[this.x][this.y] = null;
            }

            synchronized (Yard.plants)
            {
                Yard.plants.remove(this); // Remove from list
            }

            System.out.println("Plant has died!");
        }
    }

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
            if (elementImage != null)
            {
                elementImage.setVisible(false);
                root.getChildren().remove(elementImage);
                System.out.println("Plant removed.");
            }
        });

        // Make the thread flag to be false to stop the plant!
        setAlive(false);

        // Since all plants are threads, once they die, we shall interrupt their threads.
        Thread.currentThread().interrupt(); // Interrupt thread explicitly
    }


}
