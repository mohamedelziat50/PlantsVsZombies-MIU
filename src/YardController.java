import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class YardController
{
    @FXML
    private ImageView yardImageView; // Background image of the yard
    @FXML
    private HBox plantCardBar; // Top bar for plant cards
    @FXML
    private GridPane yardGrid; // 5x9 grid for plant placement
    @FXML
    private ImageView peashooterCard; // Peashooter card

    private boolean isPlantSelected = false; // Tracks if a plant is selected
    private ImageView draggingPlant; // Plant PNG that follows the cursor
    private String selectedPlantType = null; // Tracks the type of plant selected (e.g., "Peashooter")

    // Handle clicking a plant card
    private void onPlantCardClick(String plantType)
    {
        isPlantSelected = true;
        selectedPlantType = plantType;

        // Show dragging plant PNG
        if (draggingPlant == null)
        {
            draggingPlant = new ImageView();
            draggingPlant.setFitHeight(80);
            draggingPlant.setFitWidth(60);
            yardImageView.getParent().getScene().getRoot().getChildrenUnmodifiable().add(draggingPlant);
        }
        draggingPlant.setImage(new Image("images/" + plantType.toLowerCase() + ".png"));

        // Handle cursor following the plant
        yardImageView.getScene().setOnMouseMoved(this::onMouseMove);
    }

    // Update plant PNG to follow the mouse cursor
    private void onMouseMove(MouseEvent event)
    {
        if (draggingPlant != null) {
            draggingPlant.setLayoutX(event.getSceneX() - draggingPlant.getFitWidth() / 2);
            draggingPlant.setLayoutY(event.getSceneY() - draggingPlant.getFitHeight() / 2);
        }
    }

    // Handle clicking on a grid slot
    private void onGridSlotClick(int row, int col, Button gridButton) {
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
}
