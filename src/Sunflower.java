import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sunflower extends Plant
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Sunflower()
    {
        super(50, 15, 30);
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
        //to be implemented
    }
}
