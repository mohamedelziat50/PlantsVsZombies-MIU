import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class TorchWood extends Plant {

    // Static list to keep track of active TorchWood instances
    public static final List<TorchWood> activeTorchWoods = new ArrayList<>();

    public TorchWood() {
        super(175, 15, 50);
        // Add this instance to the activeTorchWoods list
        activeTorchWoods.add(this);
    }

    public TorchWood(int x, int y) {
        this();
        super.x = x;
        super.y = y; // Assuming Plant has x, y, and health

        // Initialize the TorchWood image
        elementImage = new ImageView(new Image("images/plants/torchWood.gif"));
        elementImage.setFitWidth(90);
        elementImage.setFitHeight(85);
        elementImage.setPreserveRatio(true);

        // Set the position for the TorchWood plant
        elementImage.setLayoutX(x - elementImage.getFitWidth() / 2);
        elementImage.setLayoutY(y - elementImage.getFitHeight() / 2);
    }

    @Override
    public void takeDamage(int damage)
    {
        // Implement take damage logic if necessary
    }

    @Override
    public void run()
    {
        // No specific thread logic for TorchWood in this implementation
    }

    @Override
    public void action()
    {
        // Define any action when needed
    }

}
