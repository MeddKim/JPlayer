package com.jplayer.player.controller;

import com.jplayer.player.domain.CourseBaseInfo;
import com.jplayer.player.domain.ThemeInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.jplayer.MainLauncher.screenHeight;
import static com.jplayer.MainLauncher.screenWidth;

/**
 * @author Wyatt
 * @date 2019-09-15
 */
@Slf4j
public class CourseSelectController {

    @FXML
    private HBox themeBox;
    @FXML
    private HBox courseBox;

    private Scene scene;

    private int defaultTheme = 0;

    private int defaultCourse = 0;

    public static CourseMainController courseMainCon;

    public void closeSystem(){
        Platform.exit();
        System.exit(0);
    }

    public void initCourseInfo(String modulePath){
        ArrayList<ThemeInfo> themeInfos = CommonUtils.getThemeInfo(modulePath);
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
            Button button = new Button();
            Image image = new Image(courseBaseInfo.getBgUrl());
            ImageView imageView = new ImageView(image);
            button.setUserData(courseBaseInfo);
            button.setGraphic(imageView);
            button.setOnMouseClicked(event -> {
                Button btn = (Button)event.getSource();
                CourseBaseInfo baseInfo = (CourseBaseInfo) btn.getUserData();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CourseMain.fxml"));
                    Parent root = (Pane) fxmlLoader.load();
                    courseMainCon = fxmlLoader.<CourseMainController>getController();
                    this.scene = new Scene(root);
                    showCourseMainScene(baseInfo.getCoursePath());
                }catch (Exception e){
                    log.info("加载main出错",e);
                }

            });
            this.courseBox.getChildren().add(button);
        }
    }

    private ToggleButton createThemeBtn(ThemeInfo themeInfo){
        ToggleButton button = new ToggleButton();
        button.setText(themeInfo.getThemeName());
        button.setUserData(themeInfo);
        return button;
    }

    public void showCourseMainScene(String coursePath){
        Platform.runLater(()-> {
            Stage stage = (Stage) themeBox.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.setWidth(screenWidth);
            stage.setHeight(screenHeight);
            courseMainCon.initChapterInfo(coursePath);
        });
    }

}
