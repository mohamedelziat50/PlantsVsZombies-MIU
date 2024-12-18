import javafx.scene.layout.Pane;

public class ConeZombie extends Zombie
{

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public ConeZombie()
    {
        super(10, 5, 100);
    }

    // Added to be used when spawning a zombie on the yard
    public ConeZombie(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
    }

    @Override
    public void takeDamage(int damage)
    {

    }

    @Override
    public void move()
    {

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

    @Override
    public void appear(Pane root, int x, int y) {

    }

    @Override
    public void disappear(Pane root)
    {
        //to be implemented
    }
}

