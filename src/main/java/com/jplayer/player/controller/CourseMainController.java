package com.jplayer.player.controller;

import com.jplayer.player.component.EventListener;
import com.jplayer.player.component.VlcMediaPlayer;
import com.sun.jna.NativeLibrary;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;

import java.nio.ByteBuffer;

/**
 * @author Willard
 * @date 2019/9/24
 */
public class CourseMainController implements EventListener {
    @FXML
    private BorderPane mainPane;
    private VlcMediaPlayer mediaPlayer;
    private AnimationTimer timer;

    @FXML
    private VBox chapterBox;

    public void initialize() {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\devFile\\vlc");

        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mediaPlayer.renderFrame();
            }
        };

        BorderPane centerPane = new BorderPane();
        Image img1 = new Image(getClass().getResourceAsStream("/images/test.jpg"));
        ImageView view1 = new ImageView(img1);
        centerPane.setCenter(view1);
        this.mainPane.setCenter(centerPane);
        setChapter();
    }
    void setChapter(){
        Button btn1 = new Button();

        btn1.setId("1");
        btn1.setText("视频");
        btn1.setOnMouseClicked(e->{
            changeToVedio();
        });

        Button btn2 = new Button();
        btn2.setId("2");
        btn2.setText("图片");
        btn2.setOnMouseClicked(e->{
            changeToPic();
        });


        Button btn3 = new Button();
        btn3.setId("1");
        btn3.setText("教材");

        Button btn4 = new Button();
        btn4.setId("2");
        btn4.setText("音频");

        chapterBox.getChildren().add(btn1);

        chapterBox.getChildren().add(btn2);

        chapterBox.getChildren().add(btn3);

        chapterBox.getChildren().add(btn4);
    }

    private void changeToVedio(){
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\devFile\\vlc");

        mediaPlayer = new VlcMediaPlayer(this);

        this.mainPane.setCenter(mediaPlayer);
        this.mediaPlayer.startPlay();
        this.timer.start();
    }

    private void changeToPic(){
        this.mediaPlayer.stopPlay();
        this.timer.stop();

        BorderPane centerPane1 = new BorderPane();
        Image img1 = new Image(getClass().getResourceAsStream("/images/test1.jpg"));
        ImageView view1 = new ImageView(img1);
        centerPane1.setCenter(view1);
        this.mainPane.setCenter(centerPane1);
    }

    @Override
    public void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat) {

    }

    @Override
    public void bufferFormat(int sourceWidth, int sourceHeight) {

    }
}
