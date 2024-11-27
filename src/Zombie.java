public abstract class Zombie extends Characters
{
    protected int attackPower;
    protected int speed;

    public Zombie() {}

    // Added temporarily to be able to use in the loading of files related to "level" class & in fileOperations interfaace
    public Zombie(int attackPower,int speed, int health)
    {
        this.attackPower = attackPower;
        this.speed = speed;
        this.health = health;
    }

    public Zombie(int attackPower, int x, int y, int health,int speed) {
        super(x, y, health);
        this.attackPower = attackPower;
        this.speed=speed;
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
    public void disappear() {
      
        System.out.println("zombie is dead");
      
    }
}
