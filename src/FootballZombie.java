import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class FootballZombie extends Zombie {

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public FootballZombie()
    {

        super(20, 0.7, 250);
        elementImage=new ImageView(new Image("images/zombies1/FootballZombie.gif"));
        elementImage.setFitWidth(120);
        elementImage.setFitHeight(125);
        elementImage.setPreserveRatio(true);


    }

    // Added to be used when spawning a zombie on the yard
    public FootballZombie(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
    }

    @Override
    public void appear(Pane root)
    {
        //to be implemented
    }




}