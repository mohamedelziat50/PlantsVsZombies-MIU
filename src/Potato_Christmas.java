import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Potato_Christmas extends Potato
{
    public Potato_Christmas(int x, int y)
    {
        super();
        super.x = x;
        super.y = y;

        // Initialize the Potato image
        elementImage = new ImageView(new Image("images/christmasPlants/potatoChristmas.gif"));
        elementImage.setFitWidth(66);
        elementImage.setFitHeight(66);
        elementImage.setPreserveRatio(true);

        // Set the position for the image
        elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) );
        elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 10);
    }
}
