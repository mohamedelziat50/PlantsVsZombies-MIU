import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class HelmetZombie extends Zombie {

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public HelmetZombie()
    {
        super(10, 0.5, 140);
        elementImage=new ImageView(new Image("images/zombies1/BucketheadZombie.gif"));
        elementImage.setFitWidth(135);
        elementImage.setFitHeight(120);
        elementImage.setPreserveRatio(true);

    }

    // Added to be used when spawning a zombie on the yard
    public HelmetZombie(int x, int y)
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