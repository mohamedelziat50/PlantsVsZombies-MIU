import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application
{
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception
    {
        primaryStage = stage;
        primaryStage.setTitle("PVZ!");
        primaryStage.getIcons().add(new Image("images/others/icon.png"));

        SoundtrackPlayer soundtrackplayer = new SoundtrackPlayer();
        soundtrackplayer.playSoundtrack();


        // Only testing for logic, not actual Main Code
        ArrayList<Plant> unlockedPlants = new ArrayList<>();
        unlockedPlants.add(new Peashooter());

        ArrayList<Zombie> unlockedZombies = new ArrayList<>();
        unlockedZombies.add(new DefaultZombie());


        Level testLevel = new Level(1, 60, unlockedPlants, unlockedZombies);
        testLevel.startLevel();

        primaryStage.show();
    }

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
}