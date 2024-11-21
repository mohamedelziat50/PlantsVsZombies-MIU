public class Characters extends MainElements
{
    protected double health;

    public Characters(double health)
    {
        this.health = health;
    }

    public double getHealth()
    {
        return health;
    }

    public void setHealth(double health)
    {
        this.health = health;
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
    //not functioned yet
    }

    @Override
    public void disappear()
    {
        //not functioned yet
    }
}


