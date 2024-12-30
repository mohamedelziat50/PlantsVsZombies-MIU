import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundtrackPlayer
{
    private static MediaPlayer sound_track_player;

    public static void playInGametrack1()
    {
        try
        {
            String path = SoundtrackPlayer.class.getResource("/music/level 1 soundtrack.mp3").toExternalForm();

            // Create a new MediaPlayer for the soundtrack
            Media media = new Media(path);
            sound_track_player = new MediaPlayer(media);

            // Set the background music to loop
            sound_track_player.setCycleCount(MediaPlayer.INDEFINITE);
            sound_track_player.setVolume(0.02);  // Adjust volume if necessary

            // Start playing the soundtrack
            sound_track_player.play();
        } catch (Exception e)
        {
            System.out.println("Error playing soundtrack: " + e.getMessage());
        }
    }

    public static void playInGametrack2()
    {
        try
        {
            String path = SoundtrackPlayer.class.getResource("/music/level 2 soundtrack.mp3").toExternalForm();

            // Create a new MediaPlayer for the soundtrack
            Media media = new Media(path);
            sound_track_player = new MediaPlayer(media);

            // Set the background music to loop
            sound_track_player.setCycleCount(MediaPlayer.INDEFINITE);
            sound_track_player.setVolume(0.02);  // Adjust volume if necessary

            // Start playing the soundtrack
            sound_track_player.play();
        } catch (Exception e)
        {
            System.out.println("Error playing soundtrack: " + e.getMessage());
        }
    }

    public static void playInGametrack3()
    {
        try
        {
            String path = SoundtrackPlayer.class.getResource("/music/level 3 christmas soundtrack.mp3").toExternalForm();

            // Create a new MediaPlayer for the soundtrack
            Media media = new Media(path);
            sound_track_player = new MediaPlayer(media);

            // Set the background music to loop
            sound_track_player.setCycleCount(MediaPlayer.INDEFINITE);
            sound_track_player.setVolume(0.02);  // Adjust volume if necessary

            // Start playing the soundtrack
            sound_track_player.play();
        } catch (Exception e)
        {
            System.out.println("Error playing soundtrack: " + e.getMessage());
        }
    }

    public static void playMainMenutrack()
    {
        try
        {
            String path = SoundtrackPlayer.class.getResource("/music/main menu soundtrack.mp3").toExternalForm();

            // Create a new MediaPlayer for the soundtrack
            Media media = new Media(path);
            sound_track_player = new MediaPlayer(media);

            // Set the background music to loop
            sound_track_player.setCycleCount(MediaPlayer.INDEFINITE);
            sound_track_player.setVolume(0.02);  // Adjust volume if necessary

            // Start playing the soundtrack
            sound_track_player.play();
        } catch (Exception e)
        {
            System.out.println("Error playing soundtrack: " + e.getMessage());
        }
    }

    public static void stopTrack() {
        try {
            if (sound_track_player != null) {
                sound_track_player.stop();  // Stop the current track
                sound_track_player.dispose();  // Release resources
                sound_track_player = null;  // Clear the reference
            }
        } catch (Exception e) {
            System.out.println("Error stopping soundtrack: " + e.getMessage());
        }
    }
}