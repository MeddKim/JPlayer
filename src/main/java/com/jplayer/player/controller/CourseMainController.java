package com.jplayer.player.controller;

import com.jplayer.player.component.media.ProtoMediaPlayer;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author Willard
 * @date 2019/9/24
 */
public class CourseMainController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private VBox chapterBox;

    private AnimationTimer timer;
    private ProtoMediaPlayer mediaPlayerPane;

    public void initialize() {
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
        MediaPlayer mediaPlayer = new MediaPlayer(new Media("file:///C:/hk/test.mp4"));
        this.mediaPlayerPane = new ProtoMediaPlayer(mediaPlayer);
        this.mainPane.setCenter(mediaPlayerPane);
    }

    private void changeToPic(){
        this.timer.stop();

        BorderPane centerPane1 = new BorderPane();
        Image img1 = new Image(getClass().getResourceAsStream("/images/test1.jpg"));
        ImageView view1 = new ImageView(img1);
        centerPane1.setCenter(view1);
        this.mainPane.setCenter(centerPane1);
    }

}
