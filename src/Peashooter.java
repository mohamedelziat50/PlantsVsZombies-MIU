public class Peashooter extends Plant {

   private final Pea pea=new Pea(20);
   private Zombie zombie;

    // Added temporarily to be able to use in the loading of files related to "level" class & in fileOperations interface
   public Peashooter()
   {
       super(100, 15, 50);
   }

    public Peashooter(int x, int y)
    {
        super(100, 10, x, y, 100);
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
    public void action() {//to be implemented
    while(isAlive()){
        pea.shot(zombie);

    }
    }

    
  

    /**
     *
     */
    @Override
    public void disappear() {
        super.disappear();
    }
}
