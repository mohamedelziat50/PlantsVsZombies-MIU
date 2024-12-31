import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu implements FileOperations
{
    /*
        MEMBER VARIABLES
     */
    // HashMap to swiftly access all the players' data.
    private HashMap<String, Player> PlayersGameData;

    // Key is the level number, and the value is the corresponding Level object. This allows the Player to choose from the unlocked levels.
    // MainMenu can filter LevelGameData using the unlockedLevels list to display only those levels for selection.
    private HashMap<Integer, Level> LevelGameData;

    // Current player accessing the main menu.
    private Player currentPlayer;

    /*
        CONSTRUCTORS
     */
    public MainMenu()
    {
        // Set the currentPlayer to be unknown.
        currentPlayer = null;

        // Load all players from the file, using implemented loadPlayers() function.
        try
        {
            // Load players from file, so that the players' data is available for operations.
            PlayersGameData = loadPlayers();

            // Load all levels' data from file
            LevelGameData = loadLevels();
        }
        catch (Exception exception)
        {
            System.out.println("Exception inside MainMenu(): Error loading data from file: " + exception);
        }
    }

    /*
        METHODS
     */
    // Removed Function to display stars on terminal.
    public void displayStars()
    {
        // Number of stars to display.
        final int numberOfStars = 20;

        // Display them.
        for (int i = 0; i < numberOfStars; i++)
            System.out.print("*");
        System.out.println();
    }

    // Function to display MainMenu, takes in Scanner as input for re-usability.
    public void displayMenu(Scanner input)
    {
        // // Initialize user choice for the loop.
        int userChoice = 0;

        // Exit loop when the user selects 5 or successful login happens
        while (currentPlayer == null) {
            // Display Stars (Top Border)
            displayStars();

            // Display menu options
            System.out.println("1. Sign in");
            System.out.println("2. Create Account");
            System.out.println("3. Display Users");
            System.out.println("4. Exit");

            // Display Stars (Bottom Border)
            displayStars();

            // Try-Catch block in case of InputMismatch
            try {
                // Prompt user for input
                System.out.print("Enter your choice: ");
                userChoice = input.nextInt();
                input.nextLine(); // Clear buffer
            } catch (InputMismatchException exception) {
                System.out.println("Exception inside displayMenu(): User entered invalid type for input. Exiting program");
                System.exit(1);
            }

            // Handle user choice
            switch (userChoice) {
                // Sign in
                case 1:
                    System.out.println("Sign in selected.");
                    if (!signIn(input))
                        System.out.println("Sign-in failed.");
                    break;
                // Create Account
                case 2:
                    System.out.println("Create Account selected.");
                    // If successful!
                    if (createAccount(input)) {
                        // Load all players from the file, since a new account has been added.
                        try {
                            PlayersGameData = loadPlayers();
                            System.out.println("Account created successfully.");
                        } catch (Exception exception) {
                            System.out.println("Exception inside displayMenu(): " + exception);
                        }
                    } else
                        System.out.println("Account creation failed.");
                    break;
                // Display Users
                case 3:
                    System.out.println("Display Users selected.");
                    displayUsers();
                    break;
                // Exit Program
                case 4:
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
                    break;
            }


            // re-set userChoice
            userChoice = 0;

            // If successful login happens
            while (currentPlayer != null && userChoice != 1) {
                // Display Stars (Top Border)
                displayStars();

                // Display menu options
                System.out.println("1. Start Adventure!");
                System.out.println("2. Delete Account");
                System.out.println("3. Sign Out");

                // Display Stars (Bottom Border)
                displayStars();

                // Try-Catch block in case of InputMismatch
                try {
                    // Prompt user for input
                    System.out.print("Enter your choice: ");
                    userChoice = input.nextInt();
                    input.nextLine(); // Clear buffer
                } catch (InputMismatchException exception) {
                    System.out.println("Exception inside displayMenu(): User entered invalid type for input. Exiting program");
                    System.exit(1);
                }

                // Handle user choice
                switch (userChoice) {
                    // Start Adventure!
                    case 1:
                        System.out.println("Let's Start the Adventure!");
                        displayLevels();
                        break;
                    // Delete Account
                    case 2:
                        System.out.println("Delete Account selected.");
                        if (deleteAccount(input)) {
                            // Load all players from the file, since an account has been deleted.
                            try {
                                PlayersGameData = loadPlayers();
                                System.out.println("Account deleted successfully.");
                            } catch (Exception exception) {
                                System.out.println("Exception inside displayMenu(): " + exception);
                            }
                        } else
                            System.out.println("Account deletion failed.");

                        break;
                    // Exit Program
                    case 3:
                        System.out.println("Sign out selected.");
                        signOut();
                        break;
                    default:
                        System.out.println("Invalid choice! Please select a valid option.");
                        break;
                }
            }

            // To be continued
        }
    }

    // Removed start game on terminal old code
    public void startGame()
    {

    }

    // Removed displayGrid game on terminal old code

    public boolean signIn(Scanner input)
    {
        // If there are no players, return false immediately.
        if(PlayersGameData.isEmpty())
        {
            System.out.println("No players exist in the system.");
            return false;
        }

        // Input username (in variable to be used again)
        System.out.print("Enter username: ");
        String username = input.nextLine();

        // If player doesn't exist, return false
        if(!PlayersGameData.containsKey(username))
        {
            System.out.println("Player not found.");
            return false;
        }

        // Input password (in the complex line lol)
        System.out.print("Enter password: ");

        // If password doesn't match, return false
        if(! PlayersGameData.get(username).getPassword().equals(input.nextLine()))
        {
            System.out.println("Incorrect password.");
            return false;
        }

        // Otherwise, successful login and that the current player
        currentPlayer = PlayersGameData.get(username);
        return true;
    }

    public boolean createAccount(Scanner input)
    {
        // Input username
        System.out.print("Enter username: ");
        String username = input.nextLine();

        // Check if user exists first!
        if(PlayersGameData.containsKey(username))
        {
            System.out.println("Player already exists.");
            return false;
        }

        // Input password
        System.out.print("Enter password: ");
        String password = input.nextLine();

        // Create new player object
        Player newPlayer = new Player(username, password);

        // Add player object to the hashMap containing all data about players.
        PlayersGameData.put(newPlayer.getUsername(), newPlayer);

        // Try-Catch Block
        try
        {
            // Write new player hashMap with the added player to the file.
            writePlayers(PlayersGameData);
        }
        catch (Exception exception)
        {
            System.out.println("Exception inside createAccount(): Error saving account: " + exception);
            return false;
        }

        // Display successful operation.
        return true;
    }

    public void displayUsers()
    {
        if(PlayersGameData.isEmpty())
        {
            System.out.println("No Player Data to display.");
            return;
        }

        // Save data into arrayList and sort it!
        ArrayList<String> sortedUsers = new ArrayList<>(PlayersGameData.keySet());
        Collections.sort(sortedUsers);

        // Display Users
        displayStars();
        System.out.println("Available Users: ");
        for(String username: sortedUsers)
            System.out.println(username);
        displayStars();
    }

    public void signOut()
    {
        if(currentPlayer != null)
            currentPlayer = null;
    }

    public boolean deleteAccount(Scanner input)
    {
        // For-some reason, just check!
        if(currentPlayer == null)
            return false;

        // Input password
        System.out.print("Re-write password for deletion: ");

        // If password doesn't match, return false and sign out player.
        if(!currentPlayer.getPassword().equals(input.nextLine()))
        {
            System.out.println("Incorrect password. Signing out.");
            signOut();
            return false;
        }

        // Remove player from game data
        PlayersGameData.remove(currentPlayer.getUsername());

        // Try-Catch Block
        try
        {
            // Write new player hashMap with the deleted player to the file.
            writePlayers(PlayersGameData);
        }
        catch (Exception exception)
        {
            System.out.println("Exception inside deleteAccount(): Error deleting account: " + exception);
            return false;
        }

        // Sign out, e.g: set currentPlayer = null
        signOut();

        return true;
    }

    public void displayLevels() throws InputMismatchException
    {
        // Exit if the LevelGameData is empty, then something wrong happened.
        if (LevelGameData.isEmpty()) {
            System.out.println("No Level Data to display. Logical Error Occurred. Exiting Program");
            System.exit(1);
        }

        // Save data into arrayList and sort it!
        ArrayList<Integer> sortedLevels = new ArrayList<>(LevelGameData.keySet());
        Collections.sort(sortedLevels);

        // Constant level in the game.
        final int LEVEL_COUNT = 3;

        // Display Levels
        displayStars();
        System.out.println("Levels: ");
        for (int i = 1; i <= LEVEL_COUNT; i++)
            System.out.println(i + ". Level " + i);
        displayStars();

        int userChoice = 0;
        Scanner input = new Scanner(System.in);
        userChoice = input.nextInt();

        startGame();
    }

}