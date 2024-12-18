import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Peashooter extends Plant
{
   // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
   public Peashooter()
   {
       super(100, 15, 50);
   }

   // Added to be used when placing a plant on the yard
   public Peashooter(int x, int y)
   {
       this();
       super.x = x;
       super.y = y;

       // Initialize the Peashooter image
       elementImage = new ImageView(new Image("images/plants/peashooter.gif"));
       elementImage.setFitWidth(90);
       elementImage.setFitHeight(85);
       elementImage.setPreserveRatio(true);


       // Set the position for the image
       elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) + 5);
       elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 25);
   }

    @Override
    public void run()
    {
        // While the plant is alive, keep shooting.
        while (isAlive()) // member variable inside characters (inherited)
        {
            try
            {
                // Shoot a pea every 5 seconds
                Thread.sleep(5000);

                // Pass this plant as a reference to stop the thread in case plant dies!
                Pea pea = new Pea(50, this);

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

    }

    @Override
    public void action()
    {

    }

}
