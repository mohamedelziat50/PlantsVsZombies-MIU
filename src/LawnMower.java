public class LawnMower extends MainElements
{
    public LawnMower() {}

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public void disappear()
    {
        System.out.println("The Lawn Mower Disappeared");
    }
}
