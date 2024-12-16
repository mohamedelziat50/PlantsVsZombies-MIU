import javafx.scene.layout.Pane;

public class IcedPea extends Plant
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public IcedPea()
    {
        super(150, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public IcedPea(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
    }

    @Override
    public void setHealth(int health) {
        super.setHealth(health); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public double getHealth() {
        return super.getHealth(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void setWaitingTime(double waitingTime) {
        super.setWaitingTime(waitingTime); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public double getWaitingTime() {
        return super.getWaitingTime(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void setCost(int cost) {
        super.setCost(cost); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public int getCost() {
        return super.getCost(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
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
    public void takeDamage(int damage) {
        super.takeDamage(damage); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isAlive() {
        return super.isAlive(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void action()
    {
        /*

        int origzombieSpeed=zombie.getSpeed();
        int zombieSpeed=zombie.getSpeed()-10;

        while(isAlive()){
            pea.shot(zombie);
            if(pea.getPeaPositionX()== zombie.getX()) {
                zombie.setSpeed(zombieSpeed);
            }
            //sleep
            //zombie.setSpeed(origzombieSpeed);
            */

    }

    @Override
    public void appear(Pane root)
    {

    }

    @Override
    public void disappear() {

    }
   
}
