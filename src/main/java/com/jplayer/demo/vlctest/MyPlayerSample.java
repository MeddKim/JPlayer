package com.jplayer.demo.vlctest;

import com.sun.jna.NativeLibrary;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.nio.ByteBuffer;

/**
 * @author Willard
 * @date 2019/9/6
 */
public class MyPlayerSample extends Application {

    /**
     * Pixel writer to update the canvas.
     */
    private PixelWriter pixelWriter;

    /**
     * Pixel format.
     */
    private  WritablePixelFormat<ByteBuffer> pixelFormat;

    /**
     *
     */
    private  MediaPlayerFactory mediaPlayerFactory;

    /**
     * The vlcj direct rendering media player component.
     */
    private  EmbeddedMediaPlayer mediaPlayer;


    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas();
        canvas.setHeight(300);
        canvas.setWidth(300);

        pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        pixelFormat = PixelFormat.getByteBgraInstance();

        mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();


        BorderPane pane = new BorderPane();
        pane.setCenter(canvas);

        Scene scene = new Scene(pane,800,800);
        stage.setScene(scene);
        stage.show();

        mediaPlayer.controls().setRepeat(true);
        String VIDEO_FILE = "C:\\devFile\\操作系统原理02.wmv";
        mediaPlayer.media().play(VIDEO_FILE);
    }

    public static void main(String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "D:/dev/lib/vlc/x64");
        launch(args);
    }
}
