import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class LawnMower extends MainElements
{
    private ImageView lawnMowerImage;
    public LawnMower() {
      lawnMowerImage=new ImageView( new Image("images/plants/LawnMower.png"));

        lawnMowerImage.setFitWidth(65);
        lawnMowerImage.setFitHeight(80);
        lawnMowerImage.setPreserveRatio(true);

    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public void appear(Pane root)
    {

    }
    @Override
    public void disappear()
    {
        System.out.println("The Lawn Mower Disappeared");
    }
    public ImageView getLawnMowerImage(){
        return lawnMowerImage;
    }

}
