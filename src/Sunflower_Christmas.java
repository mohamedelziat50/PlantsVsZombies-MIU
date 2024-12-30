import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sunflower_Christmas extends Sunflower
{
    public Sunflower_Christmas(int x, int y)
    {
        super();
        super.x = x;
        super.y = y;

        // Initialize the Sunflower image
        elementImage = new ImageView(new Image("images/christmasPlants/sunflowerChristmas.gif"));
        elementImage.setFitWidth(90);
        elementImage.setFitHeight(79);
        elementImage.setPreserveRatio(true);

        // Set the position for the image
        elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) + 5);
        elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 15);
    }
}
