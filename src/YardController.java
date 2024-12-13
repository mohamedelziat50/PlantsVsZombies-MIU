import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.sql.SQLOutput;

public class YardController
{
    @FXML
    private ImageView yardImageView;
    @FXML
    private GridPane yardGrid;
    @FXML
    private ImageView peashooterCard; // Peashooter card

    private boolean isPlantSelected = false;
    private ImageView draggingPlant; // Plant PNG that follows the cursor
    private String selectedPlantType = null; // Tracks the type of plant selected (e.g., "Peashooter")


    // Update plant PNG to follow the mouse cursor
    private void onMouseMove(MouseEvent event)
    {
        if (draggingPlant != null) {
            draggingPlant.setLayoutX(event.getSceneX() - draggingPlant.getFitWidth() / 2);
            draggingPlant.setLayoutY(event.getSceneY() - draggingPlant.getFitHeight() / 2);
        }
    }

    // Handle clicking on a grid slot
    private void onGridSlotClick(int row, int col, Button gridButton)
    {
        if (isPlantSelected && selectedPlantType != null) {
            System.out.println("Planting " + selectedPlantType + " at (" + row + ", " + col + ")");

            // Place the plant PNG in the clicked slot
            ImageView plantImageView = new ImageView(new Image("images/" + selectedPlantType.toLowerCase() + ".png"));
            plantImageView.setFitHeight(80);
            plantImageView.setFitWidth(60);
            yardGrid.add(plantImageView, col, row);

            // Disable the button to prevent re-planting
            gridButton.setDisable(true);

            // Reset selection
            isPlantSelected = false;
            selectedPlantType = null;

            // Remove dragging plant PNG
            yardImageView.getScene().setOnMouseMoved(null);
            yardImageView.getParent().getScene().getRoot().getChildrenUnmodifiable().remove(draggingPlant);
            draggingPlant = null;
        }
    }

    public void onPlantCardClick(MouseEvent mouseEvent)
    {
        System.out.println("ehhhhhh");
        isPlantSelected = true;

        if (draggingPlant == null)
        {
            draggingPlant = new ImageView();
            draggingPlant.setFitHeight(60);
            draggingPlant.setFitWidth(60);
            draggingPlant.setImage(new Image("images/plant.gif"));

            // Add the draggingPlant to a parent node
            if (yardImageView.getParent() instanceof AnchorPane pane)
            {
                pane.getChildren().add(draggingPlant);
            } else {
                System.out.println("Parent node is not a Pane. Cannot add draggingPlant.");
                return;
            }
        }

        yardImageView.getScene().setOnMouseMoved(this::onMouseMove);
    }

}
