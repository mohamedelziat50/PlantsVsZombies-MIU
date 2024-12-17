import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.shape.Rectangle;

public class MainGUI extends Application implements FileOperations {
    private HashMap<String, Player> users;

    @Override
    public void start(Stage primaryStage) {
        // Load user data from binary file into the hashmap
        try {
            users = loadPlayers();
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        Pane root = new Pane();

        // Background Image
        ImageView background = new ImageView(new Image("file:/C:/Users/Lenovo/Desktop/SIGNIN.jpg"));
        background.setFitWidth(800);
        background.setFitHeight(598);

        //SignIn Button creation and sizing
        Button signInButton = new Button();
        signInButton.setPrefSize(260, 55);
        signInButton.setLayoutX(429);
        signInButton.setLayoutY(207);
        signInButton.setRotate(9);
        signInButton.setOpacity(0);
        signInButton.setOnAction(e -> handleSignIn(root)); //Linking the signIn button to the event handler

        //SignUp Button creation and sizing
        Button signUpButton = new Button();
        signUpButton.setPrefSize(260, 55);
        signUpButton.setLayoutX(421);
        signUpButton.setLayoutY(283);
        signUpButton.setRotate(10);
        signUpButton.setOpacity(0);
        signUpButton.setOnAction(e -> handleSignUp(root)); //Linking the signUp button to the event handler


        //Exit Button creation and sizing
        Button exitButton = new Button();
        exitButton.setPrefSize(260, 55);
        exitButton.setLayoutX(410);
        exitButton.setLayoutY(358);
        exitButton.setRotate(12);
        exitButton.setOpacity(0);
        exitButton.setOnAction(e -> handleExit()); //Linking the Exit button to the event handler

        root.getChildren().addAll(background, signInButton, signUpButton, exitButton); //Adding all components to teh root container

        // Scene and stage
        Scene scene = new Scene(root, 800, 598);
        primaryStage.setTitle("Welcome to our plans vs zombies video Game!!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); //To close the option of opening the game in full screen mode since it ruins the dimensions
        primaryStage.show();
    }

    private void handleSignIn(Pane root) {
        // Step 1: Add the dark semi-transparent overlay
        Rectangle overlay = new Rectangle(800, 598); // Same dimensions as the window
        overlay.setFill(javafx.scene.paint.Color.BLACK);
        overlay.setOpacity(0.65); // Adjust this value (0.0 - 1.0) for transparency

        // Step 2: Create the sign-in container
        StackPane signInContainer = new StackPane();
        signInContainer.setPrefWidth(457);
        signInContainer.setPrefHeight(318);
        signInContainer.setLayoutX(210);
        signInContainer.setLayoutY(152);

        ImageView backgroundImage = new ImageView(new Image("file:/C:/Users/Lenovo/Desktop/Inscene2.jpeg"));
        backgroundImage.setFitWidth(395);
        backgroundImage.setFitHeight(275);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(205);
        usernameField.setMaxHeight(30);
        usernameField.setTranslateY(-35);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(205);
        passwordField.setMaxHeight(30);
        passwordField.setTranslateY(15);

        // Exit Button to close the sign-in menu and remove the overlay
        Button exitButton = new Button("Back to Main Menu");
        exitButton.setTranslateY(80);
        exitButton.setOnAction(e -> {
            root.getChildren().removeAll(overlay, signInContainer); // Remove both the overlay and menu
        });

        signInContainer.getChildren().addAll(backgroundImage, usernameField, passwordField, exitButton);

        // Step 3: Add the overlay and menu to the root
        root.getChildren().addAll(overlay, signInContainer);

        // Styling for fields (optional)
        usernameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black; -fx-prompt-text-fill: gray;");
        passwordField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black; -fx-prompt-text-fill: gray;");

        // Step 4: Handle Sign-In logic when pressing Enter in the password field
        passwordField.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                showAlert("Success", "Sign-In Successful", "Welcome, " + username);
                root.getChildren().removeAll(overlay, signInContainer); // Remove the menu and overlay

                // Show the level menu
                ImageView levelMenuImage = new ImageView(new Image("file:/C:/Users/Lenovo/Desktop/levelMenu.jpg"));
                levelMenuImage.setFitWidth(800);
                levelMenuImage.setFitHeight(598);

                Label usernameLabel = new Label(username);
                usernameLabel.setLayoutX(144);
                usernameLabel.setLayoutY(86);
                usernameLabel.setStyle("-fx-font-family: 'Lucida Console'; -fx-font-size: 12px; -fx-text-fill: white;");
                Label Bestproject = new Label("\uD83C\uDF1F Best Project \uD83C\uDF1F");
                Bestproject.setLayoutX(120);
                Bestproject.setLayoutY(155);


                root.getChildren().addAll(levelMenuImage, usernameLabel,Bestproject);
            } else {
                showAlert("Error", "Sign-In Failed", "Invalid username or password.");
            }
        });
    }



    //Handling of the SignUp Button
    private void handleSignUp(Pane root) {
        StackPane signUpContainer = new StackPane();
        signUpContainer.setPrefWidth(457);
        signUpContainer.setPrefHeight(318);
        signUpContainer.setLayoutX(210);
        signUpContainer.setLayoutY(152);

        ImageView backgroundImage = new ImageView(new Image("file:/C:/Users/Lenovo/Desktop/Inscene2.jpeg"));
        backgroundImage.setFitWidth(395);
        backgroundImage.setFitHeight(275);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter new username");
        usernameField.setMaxWidth(205);
        usernameField.setMaxHeight(30);
        usernameField.setTranslateY(-35);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter new password");
        passwordField.setMaxWidth(205);
        passwordField.setMaxHeight(30);
        passwordField.setTranslateY(15);

        signUpContainer.getChildren().addAll(backgroundImage, usernameField, passwordField);
        root.getChildren().add(signUpContainer);

        usernameField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black; -fx-prompt-text-fill: gray;");
        passwordField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black; -fx-prompt-text-fill: gray;");

        passwordField.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (!users.containsKey(username)) {
                Player newPlayer = new Player(username, password);
                users.put(username, newPlayer);

                try {
                    writePlayers(users);
                    showAlert("Success", "Account Created", "Welcome, " + username);
                    root.getChildren().remove(signUpContainer);
                } catch (IOException ex) {
                    showAlert("Error", "Save Failed", "Could not save user data.");
                }
            } else {
                showAlert("Error", "Username Taken", "Please choose a different username.");
            }
        });
    }


    //Handling of the Exit Button
    private void handleExit() {

        System.exit(0);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
