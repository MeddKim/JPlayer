package com.jplayer;

import com.jplayer.player.component.VlcMediaPlayer;
import com.sun.jna.NativeLibrary;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.binding.RuntimeUtil;

/**
 * @author Willard
 * @date 2019/9/4
 */
public class MainApplication extends Application {

    private AnimationTimer timer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VlcMediaPlayer mediaPlayer = new VlcMediaPlayer();
        mediaPlayer.setMaxHeight(500);
        mediaPlayer.setMaxHeight(500);
        Scene scene = new Scene(mediaPlayer,800,800);
        primaryStage.setScene(scene);
        primaryStage.show();

        mediaPlayer.startPlay();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mediaPlayer.renderFrame();
            }
        };

        timer.start();
    }

    public static void main(String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\devFile\\vlc");
        launch(args);
    }
}
