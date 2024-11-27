import java.io.Serializable;

public class Pea implements Serializable
{
    protected int peaPositionX;
    protected int damage;

    
    public Pea(int damage)
    {
        this.damage = damage;
    }
    
    public int getDamage()
    {
        return damage;
    }

    public int getPeaPositionX() {
        return peaPositionX;
    }



    public void shot(Zombie zombie)
    {
        peaPositionX+=5; // for when the peashooter starts shooting peas
        if(zombie.getX()==this.getPeaPositionX()){
            zombie.takeDamage(this.damage);
            // delete the pea
        }
    }



}
