package com.jplayer.demo.chapter09;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
/**
 * @author Willard
 * @date 2019/9/5
 */
public class EmbeddedMediaPlayer extends Application {

    private static final String MEDIA_URL =
            "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Embedded Media Player");
        Group root = new Group();
        Scene scene = new Scene(root, 540, 241);

        // create media player
        Media media = new Media (MEDIA_URL);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        MediaControl mediaControl = new MediaControl(mediaPlayer);
        scene.setRoot(mediaControl);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
