import javafx.scene.layout.Pane;

public class Jalepeno extends Plant {

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Jalepeno()
    {
        super(150, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public Jalepeno(int x, int y)
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
    public void action() {
        //to be implemented
    }
}
