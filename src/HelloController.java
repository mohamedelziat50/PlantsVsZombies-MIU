import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HelloController
{
    @FXML
    void buttonClick(MouseEvent event)
    {
        System.out.println("Hello World!");
    }

}
