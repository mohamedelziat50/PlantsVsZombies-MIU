abstract public class MainElements
{
    protected int[][] position;

    public MainElements()
    {
        // position to be initialized with some dimensions.
    }

    public void setPosition(int[][] position)
    {
        this.position = position;
    }

    public int[][] getPosition()
    {
        return position;
    }

    public abstract void action();    // to be overridden

    public abstract void disappear();      // when an object dies


}



