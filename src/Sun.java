import java.util.*;


public class Sun extends MainElements
{
    protected int x;
    protected int y;

    public Sun() {}

    public Sun(int x,int y)
    {
        this.x = x;
        this.y=y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

   public void  move(){
      y--;
  }

  


    @Override
    public void disappear()
    {
        
        System.out.println("sun collected");
        //assuming that the sun was collected and added to the player resources
    }
}
