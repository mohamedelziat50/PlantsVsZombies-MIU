import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Potato extends Plant
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Potato()
    {
        super(50, 15, 200);
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

    @Override
    public void takeDamage(int damage)
    {

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
