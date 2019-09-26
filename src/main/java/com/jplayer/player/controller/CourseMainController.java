package com.jplayer.player.controller;

import com.jplayer.player.component.simple.SimpleMediaPlayer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Willard
 * @date 2019/9/24
 */
public class CourseMainController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private VBox chapterBox;

    private SimpleMediaPlayer simpleMediaPlayer;

    public void initialize() {
        this.setChapter();
        this.simpleMediaPlayer = SimpleMediaPlayer.newInstance("file:///C:/hk/test.mp4");
        this.simpleMediaPlayer.setPadding(new Insets(200.00,200.00,200.00,200.00));
        this.mainPane.setCenter(simpleMediaPlayer);

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

    }

    private void changeToPic(){
    }

}
