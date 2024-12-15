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
    public void setHealth(int health) {
        super.setHealth(health);
    }
@Override
    public int getX() {
        return x;
    }
@Override
    public void setX(int x) {
        this.x = x;
    }
@Override
    public int getY() {
        return y;
    }

   @Override
    public void setY(int y) {
        this.y = y;
    }
@Override
    public double getWaitingTime() {
        return waitingTime;
    }
@Override
    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

   
    
    public void move(){
        while(isAlive()){
            x--;
        }
        
    }
    

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean isAlive() {
        return super.isAlive(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }


    @Override
    public void appear(Pane root)
    {

    }

    @Override
    public void disappear() {
      
        System.out.println("zombie is dead");
      
    }
}
