import javax.sound.sampled.*;
import java.io.IOException;

public class SoundPlayer {

    public static void playSound(String soundFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                SoundPlayer.class.getResource(soundFile)
            );
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error : " + soundFile);
            e.printStackTrace();
        }
    }
}