import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Jalepeno extends Plant {

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Jalepeno()
    {
        super(100, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public Jalepeno(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;

        // Initialize the Peashooter image
        elementImage = new ImageView(new Image("images/plants/jalapeno.png"));
        elementImage.setFitWidth(41);
        elementImage.setFitHeight(78);
        elementImage.setPreserveRatio(true);


        // Set the position for the image
        elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) + 5);
        elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 25);
    }

    @Override
    public void run()
    {

    }

    @Override
    public void takeDamage(int damage)
    {

    }

    @Override
    public void action()
    {

    }
}
