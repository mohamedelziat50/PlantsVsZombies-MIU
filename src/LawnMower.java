import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


import javafx.scene.image.ImageView;

public class LawnMower extends MainElements
{
    public LawnMower()
    {
        elementImage =new ImageView( new Image("images/plants/LawnMower.png"));

        elementImage.setFitWidth(65);
        elementImage.setFitHeight(80);
        elementImage.setPreserveRatio(true);
    }

    @Override
    public void appear(Pane root)
    {

    }
    @Override
    public void disappear(Pane root)
    {
        System.out.println("The Lawn Mower Disappeared");
    }
}
