package factory;

import javax.sound.sampled.*;
import java.io.File;

public class Sound {
    public static void music() {
        File musicPath = new File("res\\sound\\background.wav");
        try{
            new Thread(() -> {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(musicPath);
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }).start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
