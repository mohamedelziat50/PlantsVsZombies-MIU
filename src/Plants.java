public abstract class Plants extends Characters
{
    protected int cost;
    protected double waitingTime;
    protected int damage;//add in uml diagram

    public Plants(double health, int cost, double waitingTime, int damage)
    {
        super(health);
        this.cost = cost;
        this.waitingTime = waitingTime;
        this.damage=damage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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
        super.disappear();//to be inmplemented
    }
}
