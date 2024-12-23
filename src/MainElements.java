import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class MainElements
{
    protected int x;
    protected int y;

    // Now all elements inherit an imageview!
    protected ImageView elementImage;

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

    public ImageView getElementImage() {
        return elementImage;
    }

    public void setElementImage(ImageView elementImage) {
        this.elementImage = elementImage;
    }

    public abstract void appear(Pane root);
    public abstract void disappear(Pane root);      // when an object dies
}


