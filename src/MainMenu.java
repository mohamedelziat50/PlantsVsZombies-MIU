public class MainMenu
{
    private Player p;

    public MainMenu(Player p) {
        this.p=p;
    }

    public MainMenu() {
    }
    //check if the username already exists else write in the file
    public void createAccount(String username,String password,String name){

    }

    //check if the username exists in the file and if exist check if matches the password
    public boolean login(String username,String password){
        return false;
    }
    //check if the username exists in the file and if exist and matches with the pass remove it from the file and return true
    public boolean delete(String username,String password){
        return false;
    }

}
