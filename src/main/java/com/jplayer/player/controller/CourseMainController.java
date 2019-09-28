package com.jplayer.player.controller;

import com.jplayer.player.component.simple.SimpleMediaPlayer;
import com.jplayer.player.domain.ChapterFile;
import com.jplayer.player.domain.ChapterInfo;
import com.jplayer.player.domain.ThemeInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Willard
 * @date 2019/9/24
 */
public class CourseMainController {
    @FXML
    private BorderPane mainPane;
    @FXML
    private VBox chapterBox;
    @FXML
    private HBox fileThumbBox;
    @FXML
    private HBox containerBox;

    private Scene scene;

    private int defaultChapter = 0;

    private SimpleMediaPlayer simpleMediaPlayer;

    public void initialize() {

    }

    public void initChapterInfo(String coursePath){
        ArrayList<ChapterInfo> chapterInfos = CommonUtils.getChapterInfo(coursePath);
        initChapterBtn(chapterInfos);
    }

    void initChapterBtn(ArrayList<ChapterInfo> chapterInfos){
        this.chapterBox.getChildren().clear();
        ToggleGroup group = new ToggleGroup();
        for(int i = 0;i < chapterInfos.size();i++){
            ChapterInfo chapterInfo = chapterInfos.get(i);
            ToggleButton button = createChapterBtn(chapterInfo);
            button.setToggleGroup(group);
            button.setUserData(chapterInfo);
            if(defaultChapter == i){
                button.setSelected(true);
                setThumbBox(chapterInfo);
            }
            chapterBox.getChildren().add(button);
        }
        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov,
                 Toggle toggle, Toggle new_toggle) -> {
                    ChapterInfo chapterInfo = (ChapterInfo) group.getSelectedToggle().getUserData();
                    setThumbBox(chapterInfo);
                });

        setContainer(chapterInfos.get(0).getChapterFiles().get(0));
    }

    private void setThumbBox(ChapterInfo chapterInfo){
        this.fileThumbBox.getChildren().clear();
        this.containerBox.getChildren().clear();
        for(ChapterFile chapterFile: chapterInfo.getChapterFiles()){
            Image image = new Image(chapterFile.getThumbUrl());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(100);
            imageView.setUserData(chapterFile);
            imageView.setOnMouseClicked(e -> {
                setContainer(chapterFile);
            });
            this.fileThumbBox.getChildren().add(imageView);
        }
    }



    private void setContainer(ChapterFile chapterFile){
        switch (chapterFile.getType()){
            case IMG:
                setImageViewToContainer(chapterFile);
                break;
            case VEDIO:
                setVideoToContainer(chapterFile);
                break;
        }
    }

    private void setImageViewToContainer(ChapterFile chapterFile){
        if(this.simpleMediaPlayer != null){
            this.simpleMediaPlayer.stop();
        }
        this.containerBox.getChildren().clear();
        Image image = new Image(chapterFile.getPlayUrl());
        ImageView imageView = new ImageView(image);
        this.containerBox.getChildren().add(imageView);
    }


    private void setVideoToContainer(ChapterFile chapterFile){
        if(this.simpleMediaPlayer != null){
            this.simpleMediaPlayer.stop();
        }
        this.containerBox.getChildren().clear();
        try {
            URL url = new URL(chapterFile.getPlayUrl());
            this.simpleMediaPlayer = SimpleMediaPlayer.newInstance(url.toString(),1000,800);
            this.containerBox.getChildren().add(this.simpleMediaPlayer);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }



    private ToggleButton createChapterBtn(ChapterInfo chapterInfo){
        ToggleButton button = new ToggleButton();
        button.setUserData(chapterInfo);
        button.getStyleClass().add(chapterInfo.getChapterType().getBtnStyle());
        return button;
    }

}
