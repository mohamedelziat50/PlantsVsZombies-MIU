import javafx.scene.layout.Pane;

public class Cherry extends Plant
{

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Cherry()
    {
        super(100, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public Cherry(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
    }

    @Override
    public int getCost()
    {
        return super.getCost();
    }

    @Override
    public void setCost(int cost)
    {
        super.setCost(cost);
    }

    @Override
    public double getWaitingTime()
    {
        return super.getWaitingTime();
    }

    @Override
    public void setWaitingTime(double waitingTime)
    {
        super.setWaitingTime(waitingTime);
    }

    @Override
    public double getHealth()
    {
        return super.getHealth();
    }

    @Override
    public void setHealth(int health)
    {
        super.setHealth(health);
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
    public void action()
    {
        //to be implemented
    }

    @Override
    public void appear(Pane root)
    {

    }

    @Override
    public void disappear() {

    }
}
