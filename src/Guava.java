public class Guava extends Plant {

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Guava()
    {
        super(150, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public Guava(int x, int y)
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
    public int getCost() {
        return super.getCost();
    }

   
    @Override
    public void setCost(int cost) {
        super.setCost(cost);
    }

   
    @Override
    public double getWaitingTime() {
        return super.getWaitingTime();
    }

    
    @Override
    public void setWaitingTime(double waitingTime) {
        super.setWaitingTime(waitingTime);
    }

   
    @Override
    public double getHealth() {
        return super.getHealth();
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
    public void action() {
        //to be implemented
    }

    @Override
    public void disappear() {
        super.disappear();
    }
}
