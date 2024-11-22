public class Potato extends Plants
{

    
    public Potato(int cost, double waitingTime, int x, int y, int health) {
        super(50, 15, x, y, 250);
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
