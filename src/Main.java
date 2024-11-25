import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // Create Scanner Object
        Scanner input = new Scanner(System.in);

        MainMenu myMenu = new MainMenu();
        myMenu.displayMenu(input);
    }
}