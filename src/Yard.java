import java.util.Map;

public class Yard
{
    /*
        MEMBER VARIABLES
     */
    // Private class called Position.
    private class ObjectPosition { public int x, y;}

    // Map to manage state of game as a "grid"
    Map<ObjectPosition, Plant> plantGrid; // Position will be constant untill it dies
    Map<ObjectPosition, Zombie> zombieGrid; // Position will always be updated.
}

/*
Notes:
    Manages the state of the gameplay interaction field using a grid .
    Handles placement of plants and zombies during gameplay.

    Use a data structure like a Map<Position, GameObject> to store the current state of the yard,
    where Position is a custom class or a 2D coordinate, and GameObject can represent either plants or zombies.
 */
