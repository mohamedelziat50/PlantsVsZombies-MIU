public class ConeZombie extends Zombie
{
    public ConeZombie(double health, double attackPower)
    {
        super(health, attackPower);
    }

    @Override
    public double getAttackPower()
    {
        return super.getAttackPower();
    }

    @Override
    public void setAttackPower(double attackPower)
    {
        super.setAttackPower(attackPower);
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

