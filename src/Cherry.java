public class Cherry extends Plants
{
    public Cherry(double health, int cost, double waitingTime,int damage)
    {
        super(health, cost, waitingTime,damage);
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
    public void setHealth(double health)
    {
        super.setHealth(health);
    }

    @Override
    public void setPosition(int[][] position)
    {
        super.setPosition(position);
    }

    @Override
    public int[][] getPosition()
    {
        return super.getPosition();
    }

    @Override
    public void action()
    {
        //to be implemented
    }

    @Override
    public void disappear()
    {
        //to be implemented
    }
}
