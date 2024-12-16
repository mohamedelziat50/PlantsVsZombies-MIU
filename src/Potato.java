import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Potato extends Plant
{
    private ImageView potatoImage;

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Potato()
    {
        super(50, 15, 200);
    }

    // Added to be used when placing a plant on the yard
    public Potato(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;

        // Initialize the Potato image
        potatoImage = new ImageView(new Image("images/plants/potato.gif"));
        potatoImage.setFitWidth(59);
        potatoImage.setFitHeight(66);
        potatoImage.setPreserveRatio(true);

        // Set the position for the image
        potatoImage.setLayoutX((x - potatoImage.getFitWidth() / 2) );
        potatoImage.setLayoutY((y - potatoImage.getFitHeight() / 2) - 10);
    }
   

    @Override
    public int getCost() {
        return super.getCost();
    }

    @Override
    public void setCost(int cost) {
        super.setCost(cost);
    }

    /**
     * @return
     */
    @Override
    public double getWaitingTime() {
        return super.getWaitingTime();
    }

    /**
     * @param waitingTime
     */
    
    
    @Override
    public void setWaitingTime(double waitingTime) {
        super.setWaitingTime(waitingTime);
    }

    /**
     * @return
     */
    @Override
    public double getHealth() {
        return super.getHealth();
    }

    
    
    @Override
    public int getX() {
        return x;
    }
@Override
    public void setX(int x) {
        this.x = x;
    }
@Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setHealth(int health) {
        super.setHealth(health);
    }

   
    @Override
    public void action() {
        //to be implemented
    }

    @Override
    public void appear(Pane root)
    {
        if(potatoImage != null)
            root.getChildren().add(potatoImage);
    }

    @Override
    public void disappear() {
        potatoImage.setVisible(false);
    }
}
