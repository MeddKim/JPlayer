package com.jplayer.player.controller;

import com.jplayer.player.component.media.ProtoMediaPlayer;
import com.jplayer.player.domain.ChapterFile;
import com.jplayer.player.domain.ChapterInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
    private BorderPane containerPane;

    private Scene scene;

    private StackPane stackPane;

    private int defaultChapter = 0;

    private ProtoMediaPlayer mediaPlayer;

    public void initialize() {
        DropShadow dropshadow = new DropShadow();// 阴影向外
        dropshadow.setRadius(10);// 颜色蔓延的距离
        dropshadow.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
        dropshadow.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
        dropshadow.setSpread(0.1);// 颜色变淡的程度
        dropshadow.setColor(Color.BLACK);// 设置颜色
        this.containerPane.setEffect(dropshadow);
//        this.mainPane.setEffect(dropshadow);
        this.containerPane.setBackground(Background.EMPTY);
    }

    private void calLayout(){
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
        this.containerPane.setCenter(null);
        for(ChapterFile chapterFile: chapterInfo.getChapterFiles()){
            Image image = new Image(chapterFile.getThumbUrl());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(100);
            imageView.setUserData(chapterFile);
            imageView.setOnMouseClicked(e -> {
                setContainer(chapterFile);
            });
//            this.fileThumbBox.getChildren().add(imageView);
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
        if(this.mediaPlayer != null){
            this.mediaPlayer.destroy();
        }
        this.containerPane.setCenter(null);
        Image image = new Image(chapterFile.getPlayUrl());
        ImageView imageView = new ImageView(image);
        this.containerPane.setCenter(imageView);
    }


    private void setVideoToContainer(ChapterFile chapterFile){
        if(this.mediaPlayer != null){
            this.mediaPlayer.destroy();
        }
        this.containerPane.setCenter(null);
        try {
            URL url = new URL(chapterFile.getPlayUrl());
            this.mediaPlayer = new ProtoMediaPlayer(960,540);
            this.mediaPlayer.start(url.toString(),false);
            this.containerPane.setCenter(this.mediaPlayer);
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
