import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application
{
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception
    {
        primaryStage = stage;
        primaryStage.setTitle("PVZ!");
        primaryStage.getIcons().add(new Image("images/seifsImages/icon.png"));

//            // Only testing for logic, not actual Main Code
//            ArrayList<Plant> unlockedPlants = new ArrayList<>();
//            unlockedPlants.add(new Peashooter());
//
//            ArrayList<Zombie> unlockedZombies = new ArrayList<>();
//            unlockedZombies.add(new DefaultZombie());
//
//            Level testLevel = new Level(1, 60, unlockedPlants, unlockedZombies);
//            testLevel.startLevel();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("yard-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
    }

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
}