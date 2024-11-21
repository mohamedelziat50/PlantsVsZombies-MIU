public abstract class Zombie extends Characters{
    protected double attackPower;

    public Zombie(double health, double attackPower) {
        super(health);
        this.attackPower = attackPower;
    }

    public double getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(double attackPower) {
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
    public void setHealth(double health) {
        super.setHealth(health);
    }

    /**
     * @param position
     */
    @Override
    public void setPosition(int[][] position) {
        super.setPosition(position);
    }

    /**
     * @return
     */
    @Override
    public int[][] getPosition() {
        return super.getPosition();
    }

    /**
     *
     */
    @Override
    public void action() {
        super.action();//to be implemented
    }

    /**
     *
     */
    @Override
    public void disappear() {
        super.disappear();//to be implemented
    }
}
