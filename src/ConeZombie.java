import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ConeZombie extends Zombie
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public ConeZombie()
    {
        super(10, 0.4, 150);
        elementImage=new ImageView(new Image("images/zombies1/ConeZombie.gif"));
        //ImageView image = new ImageView(new Image("images/zombies/walking-plants-vs-zombies.gif"));
        elementImage.setFitHeight(155);
        elementImage.setFitWidth(134);
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
    public void appear(Pane root) {

    }

}

