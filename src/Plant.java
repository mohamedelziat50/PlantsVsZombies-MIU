import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public abstract class Plant extends Characters
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

    @Override
    public abstract void action();

    @Override
    public void appear(Pane root)
    {
        if(elementImage != null)
            root.getChildren().add(elementImage);
    }

    @Override
    public void disappear(Pane root)
    {
        elementImage.setVisible(false);
        if (elementImage != null)
            root.getChildren().remove(elementImage);
    }
}
