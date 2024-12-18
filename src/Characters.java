import javafx.scene.layout.Pane;

import java.io.Serializable;

public abstract class Characters extends MainElements implements Serializable
{
    protected int health;
    protected double waitingTime;

    public Characters() {}

    public Characters(int health, double waitingTime)
    {
        this.health = health;
        this.waitingTime = waitingTime;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public double getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public boolean isAlive()
    {
        return health > 0;
    }

    // Added this to be over-ridden by plants, pea, and zombies.
    public abstract void action();

    @Override
    public abstract void appear(Pane root);

    @Override
    public abstract void disappear(Pane root);
}


