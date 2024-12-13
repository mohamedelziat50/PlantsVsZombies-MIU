public class Peashooter extends Plant {

   private Pea pea;

   // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
   public Peashooter()
   {
       super(100, 15, 50);
   }

   // Added to be used when placing a plant on the yard
   public Peashooter(int x, int y)
   {
       this();
       super.x = x;
       super.y = y;
   }

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
@Override
    public void setY(int y) {
        this.y = y;
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
    {//to be implemented

    }

    @Override
    public void disappear() {
        super.disappear();
    }
}
