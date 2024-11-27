import java.util.Map;

public class Yard
{
    // Represents the Dimensions for the Yard for each level which will be constant.
    private final int ROWS = 5, COLUMNS = 9;
}

/*
Notes:
    Manages the state of the gameplay interaction field using a grid .
    Handles placement of plants and zombies during gameplay.

    Use a data structure like a Map<Position, GameObject> to store the current state of the yard,
    where Position is a custom class or a 2D coordinate, and GameObject can represent either plants or zombies.
 */
