import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundtrackPlayer
{
    public void playSoundtrack()
    {
        try
        {
            // Load the soundtrack from the resources folder
            String path = getClass().getResource("/music/background music.mp3").toExternalForm();

            // Play the soundtrack
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);// Loop playback
            mediaPlayer.setVolume(0.02);
            mediaPlayer.play();


        } catch (Exception e)
        {
            System.out.println("Error playing soundtrack: " + e.getMessage());
        }
    }
}