import javafx.scene.layout.Pane;

public class IcedPea extends Plant
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public IcedPea()
    {
        super(150, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public IcedPea(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
    }

    @Override
    public void takeDamage(int damage)
    {

    }

    @Override
    public void run()
    {

    }

    @Override
    public void action()
    {
        /*

        int origzombieSpeed=zombie.getSpeed();
        int zombieSpeed=zombie.getSpeed()-10;

        while(isAlive()){
            pea.shot(zombie);
            if(pea.getPeaPositionX()== zombie.getX()) {
                zombie.setSpeed(zombieSpeed);
            }
            //sleep
            //zombie.setSpeed(origzombieSpeed);
            */

    }
}
