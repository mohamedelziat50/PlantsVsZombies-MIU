abstract public class MainElements
{
    protected int x;
    protected int y;

    public MainElements()
    {
        // position to be initialized with some dimensions.
    }

    public MainElements(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public abstract void disappear();      // when an object dies
}



