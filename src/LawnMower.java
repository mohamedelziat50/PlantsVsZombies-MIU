public class LawnMower extends MainElements{


    public LawnMower(int x) {
        this.position[x][0]++;
    }


    @Override
    public void setPosition(int[][] position) {
        super.setPosition(position);
    }


    @Override
    public int[][] getPosition() {
        return super.getPosition();
    }


    @Override
    public void action() {
     //if zombie x== lawnmower x clear the row
    }


    @Override
    public void disappear() {
        System.out.println("the lawn mower disappeared");
    }
}
