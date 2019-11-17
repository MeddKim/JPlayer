package com.jplayer.player.controller;

import com.google.common.collect.Lists;
import com.jplayer.MainLauncher;
import com.jplayer.player.component.imgslider.ImageEventListener;
import com.jplayer.player.component.imgslider.ImageSlider;
import com.jplayer.player.component.imgslider.SliderEvent;
import com.jplayer.player.domain.CourseBaseInfo;
import com.jplayer.player.domain.ThemeInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.jplayer.MainLauncher.*;

/**
 * @author Wyatt
 * @date 2019-09-15
 */
@Slf4j
public class CourseSelectController implements ImageEventListener {

    @FXML
    private HBox themeBox;

    @FXML
    private BorderPane mainPane;
    private Scene scene;

    private int defaultTheme = 0;

    private int defaultCourse = 0;

    private double sliderWidth = 800;

    private ImageSlider imageSlider;

    public static CourseMainController courseMainCon;

    private String currentPath;

    public void closeSystem(){
        Platform.exit();
        System.exit(0);
    }

    public void returnModuleSelect() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ModuleSelect.fxml"));
        Parent root = (Pane) fxmlLoader.load();
        ModuleSelectController controller = fxmlLoader.<ModuleSelectController>getController();
        Scene newScene = new Scene(root);
        Platform.runLater(()-> {

            MainLauncher.primaryStageObj.setScene(newScene);
            MainLauncher.primaryStageObj.setResizable(false);
            MainLauncher.primaryStageObj.setMaximized(true);
            MainLauncher.primaryStageObj.setWidth(MainLauncher.globalAppWidth);
            MainLauncher.primaryStageObj.setHeight(MainLauncher.globalAppHeight);
            controller.initModuleInfo(MainLauncher.coursePath);
        });
    }

    public void initCourseInfo(String modulePath){
        this.currentPath = modulePath;
        calLayout();
        ArrayList<ThemeInfo> themeInfos = CommonUtils.getThemeInfo(modulePath);
        initThemeBox(themeInfos);
        setCourseImageSlider(themeInfos.get(defaultTheme));
    }
    void calLayout(){
        this.sliderWidth = MainLauncher.globalAppWidth;
    }
    /**
     * 填充主题选择Box
     */
    private void initThemeBox(ArrayList<ThemeInfo> themeInfos){
        this.themeBox.getChildren().clear();
        this.mainPane.setCenter(null);
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
               setCourseImageSlider(themeInfo);
        });
    }


    /**
     * 填充课程选择box
     * @param themeInfo
     */
    private void setCourseImageSlider(ThemeInfo themeInfo){
        List<CourseBaseInfo> courseBaseInfos = themeInfo.getCourse();
        List<SliderEvent> imageDatas = Lists.newArrayList();
        for(CourseBaseInfo courseBaseInfo : courseBaseInfos){
            SliderEvent sliderEvent = new SliderEvent();
            sliderEvent.setImagePath(courseBaseInfo.getBgUrl());
            sliderEvent.setData(courseBaseInfo);
            imageDatas.add(sliderEvent);
        }
        this.imageSlider = new ImageSlider(this.sliderWidth,true);
        BorderPane.setAlignment(this.imageSlider, Pos.CENTER);
        BorderPane.setMargin(this.imageSlider,new Insets(-150,0,0,0));
        this.imageSlider.addListener(this);
        this.imageSlider.initImages(imageDatas);
        this.mainPane.setCenter(this.imageSlider);

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
            stage.setWidth(globalAppWidth);
            stage.setHeight(globalAppHeight);
            courseMainCon.initChapterInfo(coursePath,this.currentPath);
        });
    }

    @Override
    public void handleEvent(SliderEvent event) {
                CourseBaseInfo baseInfo = (CourseBaseInfo) event.getData();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CourseMain.fxml"));
                    Parent root = (Pane) fxmlLoader.load();
                    courseMainCon = fxmlLoader.<CourseMainController>getController();
                    this.scene = new Scene(root);
                    showCourseMainScene(baseInfo.getCoursePath());
                }catch (Exception e){
                    log.info("加载main出错",e);
                }
    }
}
