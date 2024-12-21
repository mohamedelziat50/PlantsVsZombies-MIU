import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class IcedPeashooter extends Plant
{
    // Added to be able to use in the loading of files related to "level" class & in fileOperations interface
    public IcedPeashooter()
    {
        super(175, 15, 50);
    }

    // Added to be used when placing a plant on the yard
    public IcedPeashooter(int x, int y)
    {
        this();
        super.x = x;
        super.y = y;

        // Initialize the Peashooter image
        elementImage = new ImageView(new Image("images/plants/icedpeashooter_gif.gif"));
        elementImage.setFitWidth(76);
        elementImage.setFitHeight(70);
        elementImage.setPreserveRatio(true);


        // Set the position for the image
        elementImage.setLayoutX((x - elementImage.getFitWidth() / 2) + 5);
        elementImage.setLayoutY((y - elementImage.getFitHeight() / 2) - 25);
    }

    @Override
    public synchronized void run()
    {
        // While the plant is alive, keep shooting.
        while (isAlive() && !Thread.currentThread().isInterrupted()) // member variable inside characters (inherited)
        {
            try
            {
                // Shoot a pea every 7 seconds to be different than normal pea
                Thread.sleep(7000);

                if (!isAlive() || Thread.currentThread().isInterrupted()) {
                    break; // Exit the loop immediately
                }

                // Pass this plant as a reference to stop the thread in case plant dies!
                snowPea pea = new snowPea(20, this);

                // Spawn pea at same location of plant
                pea.elementImage.setLayoutX(elementImage.getLayoutX() + 65);
                pea.elementImage.setLayoutY(elementImage.getLayoutY() + 25);

                pea.appear(Yard.root);
                // Create a thread of the pea to run independently

                Thread peaThread = new Thread(pea);
                peaThread.setDaemon(true); // Ensure it stops with the app
                peaThread.start();
                peaShooterAudio();
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

    public void peaShooterAudio() {
        try {
            String path = getClass().getResource("/music/peashooter shoots.mp3").toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.9);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing peashooter sound: " + e.getMessage());
        }
    }

    @Override
    public void takeDamage(int damage)
    {
        // Call the superclass implementation to apply damage
        super.takeDamage(damage);

        // Add any specific behavior for this subclass if needed
        System.out.println("Peashooter takes damage: " + damage + " Current health: " + this.health);
    }

    @Override
    public void action()
    {

    }

}
