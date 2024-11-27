public abstract class Plant extends Characters
{
    protected int cost;

    public Plant() {}

    // Added temporarily to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Plant(int cost, double waitingTime, int health)
    {
        this.cost = cost;
        this.waitingTime = waitingTime;
        this.health = health;
    }

    public Plant(int cost, double waitingTime, int x, int y, int health) {
        super(x, y, health);
        this.cost = cost;
        this.waitingTime = waitingTime;
        
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
   

  

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
@Override
    public double getWaitingTime() {
        return waitingTime;
    }
@Override
    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

  
    public abstract void action();//in sunflower we will consider that it shoots suns 

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
    public void disappear() {
        
        System.out.println("plant is dead");
      
    }
}
