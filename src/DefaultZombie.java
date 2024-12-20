import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DefaultZombie extends Zombie
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public DefaultZombie()
    {
        super(10, 2, 100);
        elementImage=new ImageView(new Image("images/zombies/walking-plants-vs-zombies.gif"));
        elementImage.setFitHeight(140);
        elementImage.setFitWidth(187);
        //elementImage.setPreserveRatio(true);

    }

    // Added to be used when spawning a zombie on the yard
    public DefaultZombie(int x, int y)
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
    public void appear(Pane root) {

    }

}


