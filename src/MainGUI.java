import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MainGUI extends Application implements FileOperations
{
    private HashMap<String, Player> users;
    private final StackPane signInContainer = new StackPane();
    private final StackPane signUpContainer = new StackPane();
    private final Pane Menu1 = new Pane();
    private final Pane levelMenu = new Pane();

    // Added primary stage to set scene anywhere in code.
    public static Stage primaryStage;
    public static Scene scene;

    @Override
    public void start(Stage stage)
    {
        primaryStage = stage;

    // Add ICON and title
    primaryStage.setTitle("Plants VS Zombies!");
    primaryStage.getIcons().add(new Image("images/others/christmasIcon.png"));

    // Main container (ROOT) using Pane layout
        AnchorPane root = new AnchorPane();

    // Scene and stage
        scene = new Scene(root, 800, 596); // Adding the root container to the scene while setting the scene dimensions
        primaryStage.setScene(scene);

    // To close the option of opening the game in full screen mode since it ruins the dimensions
        primaryStage.setResizable(false);

    // Display the loading screen at first
        LoadingScreen.showStartScreen(root);

    // Show the primary stage only after loading screen is initialized
        Platform.runLater(() -> {
            // Set the screen title
            primaryStage.setTitle("PVZ - Main Menu!");

            // Preload necessary assets
            SoundtrackPlayer.playMainMenutrack();
            Yard.preloadZombies();

            // Show the primary stage
            primaryStage.show();

            // Added this to center on screen once switched
            MainGUI.primaryStage.centerOnScreen();
        });



        // Load user data from binary file into the hashmap
        try {
            users = loadPlayers();
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }


        //Contents of Menu1 container
        // Background Image of menu 1
        ImageView background = new ImageView(new Image("images/menuImages/SIGNIN.jpg"));
        background.setFitWidth(800);
        background.setFitHeight(598);

        //SignIn Button creation and sizing
        Button signInButton = new Button();
        signInButton.setPrefSize(260, 55);
        signInButton.setLayoutX(429);
        signInButton.setLayoutY(207);
        signInButton.setRotate(9);
        signInButton.setOpacity(0);
        signInButton.setOnAction(e -> {
                buttonClickAudio();
                handleSignIn(root);
        }); //Linking the signIn button to the event handler

        //SignUp Button creation and sizing
        Button signUpButton = new Button();
        signUpButton.setPrefSize(260, 55);
        signUpButton.setLayoutX(421);
        signUpButton.setLayoutY(283);
        signUpButton.setRotate(10);
        signUpButton.setOpacity(0);
        signUpButton.setOnAction(e -> {
                buttonClickAudio();
                handleSignUp(root);
    }); //Linking the signUp button to the event handler

        //Exit Button creation and sizing
        Button exitButton = new Button();
        exitButton.setPrefSize(260, 55);
        exitButton.setLayoutX(410);
        exitButton.setLayoutY(358);
        exitButton.setRotate(12);
        exitButton.setOpacity(0);
        exitButton.setOnAction(e -> {
                buttonClickAudio();
                handleExit();
    }); //Linking the Exit button to the event handler

        //Adding the contents of Menu1 to their corresponding container
        Menu1.getChildren().addAll(background, signInButton, signUpButton, exitButton); //Adding all components to the Main menu 1 container
        root.getChildren().addAll(Menu1); //Adding the Menu1 container to the root container


//Contents of levelMenu container

        //level1 Button creation and sizing
        Button level1 = new Button();
        level1.setPrefSize(260, 55);
        level1.setLayoutX(429);
        level1.setLayoutY(207);
        level1.setRotate(9);
        level1.setOpacity(0);
        level1.setOnAction(e -> {
            buttonClickAudio();
            MainMenu.zombieHand(root);
            PauseTransition pause = new PauseTransition(Duration.seconds(5)); //allow the zombie hand to be done before switching scenes
            pause.setOnFinished(event -> {
                handleLevel1(root);
            });
            pause.play();
    }); //Linking the Level1 button to the event handler

        //level2 Button creation and sizing
        Button level2 = new Button();
        level2.setPrefSize(260, 55);
        level2.setLayoutX(421);
        level2.setLayoutY(283);
        level2.setRotate(10);
        level2.setOpacity(0);
        level2.setOnAction(e -> {
            buttonClickAudio();
            MainMenu.zombieHand(root);
            PauseTransition pause = new PauseTransition(Duration.seconds(5)); //allow the zombie hand to be done before switching scenes
            pause.setOnFinished(event -> {
                handleLevel2(root);
            });
            pause.play();
    }); //Linking the level2 button to the event handler

        //level3 Button creation and sizing
        Button level3 = new Button();
        level3.setPrefSize(260, 55);
        level3.setLayoutX(410);
        level3.setLayoutY(358);
        level3.setRotate(12);
        level3.setOpacity(0);
        level3.setOnAction(e -> {
            buttonClickAudio();
            MainMenu.zombieHand(root);
            PauseTransition pause = new PauseTransition(Duration.seconds(5)); //allow the zombie hand to be done before switching scenes
            pause.setOnFinished(event -> {
                handleLevel3(root);
            });
            pause.play();
        });

        //Linking the level3 button to the event handler

        //Level background
        ImageView levelMenuImage = new ImageView(new Image("images/menuImages/levelMenu.jpg"));
        levelMenuImage.setFitWidth(800);
        levelMenuImage.setFitHeight(598);
        levelMenu.getChildren().addAll(levelMenuImage,level1,level2,level3); //Adding the LevelMenu components to the SignIn container


        // SignIn Button hover effect
        signInButton.setOnMouseEntered(e -> {
            background.setImage(new Image("images/menuImages/hover1.jpg"));
        });
        signInButton.setOnMouseExited(e -> {
            background.setImage(new Image("images/menuImages/SIGNIN.jpg"));
        });

        // signUpButton Button hover effect
        signUpButton.setOnMouseEntered(e -> {
            background.setImage(new Image("images/menuImages/hover2.jpg"));
        });
        signUpButton.setOnMouseExited(e -> {
            background.setImage(new Image("images/menuImages/SIGNIN.jpg"));
        });

        // exitButton hover effect
        exitButton.setOnMouseEntered(e -> {
            background.setImage(new Image("images/menuImages/hover3.jpg"));
        });
        exitButton.setOnMouseExited(e -> {
            background.setImage(new Image("images/menuImages/SIGNIN.jpg"));
        });



        // level1 Button hover effect
        level1.setOnMouseEntered(e -> {

            levelMenuImage.setImage(new Image("images/menuImages/lev1Hover.jpg"));
        });
        level1.setOnMouseExited(e -> {
            levelMenuImage.setImage(new Image("images/menuImages/levelMenu.jpg"));
        });

        // level2 Button hover effect
        level2.setOnMouseEntered(e -> {
            levelMenuImage.setImage(new Image("images/menuImages/lev2Hover.jpg"));
        });
        level2.setOnMouseExited(e -> {
            levelMenuImage.setImage(new Image("images/menuImages/levelMenu.jpg"));
        });

        // level3 Button hover effect
        level3.setOnMouseEntered(e -> {
            levelMenuImage.setImage(new Image("images/menuImages/lev3Hover.jpg"));
        });
        level3.setOnMouseExited(e -> {
            levelMenuImage.setImage(new Image("images/menuImages/levelMenu.jpg"));
        });
    }

    //Handling of the SignIn button
    private void handleSignIn(Pane root) {
        // Dark transparent overlay
        Rectangle overlay = new Rectangle(800, 598); // Same dimensions as the window
        overlay.setFill(javafx.scene.paint.Color.BLACK);
        overlay.setOpacity(0.65); // Transparency adjustment

        //Sizing of the SignIn container
        signInContainer.setPrefWidth(457);
        signInContainer.setPrefHeight(318);
        signInContainer.setLayoutX(210);
        signInContainer.setLayoutY(152);

        //Creating and sizing the background image that appears when sign in is pressed
        ImageView backgroundImage = new ImageView(new Image("images/menuImages/Inscene2.png"));
        backgroundImage.setFitWidth(395);
        backgroundImage.setFitHeight(275);

        //Creating the textfield and dealing with the related sizing and dimensions
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(160);
        usernameField.setMaxHeight(20);
        usernameField.setTranslateY(-35);
        usernameField.setTranslateX(10);

        //Creating the passField and dealing with the related sizing and dimensions
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(160);
        passwordField.setMaxHeight(20);
        passwordField.setTranslateY(6);
        passwordField.setTranslateX(11);

        // Back Button to close the sign-in menu and remove the overlay
        Button back = new Button();
        back.setMaxWidth(155);
        back.setTranslateY(51);
        back.setTranslateX(13);
        back.setOpacity(0);
//Adding the signin window components to their container
        signInContainer.getChildren().addAll(backgroundImage, usernameField, passwordField, back); //Adding the signIn components to the SignIn container
        root.getChildren().addAll(overlay,signInContainer);//Adding the sign in container and the overlay to the root


        //Back button handling
        back.setOnAction(e -> {
            buttonClickAudio();
            root.getChildren().removeAll(overlay,signInContainer); // Removes the sign in container and the overlay in order to return to menu 1 when the back button is pressed
        });


        //Some text styling for the prompt text of the usernameField and PasswordField
        usernameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black; -fx-prompt-text-fill: gray;");
        passwordField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black; -fx-prompt-text-fill: gray;");

        //Handling the password field after enter is pressed
        passwordField.setOnAction(e -> {
            String username = usernameField.getText(); //Username is saved into a string
            String password = passwordField.getText(); //Pass saved to a string

            //If Else statements to check if the entered credentials are valid or not
            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) { //Checks if the hashMap contains a key identical to the username and if the password related to this key is identical to the entered password
                root.getChildren().removeAll(overlay, signInContainer, Menu1);


                //label that displays the user who is currently signed in
                Label usernameLabel = new Label(username);
                usernameLabel.setLayoutX(144);
                usernameLabel.setLayoutY(86);
                usernameLabel.setStyle("-fx-font-family: 'Lucida Console'; -fx-font-size: 12px; -fx-text-fill: white;");

                //Creating the logout button and initializing the dimensions and the sizing of it
                Button logOut=new Button();
                logOut.setPrefWidth(76);
                logOut.setPrefHeight(69);
                logOut.setLayoutX(564);
                logOut.setLayoutY(462);
                logOut.setOpacity(0);

                //Creating the logout button and initializing the dimensions and the sizing of it
                Button exit=new Button();
                exit.setPrefWidth(76);
                exit.setPrefHeight(69);
                exit.setLayoutX(704);
                exit.setLayoutY(478);
                exit.setOpacity(0);
                exit.setOnAction(event -> {
                        buttonClickAudio();
                        handleExit();
            }); //Linking the Exit button to the event handler


                levelMenu.getChildren().addAll(usernameLabel,logOut,exit); //Adding the components to Menu2 container
                root.getChildren().addAll(levelMenu);
                showAlert(root, "Sign-In Successful", "Welcome, " + username);

                logOut.setOnAction(event -> {
                    buttonClickAudio();
                    levelMenu.getChildren().removeAll(usernameLabel,logOut,exit);
                    root.getChildren().removeAll(levelMenu); // Remove level menu components
                    root.getChildren().addAll(Menu1);; // Redisplay the main menu
                    showAlert(root, "Logging out", "Bye, "+username);
                });

                //Display alert if signIn failed
            } else {
                root.getChildren().removeAll(signInContainer,overlay);
                showAlert(root, "Sign-In Failed", "Invalid username or password.");
            }


        });

    }



    //Handling of the SignUp Button
    private void handleSignUp(Pane root) {
        // Dark transparent overlay
        Rectangle overlay = new Rectangle(800, 598); // Same dimensions as the window
        overlay.setFill(javafx.scene.paint.Color.BLACK);
        overlay.setOpacity(0.65); // Adjust this value (0.0 - 1.0) for transparency

        // Sign-Up container sizing and dimensions
        signUpContainer.setPrefWidth(457);
        signUpContainer.setPrefHeight(318);
        signUpContainer.setLayoutX(210);
        signUpContainer.setLayoutY(152);

        //Image that appears when sign up is clicked
        ImageView backgroundImage = new ImageView(new Image("images/menuImages/Inscene2.png"));
        backgroundImage.setFitWidth(395);
        backgroundImage.setFitHeight(275);

        // Username Field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter new username");
        usernameField.setMaxWidth(160);
        usernameField.setMaxHeight(20);
        usernameField.setTranslateY(-35);
        usernameField.setTranslateX(10);

        // Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter new password");
        passwordField.setMaxWidth(160);
        passwordField.setMaxHeight(20);
        passwordField.setTranslateY(6);
        passwordField.setTranslateX(11);

        // Back Button to close the sign-up menu and remove the overlay
        Button back = new Button();
        back.setMaxWidth(155);
        back.setTranslateY(51);
        back.setTranslateX(13);
        back.setOpacity(0);

        //Back button handling
        back.setOnAction(e -> {
            buttonClickAudio();
            root.getChildren().removeAll(overlay, signUpContainer); // Remove both the overlay and menu
        });

        //Adding all elements to signUp container
        signUpContainer.getChildren().addAll(backgroundImage, usernameField, passwordField, back);

        // Add the overlay and SignupContainer to the root
        root.getChildren().addAll(overlay, signUpContainer);

        //Prompt text styling for the usernameField and passField
        usernameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black; -fx-prompt-text-fill: gray;");
        passwordField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black; -fx-prompt-text-fill: gray;");

        //Handling the PassField
        passwordField.setOnAction(e -> {
            String username = usernameField.getText(); //Saving entered username to string
            String password = passwordField.getText(); //Saving entered pass to string

            //Check if the username already exists
            if (!users.containsKey(username)) {
                Player newPlayer = new Player(username, password); //If username doesn't exist create a new player
                users.put(username, newPlayer); //Add the player to the hashMap

                //Try Catch Block to save the player into the file
                try {
                    writePlayers(users);
                    showAlert(root, "Account Created", "Welcome, " + username);
                    root.getChildren().removeAll(overlay, signUpContainer);
                } catch (IOException ex) {
                    showAlert(root, "Save Failed", "Could not save user data.");
                }
            }
            //If username exists display alert
            else {
                root.getChildren().removeAll(signUpContainer,overlay);
                showAlert(root ,"Username Taken", "Please choose a different username.");
            }
        });
    }



    //Handling of the Exit Button
    private void handleExit() {
        buttonClickAudio();
        System.exit(0);
    }

    //Handling of level1
    private void handleLevel1 (Pane root)
    {
        SoundtrackPlayer.stopTrack();
        SoundtrackPlayer.playInGametrack1();
        Level testLevel = new Level(1, 60);
        testLevel.startLevel();
        System.out.println("DONE level1");
    }

    //Handling of level2
    private void handleLevel2 (Pane root){
        SoundtrackPlayer.stopTrack();
        SoundtrackPlayer.playInGametrack2();
        Level testLevel = new Level(2, 60);
        testLevel.startLevel();
        System.out.println("DONE level2");
    }

    //Handling of level3
    private void handleLevel3 (Pane root) {
        SoundtrackPlayer.stopTrack();
        SoundtrackPlayer.playInGametrack3();
        Level testLevel = new Level(3, 60);
        testLevel.startLevel();
        System.out.println("DONE level3");
    }

    //Function to display alert when needed
    private void showAlert(Pane root, String header, String content) {
        //Alert background
        ImageView Alert = new ImageView(new Image("images/menuImages/pvz.png"));
        Alert.setFitWidth(422);
        Alert.setFitHeight(319);
        Alert.toFront();

        // label that displays the alert message
        Label messageLabel = new Label(content);
        messageLabel.setStyle("-fx-font-family: 'Book Antiqua'; -fx-font-size: 10px; -fx-text-fill: white;");
        messageLabel.setTranslateY(10);
        messageLabel.setTranslateX(0);
        messageLabel.prefHeight(21);
        messageLabel.prefWidth(192);

        Label Alerttitle = new Label(header);
        Alerttitle.setStyle("-fx-font-family: 'Book Antiqua'; -fx-font-size: 10px; -fx-text-fill: red;");
        Alerttitle.setTranslateY(-26);
        Alerttitle.setTranslateX(0);
        Alerttitle.prefHeight(21);
        Alerttitle.prefWidth(192);
        System.out.println("Alert triggered");

        // OK button to exit the alert
        Button okButton = new Button("OK");
        okButton.setPrefHeight(25);
        okButton.setPrefWidth(188);
        okButton.setTranslateY(58);
        okButton.setTranslateX(4);
        okButton.setOpacity(0);


        // Alert container
        StackPane alertContainer = new StackPane();
        alertContainer.getChildren().addAll(Alert,messageLabel, Alerttitle, okButton);
        alertContainer.setPrefWidth(457);
        alertContainer.setPrefHeight(318);
        alertContainer.setLayoutX(210);
        alertContainer.setLayoutY(152);
        root.getChildren().addAll(alertContainer); //Adding the alert container to the root pane
        alertContainer.toFront();

        //Ok button handling
        okButton.setOnAction(e -> {
            buttonClickAudio();
            root.getChildren().removeAll(alertContainer); //Removes the alert container to exit
        });
    }

    public void buttonClickAudio() {
        try {
            // Path to the sun collected sound
            String path = getClass().getResource("/music/button click.mp3").toExternalForm();
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.5);
            javafx.application.Platform.runLater(() -> mediaPlayer.play());

        } catch (Exception e) {
            System.out.println("Error playing button click sound: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}