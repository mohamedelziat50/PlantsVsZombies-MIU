public class IcedPea extends Plants{
    public IcedPea(double health, int cost, double waitingTime, int damage) {
        super(health, cost, waitingTime, damage);
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
    public void setHealth(double health) {
        super.setHealth(health);
    }

    /**
     * @param position
     */
    @Override
    public void setPosition(int[][] position) {
        super.setPosition(position);
    }

    /**
     * @return
     */
    @Override
    public int[][] getPosition() {
        return super.getPosition();
    }

    /**
     *
     */
    @Override
    public void action() {
        super.action();//to be implemented
    }

    /**
     *
     */
    @Override
    public void disappear() {
        super.disappear();
    }
}
