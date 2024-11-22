public abstract class Characters extends MainElements
{
    protected int health;
   protected double waitingTime;

    public Characters() 
    {
        
    }

    public Characters(int x, int y,int health) {
        super(x, y);
        this.health=health;
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
        return health>0;
    }
  

   public void takeDamage(int damage)
   {
       if(isAlive()){
           health-=damage;
       }
       else{
           disappear();
       }
   }
   
   

    @Override
    public abstract void disappear();
    
}


