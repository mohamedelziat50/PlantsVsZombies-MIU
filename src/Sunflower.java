import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Sunflower extends Plant
{
    private ImageView sunflowerImage;

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Sunflower()
    {
        super(50, 15, 30);
    }

    // Added to be used when placing a plant on the yard
    public Sunflower(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;

        // Initialize the Sunflower image
        sunflowerImage = new ImageView(new Image("images/plants/sunflower.gif"));
        sunflowerImage.setFitWidth(73);
        sunflowerImage.setFitHeight(70);
        sunflowerImage.setPreserveRatio(true);

        // Set the position for the image
        sunflowerImage.setLayoutX((x - sunflowerImage.getFitWidth() / 2) + 5);
        sunflowerImage.setLayoutY((y - sunflowerImage.getFitHeight() / 2) - 15);
    }
    
    /**
     * @return
     */
    @Override
    public int getCost() {
        return super.getCost();
    }

    /**
     * @param cost
     */
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

    /**
     * @param health
     */
    @Override
    public void setHealth(int health) {
        super.setHealth(health);
    }

    
    @Override
    public void action() {
     //to be implemented
    }

    @Override
    public void setY(int y) {
        super.setY(y); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public int getY() {
        return super.getY(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void setX(int x) {
        super.setX(x); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public int getX() {
        return super.getX(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }



    @Override
    public void appear(Pane root)
    {
        if(sunflowerImage != null)
            root.getChildren().add(sunflowerImage);
    }

    @Override
    public void disappear()
    {

    }
}
