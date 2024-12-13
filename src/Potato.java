public class Potato extends Plant
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Potato()
    {
        super(100, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public Potato(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
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

    /**
     * @param health
     */
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

    /**
     *
     */
    @Override
    public void disappear() {
        super.disappear();
    }
}
