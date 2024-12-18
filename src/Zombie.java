import javafx.scene.image.ImageView;
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

    // Used in collision handling
    public boolean isColliding(ImageView pea)
    {
        // Shrink bounds for more precise collision detection
        double margin = 50; // Adjust as needed
        var zombieBounds = elementImage.getBoundsInParent(); // var -> specify any datatype you want, no need to explicitly declare it
        var peaBounds = pea.getBoundsInParent();

        var adjustedZombieBounds = new javafx.geometry.BoundingBox(
                zombieBounds.getMinX() + margin,
                zombieBounds.getMinY() + margin,
                zombieBounds.getWidth() - 2 * margin,
                zombieBounds.getHeight() - 2 * margin
        );

        return adjustedZombieBounds.intersects(peaBounds);
    }

    public abstract void move();

    public abstract void takeDamage(int damage);

    @Override
    public abstract void action();

    @Override
    public abstract void appear(Pane root);
    public abstract void appear(Pane root,int x,int y);


    @Override
    public abstract void disappear(Pane root);
}
