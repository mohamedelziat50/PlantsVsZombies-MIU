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
        // 0.02 instead of 2 to mantain the smoothness without delaying movement of zombies and lagging
        super(10, 0.5, 100);
        elementImage=new ImageView(new Image("images/zombies1/Zombie.gif"));
        elementImage.setFitHeight(155);
        elementImage.setFitWidth(134);
        elementImage.setPreserveRatio(true);

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

