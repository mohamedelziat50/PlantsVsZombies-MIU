import javafx.scene.layout.Pane;


public class Sun extends MainElements
{
    public Sun() {}

    public Sun(int x,int y)
    {
        super(x, y);
    }

    @Override
    public void appear(Pane root)
    {

    }

    @Override
    public void disappear(Pane root)
    {
        
        System.out.println("sun collected");
        //assuming that the sun was collected and added to the player resources
    }
}
