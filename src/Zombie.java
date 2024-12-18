import javafx.scene.layout.Pane;

public abstract class Zombie extends Characters
{
    protected int attackPower;
    protected int speed;
    // ADD WAITING TIME FOR ATTACKING

    public Zombie() {}

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Zombie(int attackPower,int speed, int health)
    {
        this.attackPower = attackPower;
        this.speed = speed;
        this.health = health;
    }

    public double getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    
    public abstract void move();

    public abstract void takeDamage(int damage);

    @Override
    public abstract void action();

    @Override
    public abstract void appear(Pane root);

    @Override
    public abstract void disappear(Pane root);
}
