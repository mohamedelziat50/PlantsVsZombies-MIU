import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application
{
    // public static stage, in order to set scene anywhere in code!
    public static Stage primaryStage;

    @Override
    public void start(Stage stage)
    {
        // Required to launch correctly.
        primaryStage = stage;

        try
        {
            primaryStage.setTitle("PVZ!");

            // Only testing for logic, not actual Main Code
            ArrayList<Plant> unlockedPlants = new ArrayList<>();
            unlockedPlants.add(new Peashooter());

            ArrayList<Zombie> unlockedZombies = new ArrayList<>();
            unlockedZombies.add(new DefaultZombie());

            Level testLevel = new Level(1, 60, unlockedPlants, unlockedZombies);
            testLevel.startLevel();
            primaryStage.show();
        }
        catch(Exception ex)
        {
            System.out.println("Exception: " + ex);
        }

        /*
        Old MainMenu Code

        // Create Scanner Object
        Scanner input = new Scanner(System.in);

        MainMenu myMenu = new MainMenu();
        myMenu.displayMenu(input);
         */

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}