import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Repeater extends Plant
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public Repeater()
    {
        super(200, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public Repeater(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;

        // Initialize the Peashooter image
        elementImage = new ImageView(new Image("images/plants/repeater.gif"));
        elementImage.setFitWidth(95);
        elementImage.setFitHeight(95);
        elementImage.setPreserveRatio(true);


        // Set the position for the image
        elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) + 5);
        elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 25);
    }

    @Override
    public void run()
    {
        // MAGETSH GAMBO LAZEM AZABTO ZAY EL PEA SHOOTER WEL ICED PEA SHOOTER
        // While the plant is alive, keep shooting.

        while (isAlive()) // member variable inside characters (inherited)
        {
            try
            {
                // Shoot a pea every 2.5 seconds since it is faster (repeater plant)
                Thread.sleep(2000);

                // Pass this plant as a reference to stop the thread in case plant dies!
                Pea pea = new Pea(40, this);

                // Spawn pea at same location of plant
                pea.elementImage.setLayoutX(elementImage.getLayoutX() + 65);
                pea.elementImage.setLayoutY(elementImage.getLayoutY() + 31);

                Platform.runLater(() -> {
                    // Add to the root pane
                    pea.appear(Yard.root);

                    // Create a thread of the pea to run independently
                    Thread peaThread = new Thread(pea);
                    peaThread.setDaemon(true); // Ensure it stops with the app
                    peaThread.start();
                });
            }
            catch (Exception e)
            {
                System.out.println("Peashooter thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Peashooter thread ended.");
    }

    @Override
    public void takeDamage(int damage)
    {
        // Call the superclass implementation to apply damage
        super.takeDamage(damage);

        // Add any specific behavior for this subclass if needed
        System.out.println("Repeater takes damage: " + damage + " Current health: " + this.health);
    }

    @Override
    public void action()
    {

    }

}
