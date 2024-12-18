import java.io.Serializable;
import javafx.scene.layout.Pane;

public class Pea extends Characters implements Serializable
{
    protected int damage;
    
    public Pea(int damage)
    {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void shot(Zombie zombie)
    {
        this.x+=5; // for when the peashooter starts shooting peas
        if(zombie.getX()==this.x)
        {
            zombie.takeDamage(this.damage);
            // disappear(); comented out for now
        }
    }

    @Override
    public void action()
    {

    }

    @Override
    public void appear(Pane root)
    {

    }

    @Override
    public void disappear(Pane root)
    {
        System.out.println("Pea disappears.");
    }
}
