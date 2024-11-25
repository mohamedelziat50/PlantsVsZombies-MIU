// File related imports.
import java.io.*;

// Data Structures related imports.
import java.util.HashMap;

// Interface that implements default methods for common functionality between classes.
public interface FileOperations
{
    String playersFilePath = "C:\\Users\\MH\\Desktop\\PlantsVsZombies-MIU\\src\\resources\\players.dat";

    // Function to load players from the players.dat file
    default HashMap<String, Player> loadPlayers() throws IOException
    {
        // Wrapper File Object
        File playersFile = new File(playersFilePath);

        // Wrapper ObjectInputStream
        ObjectInputStream input = null;

        // Result HashMap to load players data into.
        HashMap<String, Player> resultHashMap = new HashMap<>();

        // Try-Catch Blocks
        try
        {
            // Check if file doesn't exist.
            if(!playersFile.exists())
            {
                // Create new file
                if (playersFile.createNewFile())
                {
                    // Display message
                    System.out.println(playersFilePath + " has been created, Returning Empty HashMap");
                    // No need to continue, it will be null.
                    return resultHashMap;
                }
                // Failed to create File, then exit program.
                else
                {
                    // Throw IOException to the caller
                    throw new IOException("Exception inside loadPlayers(): Failed to create " + playersFilePath + " .");
                }
            }

            // Create ObjectInputStream (Acts as a wrapper to the FileInputStream)
            input = new ObjectInputStream(new FileInputStream(playersFile));

            // Load data. (Notice the Type Casting is necessary!)
            resultHashMap = (HashMap<String, Player>) input.readObject();
        }
        // Catch Exceptions
        catch (EOFException exception)
        {
            // Handle case where the file is empty
            System.out.println("Exception inside loadPlayers(): File is empty: " + exception + ", returning an empty map.");
        }
        catch (IOException | ClassNotFoundException exception)
        {
            System.out.println("Exception inside loadPlayers(): Error loading player data: " + exception);
        }
        // Close Streams
        finally
        {
            // Close ObjectInputStream
            if(input != null)
                input.close();
        }

        // Return the hashmap loaded with data.
        return resultHashMap;
    }

    // Function to write players to the players.dat file
    default void writePlayers(final HashMap<String, Player> currentPlayers) throws IOException
    {
        // Wrapper File Object
        File playersFile = new File(playersFilePath);

        // Wrapper ObjectOutputStream
        ObjectOutputStream output = null;

        // Try-Catch Blocks
        try
        {
            // Check if file doesn't exist.
            if (!playersFile.exists()) {
                // Create new file
                if (playersFile.createNewFile()) {
                    // Display message
                    System.out.println("Exception inside writePlayers(): " + playersFilePath + " has been created, Writing HashMap for the first time.");
                }
                // Failed to create File, then exit program.
                else {
                    // Throw IOException to the caller
                    throw new IOException("Exception inside writePlayers(): Failed to create " + playersFilePath + " .");
                }
            }

            // Create ObjectOutputStream (Acts as a wrapper to the FileOutputStream)
            output = new ObjectOutputStream(new FileOutputStream(playersFile));

            // Write data.
            output.writeObject(currentPlayers);
        }
        // Catch Exceptions
        catch (IOException exception)
        {
            System.out.println("Exception inside writePlayers(): Error writing player data: " + exception);
        }
        // Close Streams
        finally
        {
            // Close ObjectInputStream
            if(output != null)
                output.close();
        }
    }
}
