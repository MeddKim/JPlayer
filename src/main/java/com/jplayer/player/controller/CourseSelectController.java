package com.jplayer.player.controller;

import com.jplayer.player.domain.CourseBaseInfo;
import com.jplayer.player.domain.ThemeInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Wyatt
 * @date 2019-09-15
 */
public class CourseSelectController {

    @FXML
    private HBox themeBox;

    @FXML
    private HBox courseBox;

    private int defaultTheme = 0;

    private int defaultCourse = 0;

    public void closeSystem(){
        Platform.exit();
        System.exit(0);
    }

    public void initialize() {
        ArrayList<ThemeInfo> themeInfos = CommonUtils.getThemeInfo("C:\\hk\\course\\0.FS未来素养课程");
        initThemeBox(themeInfos);
        addCourseBox(themeInfos.get(defaultTheme));
    }

    /**
     * 填充主题选择Box
     */
    private void initThemeBox(ArrayList<ThemeInfo> themeInfos){
        this.themeBox.getChildren().clear();
        ToggleGroup group = new ToggleGroup();
        for(int i = 0;i < themeInfos.size();i++){
            ThemeInfo themeInfo = themeInfos.get(i);
            ToggleButton button = createThemeBtn(themeInfo);
            button.setToggleGroup(group);
            button.setUserData(themeInfo);
            if(defaultTheme == i){
                button.setSelected(true);
            }
            themeBox.getChildren().add(button);
        }
        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov,
                 Toggle toggle, Toggle new_toggle) -> {
               ThemeInfo themeInfo = (ThemeInfo) group.getSelectedToggle().getUserData();
               addCourseBox(themeInfo);
        });
    }


    /**
     * 填充课程选择box
     * @param themeInfo
     */
    private void addCourseBox(ThemeInfo themeInfo){
        this.courseBox.getChildren().clear();
        List<CourseBaseInfo> courseBaseInfos = themeInfo.getCourse();
        for(CourseBaseInfo courseBaseInfo : courseBaseInfos){
            Image image = new Image(courseBaseInfo.getBgUrl());
            ImageView imageView = new ImageView(image);
            imageView.setUserData(courseBaseInfo);
            this.courseBox.getChildren().add(imageView);
        }
    }

    private ToggleButton createThemeBtn(ThemeInfo themeInfo){
        ToggleButton button = new ToggleButton();
        button.setText(themeInfo.getThemeName());
        button.setUserData(themeInfo);
        return button;
    }



}
