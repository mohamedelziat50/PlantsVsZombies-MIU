// File related imports.
import java.io.*;

// Data Structures related imports.
import java.util.ArrayList;
import java.util.HashMap;

// Interface that implements default methods for common functionality between classes.
public interface FileOperations
{
    String playersFilePath = "players.dat";
    String levelsFilePath = "levels.dat";

    // Function that checks if a file exists, and creates one if it doesn't exist.
    default boolean checkFileExistence(File theFile, final String path) throws IOException
    {
        // Check if file doesn't exist.
        if(!theFile.exists())
        {
            // Create new file
            if (theFile.createNewFile())
            {
                // Display message
                System.out.println(path + " has been created.");

                // False indicating it did not exist
                return false;
            }
            // Failed to create File, then exit program.
            else
            {
                // Throw IOException to the caller
                throw new IOException("Exception: Failed to create " + path + " .");
            }
        }

        // Otherwise, it does exist.
        return true;
    }

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
            if(!checkFileExistence(playersFile, playersFilePath))
            {
                // Display message
                System.out.println("Returning Empty Players HashMap");

                // No need to continue, it will be null.
                return resultHashMap;
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
            if(!checkFileExistence(playersFile, playersFilePath))
            {
                // Display message
                System.out.println("Writing HashMap for the first time.");
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

    // Function to load the levels from the levels.dat file
    default HashMap<Integer, Level> loadLevels() throws IOException
    {
        // Wrapper File Object
        File levelsFile = new File(levelsFilePath);

        // Wrapper ObjectInputStream
        ObjectInputStream input = null;

        // Result HashMap to load levels data inside.
        HashMap<Integer, Level> resultHashMap = new HashMap<>();

        // Try-Catch Blocks
        try
        {
            // Check if file doesn't exist or is empty.
            if (!checkFileExistence(levelsFile, levelsFilePath) || levelsFile.length() == 0)
            {
                System.out.println(levelsFilePath + " is missing or empty. Writing default levels!");
                writeConstantLevels();
            }

            // Create ObjectInputStream (Acts as a wrapper to the FileInputStream)
            input = new ObjectInputStream(new FileInputStream(levelsFile));

            // Load data. (Notice the Type Casting is necessary!)
            resultHashMap = (HashMap<Integer, Level>) input.readObject();
        }
        // Catch Exceptions
        catch (EOFException exception)
        {
            // Handle case where the file is empty
            System.out.println("Exception inside loadLevels(): File is empty: " + exception + ", returning an empty map.");
        }
        catch (IOException | ClassNotFoundException exception)
        {
            System.out.println("Exception inside loadLevels(): Error loading levels' data: " + exception);
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

    // Function to write CONSTANT levels to the levels.dat file (In-case any corruption happens, and to keep things consistent)
    default void writeConstantLevels() throws IOException
    {
        // Wrapper File Object
        File levelsFile = new File(levelsFilePath);

        // Wrapper ObjectOutputStream
        ObjectOutputStream output = null;

        // Try-Catch Blocks
        try
        {
            // Check if file doesn't exist.
            if(! checkFileExistence(levelsFile, levelsFilePath))
            {
                // Display message
                System.out.println("writeConstantLevels() has been called.");
            }

            // Create ObjectOutputStream (Acts as a wrapper to the FileOutputStream)
            output = new ObjectOutputStream(new FileOutputStream(levelsFile));

            /*
                LATER, LOADING DATA OF PLANTS AND ZOMBIES WILL BE CONSTANT FROM A FILE, AND NOT HARDCODED.
                FOR NOW, USE DEFAULT CONSTRUCTOR WITH HARDCODED DATA.

                FOR NOW PHASE-1 ONLY A SINGLE LEVEL WITH A PEASHOOTER AND A DEFAULT ZOMBIE WILL BE DISPLAYED.
             */

            // ArrayList for unlocked plants & Zombies
            ArrayList<Plant> level1UnlockedPlants = new ArrayList<>();
            level1UnlockedPlants.add(new Peashooter());

            ArrayList<Zombie> level1UnlockedZombies = new ArrayList<>();
            level1UnlockedZombies.add(new DefaultZombie());

            // Create Levels manually.
            Level level1 = new Level (
                    1,              // Level Number
                    60,                        // Duration in seconds
                    level1UnlockedPlants,      // Unlocked plants
                    level1UnlockedZombies     // Unlocked zombies
                    // Yard configuration is null
            );

            // Then create hashmap to store all levels for faster access!
            HashMap<Integer, Level> constantLevelsHashMap = new HashMap<>();
            constantLevelsHashMap.put(level1.getLevelNumber(), level1);

            // Write the HashMap containing levels..
            output.writeObject(constantLevelsHashMap);

            // Display message for tracing
            System.out.println("Levels wrote successfully.");
        }
        // Catch Exceptions
        catch (IOException exception)
        {
            System.out.println("Exception inside writeConstantLevels(): Error writing levels data: " + exception);
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
