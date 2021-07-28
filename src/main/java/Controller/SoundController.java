package main.java.Controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;

public class SoundController {
    private MediaPlayer mediaPlayer;

    public SoundController(String firstMusic) {
        Media sound = new Media(new File(firstMusic).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setStartTime(new Duration(0));
        mediaPlayer.setAutoPlay(true);
    }

    public void playSound(String music) {
        mediaPlayer.stop();
        Media sound = new Media(new File(music).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setStartTime(new Duration(0));
        mediaPlayer.setAutoPlay(true);
    }

    public void stop() {
        mediaPlayer.stop();
    }

}
