import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Potato extends Plant
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Potato()
    {
        super(50, 22, 300);
    }

    // Added to be used when placing a plant on the yard
    public Potato(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;

        // Initialize the Potato image
        elementImage = new ImageView(new Image("images/plants/potato.gif"));
        elementImage.setFitWidth(59);
        elementImage.setFitHeight(66);
        elementImage.setPreserveRatio(true);

        // Set the position for the image
        elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) );
        elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 10);
    }


    // Over-ride Potato to display different health-stages
    @Override
    public void takeDamage(int damage)
    {
        // Call the superclass implementation to apply damage
        super.takeDamage(damage);

        // Calculate health percentage based on original health (this.health is the current health)
        double healthPercentage = (double) this.health / 300;  // 200 is the original health value for Potato

        // Change image based on health percentage
        if (healthPercentage <= 0.30)
        {
            // Change to "potato-cracked2.gif" if health is 30% or less
            elementImage.setImage(new Image("images/plants/potato-cracked2.gif"));
        } else if (healthPercentage <= 0.75)
        {
            // Change to "potato-cracked1.gif" if health is 75% or less
            elementImage.setImage(new Image("images/plants/potato-cracked1.gif"));
        }

        // Log the current health for debugging
        System.out.println("Potato takes damage: " + damage + " Current health: " + this.health);
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