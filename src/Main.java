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
        //Set the static primary stage as the current stage
        primaryStage = stage;

        primaryStage.setTitle("PVZ!");
        primaryStage.getIcons().add(new Image("images/others/icon.png"));

        Soundtrackplayer soundtrackplayer = new Soundtrackplayer();
        soundtrackplayer.playSoundtrack();

        // Only testing for logic, not actual Main Code
        ArrayList<Plant> unlockedPlants = new ArrayList<>();
        unlockedPlants.add(new Peashooter());



        ArrayList<Zombie> unlockedZombies = new ArrayList<>();
        unlockedZombies.add(new DefaultZombie());

        Level testLevel = new Level(1, 60, unlockedPlants, unlockedZombies);
        testLevel.startLevel();
        primaryStage.show();

      /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("yard-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
       */

    }

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
}