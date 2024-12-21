import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Zombie extends Characters
{
    protected int attackPower;
    protected double speed;
    // ADD WAITING TIME FOR ATTACKING

    private volatile boolean isAttacking = false;
    private volatile boolean slowed = false;

    public Zombie() {}

    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Zombie(int attackPower,double speed, int health)
    {
        this.attackPower = attackPower;
        this.speed = speed;
        this.health = health;
    }

    public boolean isSlowed() {
        return slowed;
    }

    public void setSlowed(boolean slowed) {
        this.slowed = slowed;
    }

    public double getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // Used in collision handling with any object!
    public boolean isColliding(ImageView object)
    {
        // Shrink bounds for more precise collision detection
        double margin = 50; // Adjust as needed

        // Get the zombie's bounds
        var zombieBounds = elementImage.getBoundsInParent(); // var -> specify any datatype you want, no need to explicitly declare it

        // Get the object's bounds (Pea or plant)
        var objectBounds = object.getBoundsInParent();

        // Shrink the zombie bounds for more precise bounds with a specific margin
        var adjustedZombieBounds = new javafx.geometry.BoundingBox(
                zombieBounds.getMinX() + margin,
                zombieBounds.getMinY() + margin,
                zombieBounds.getWidth() - 2 * margin,
                zombieBounds.getHeight() - 2 * margin
        );

        // Use built-in intersects object of the Bounds class
        return adjustedZombieBounds.intersects(objectBounds);
    }

    // This function checks whether the current zombie thread collided with a plant through the zombie's isColliding function
    public Plant checkForPlantCollision()
    {
        synchronized (Yard.plants)
        {
            for (Plant plant: Yard.plants)
            {
                if (isColliding(plant.elementImage)) {
                    return plant; // Return the first plant it collides with
                }
            }
        }
        return null; // No collision
    }

    // This function moves the zombie thread throughout its lifetime
    public synchronized void move()
    {
        // Skip movement if already attacking or dead
        if (!isAlive() || isAttacking) return;

        Plant targetPlant = checkForPlantCollision();

        if (targetPlant != null && targetPlant.isAlive()) {
            attack(targetPlant); // Handle attack
        } else {
            Platform.runLater(() -> {
                elementImage.setLayoutX(elementImage.getLayoutX() - speed);
            });

            if (elementImage.getLayoutX() < 0) {
                System.out.println("game over");
                System.exit(0);//change to a reasonble function
            }

            try {
                Thread.sleep(1); // Mantain speed smoothness, GREATER = MORE ZOMBIE LAG
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Zombie movement thread interrupted");
            }
        }
    }


    @Override
    public void takeDamage(int damage) {
        health -= damage;

        // Create a ColorAdjust to increase brightness
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.5); // Increase brightness
        elementImage.setEffect(colorAdjust);

        System.out.println("Zombie takes damage: " + damage);

        // Create a Timeline to reset the brightness after 0.5 seconds
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(200), // Duration of 0.5 seconds
                        new KeyValue(colorAdjust.brightnessProperty(),0) // Reset effect to normal
                )
        );
        timeline.setCycleCount(1); // Run the timeline only once
        timeline.play(); // Start the timeline

        // Check if the zombie is dead
        if (health <= 0) {
            setAlive(false); // Mark zombie as dead

            synchronized (Yard.zombies) {
                Yard.zombies.remove(this); // Remove from the list
            }

            Platform.runLater(() -> {
                disappear(Yard.root); // Remove zombie from the screen
            });


        }
    }


    // This functions attacks a collided plant (Thread since it also accesses shared resources of itself, and Plant array list)
    private void attack(Plant targetPlant)
    {
        // If zombie is current attacking, prevent many threads for attacking.
        if (isAttacking)
            return;

        // Set is attacking to be true to create a new attacking thread
        isAttacking = true;

        // Store original speed to use later
        double originalSpeed = this.getSpeed();

        // Stop movement
        setSpeed(0);
        if(this instanceof FootballZombie){
            elementImage.setImage(new Image("images/zombies1/FootballZombieAttack.gif"));
            elementImage.setFitWidth(120);
            elementImage.setFitHeight(125);
            elementImage.setPreserveRatio(true);

        }
       else if(this instanceof ConeZombie){
            elementImage.setImage(new Image("images/zombies1/ConeheadZombieAttack.gif"));
            elementImage.setFitHeight(155);
            elementImage.setFitWidth(134);
            elementImage.setPreserveRatio(true);
        }
       else if(this instanceof DefaultZombie){
            elementImage.setImage(new Image("images/zombies1/ZombieAttack.gif"));
            elementImage.setFitHeight(155);
            elementImage.setFitWidth(134);
            elementImage.setPreserveRatio(true);
        }
       else if(this instanceof HelmetZombie){
            elementImage.setImage(new Image("images/zombies1/BucketheadZombieAttack.gif"));
            elementImage.setFitHeight(155);
            elementImage.setFitWidth(134);
            elementImage.setPreserveRatio(true);
        }

        // Attack the plant
        Thread attackThread = new Thread(() -> {
            try {
                while (isAlive() && targetPlant.isAlive() && isColliding(targetPlant.elementImage))
                {
                    targetPlant.takeDamage(attackPower);

                    // Attack every 3 seconds
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                System.err.println("Attack thread interrupted");
            } finally {
                isAttacking = false;
                Platform.runLater(()->{
                    if(this instanceof FootballZombie){
                        elementImage.setImage(new Image("images/zombies1/FootballZombie.gif"));
                        elementImage.setFitWidth(120);
                        elementImage.setFitHeight(125);
                        elementImage.setPreserveRatio(true);
                    }
                    else if(this instanceof DefaultZombie){
                          elementImage.setImage(new Image("images/zombies1/Zombie.gif"));
                        elementImage.setFitHeight(155);
                        elementImage.setFitWidth(134);
                        elementImage.setPreserveRatio(true);

                    }
                    else if(this instanceof ConeZombie){
                        elementImage.setImage(new Image("images/zombies1/ConeZombie.gif"));
                        elementImage.setFitHeight(155);
                        elementImage.setFitWidth(134);
                        elementImage.setPreserveRatio(true);
                    }
                    else if(this instanceof HelmetZombie){
                       elementImage.setImage(new Image("images/zombies1/BucketheadZombie.gif"));
                        elementImage.setFitHeight(155);
                        elementImage.setFitWidth(134);
                        elementImage.setPreserveRatio(true);
                    }
                });

                setSpeed(originalSpeed);
            }
        });

        attackThread.setDaemon(true);
        attackThread.start();
    }


    @Override
    public abstract void action();

    @Override
    public abstract void appear(Pane root);

    public void appear(Pane root,int x,int y)
    {
        Platform.runLater(() -> {
            elementImage.setLayoutX(x);
            elementImage.setLayoutY(y);

            root.getChildren().add(elementImage);
        });

        setAlive(true);
    }


    @Override
    public void disappear(Pane root)
    {
        System.out.println("Zombie died!");
        if(this instanceof FootballZombie){
            elementImage.setImage(new Image("images/zombies1/FootballZombieDie.gif"));
            elementImage.setFitWidth(134);
            elementImage.setFitHeight(150);
            elementImage.setPreserveRatio(true);
        }
        else{
            elementImage.setImage(new Image("images/zombies1/ZombieDie.gif"));
            elementImage.setFitHeight(155);
            elementImage.setFitWidth(134);
            elementImage.setPreserveRatio(true);
        }
        double gifDurationInSeconds;

        if(this instanceof FootballZombie){
        gifDurationInSeconds=0.8;
        }
        else{
        gifDurationInSeconds=1.6;
        }


        // Create a PauseTransition to wait for the GIF to finish
        PauseTransition pause = new PauseTransition(Duration.seconds(gifDurationInSeconds));
        pause.setOnFinished(event -> {
            root.getChildren().remove(elementImage);
            System.out.println("Zombie removed from the screen!");
        });

        // Start the pause
        pause.play();
    }
}