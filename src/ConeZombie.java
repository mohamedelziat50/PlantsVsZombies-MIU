import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ConeZombie extends Zombie
{

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public ConeZombie()
    {
        super(10, 2, 100);
        elementImage=new ImageView(new Image("images/zombies/coneZombie.gif"));
        elementImage.setFitHeight(130);
        elementImage.setFitWidth(100);
        elementImage.setPreserveRatio(true);
    }

    // Added to be used when spawning a zombie on the yard
    public ConeZombie(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
    }


    @Override
    public void action()
    {

    }

    @Override
    public void appear(Pane root)
    {
        //to be implemented
    }

}