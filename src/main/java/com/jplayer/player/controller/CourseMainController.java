package com.jplayer.player.controller;

import com.jplayer.player.component.VlcMediaPlayer1;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Willard
 * @date 2019/9/24
 */
public class CourseMainController {
    @FXML
    private VBox chapterBox;
    @FXML
    private BorderPane vlcMediaPlayer1;

    public void initialize() {
        setChapter();
    }
    void setChapter(){
        Button btn1 = new Button();
        btn1.setId("1");
        btn1.setText("视频");

        Button btn2 = new Button();
        btn2.setId("2");
        btn2.setText("图片");

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

}
