public class HelmetZombie extends Zombie {

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public HelmetZombie()
    {
        super(10, 5, 100);
    }

    // Added to be used when spawning a zombie on the yard
    public HelmetZombie(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;
    }

    @Override
    public double getAttackPower() {
        return super.getAttackPower();
    }

    /**
     * @param attackPower
     */
    @Override
    public void setAttackPower(int attackPower) {
        super.setAttackPower(attackPower);
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    

    @Override
    public boolean isAlive() {
        return super.isAlive(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void move() {
        super.move(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void setWaitingTime(double waitingTime) {
        super.setWaitingTime(waitingTime); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public double getWaitingTime() {
        return super.getWaitingTime(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void setY(int y) {
        super.setY(y); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public int getY() {
        return super.getY(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public void setX(int x) {
        super.setX(x); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public int getX() {
        return super.getX(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    

  
    @Override
    public void disappear() {
        super.disappear();
    }
}
