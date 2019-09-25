package com.jplayer.player.component;

import com.jplayer.player.component.EventListener;
import com.jplayer.player.component.VlcMediaPlayer;
import com.sun.jna.NativeLibrary;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;

import java.nio.ByteBuffer;

/**
 * @author Willard
 * @date 2019/9/4
 */
public class MainApplication extends Application implements EventListener {

    private AnimationTimer timer;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        VlcMediaPlayer mediaPlayer = new VlcMediaPlayer(this);
        Scene scene = new Scene(mediaPlayer,800,600);
        stage.setScene(scene);
        stage.show();
//        stage.setMaximized(true);

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

    @Override
    public void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat) {
        System.out.println("display...........");
    }

    @Override
    public void bufferFormat(int sourceWidth, int sourceHeight) {
//        this.stage.setHeight(sourceHeight);
//        this.stage.setWidth(sourceWidth);
    }
}
