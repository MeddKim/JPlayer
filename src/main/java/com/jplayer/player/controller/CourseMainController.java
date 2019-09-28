package com.jplayer.player.controller;

import com.jplayer.player.component.simple.SimpleMediaPlayer;
import com.jplayer.player.domain.ChapterInfo;
import com.jplayer.player.domain.ThemeInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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

    private int defaultChapter = 0;

    private SimpleMediaPlayer simpleMediaPlayer;

    public void initialize() {
//        this.setChapter();
//        this.simpleMediaPlayer = SimpleMediaPlayer.newInstance("file:///C:/hk/test.mp4");
//        this.simpleMediaPlayer.setPadding(new Insets(200.00,200.00,200.00,200.00));
//        this.mainPane.setCenter(simpleMediaPlayer);
        ArrayList<ChapterInfo> chapterInfos = CommonUtils.getChapterInfo("E:\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)");
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
            }
            chapterBox.getChildren().add(button);
        }
        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov,
                 Toggle toggle, Toggle new_toggle) -> {
                    ChapterInfo chapterInfo = (ChapterInfo) group.getSelectedToggle().getUserData();
                });
    }
    private ToggleButton createChapterBtn(ChapterInfo chapterInfo){
        ToggleButton button = new ToggleButton();
        button.setUserData(chapterInfo);
        button.getStyleClass().add(chapterInfo.getChapterType().getBtnStyle());
        return button;
    }

}
