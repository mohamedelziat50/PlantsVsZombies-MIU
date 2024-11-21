public class HelmetZombie extends Zombie {
    public HelmetZombie(double health, double attackPower) {
        super(health, attackPower);
    }

    /**
     * @return
     */
    @Override
    public double getAttackPower() {
        return super.getAttackPower();
    }

    /**
     * @param attackPower
     */
    @Override
    public void setAttackPower(double attackPower) {
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
        super.action();
    }

    /**
     *
     */
    @Override
    public void disappear() {
        super.disappear();
    }
}
