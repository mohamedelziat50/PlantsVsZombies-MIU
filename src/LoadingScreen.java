import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.Random;

public class LoadingScreen
{
    private static AnchorPane root;

    private static final int LOADING_SCREEN_TIME = 7;

    private static final String[] LOADING_SCREEN_PATHS = {
            "/images/loadingScreens/feastivus_loadingscreen.png",
            "/images/loadingScreens/pvz2_loadingscreen.png"
    };

    private static String getRandomLoadingScreen()
    {
        Random random = new Random();
        int randomIndex = random.nextInt(LOADING_SCREEN_PATHS.length);
        return LOADING_SCREEN_PATHS[randomIndex];
    }

    public static void show(Stage stage)
    {
        // Root AnchorPane
        root = new AnchorPane();
        root.setPrefSize(600, 400);

        // Child AnchorPane
        AnchorPane childPane = new AnchorPane();
        childPane.setLayoutX(6.0);
        childPane.setPrefSize(800, 589);

        // ImageView
        ImageView backgroundImage = new ImageView();
        backgroundImage.setFitWidth(Yard.WIDTH);
        backgroundImage.setFitHeight(Yard.HEIGHT);
        backgroundImage.setLayoutX(-8.0);
        backgroundImage.setPickOnBounds(true);

        // Select a random image path
        String randomImagePath = getRandomLoadingScreen();
        backgroundImage.setImage(new Image(randomImagePath));

        // Add ImageView to child AnchorPane
        childPane.getChildren().add(backgroundImage);

        // Add child AnchorPane to root
        root.getChildren().add(childPane);

        // Set the scene to the stage
        Scene scene = new Scene(root);
        Platform.runLater(() -> stage.setScene(scene));

        // Pause for 5 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(LOADING_SCREEN_TIME));
        pause.setOnFinished(event -> {
            System.out.println("Entered Loading Screen");
            // Return to the main menu or perform another action

            MainGUI.primaryStage.setScene(MainGUI.scene); // Transition to main menu
            MainGUI.primaryStage.centerOnScreen();
        });

        pause.play();
    }

    public static void showStartScreen(AnchorPane root)
    {
        // Add loading screen
        Platform.runLater(() -> {
            ImageView loadingScreenImage = new ImageView(new Image("images/others/loadingScreen.png"));
            loadingScreenImage.setFitWidth(810);
            loadingScreenImage.setFitHeight(598);
            loadingScreenImage.setPreserveRatio(false);

            root.getChildren().add(loadingScreenImage);

            root.setOnMouseClicked(e->{
                root.getChildren().remove(loadingScreenImage);
            });

        });
    }
}

