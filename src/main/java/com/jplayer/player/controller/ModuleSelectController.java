package com.jplayer.player.controller;

import com.jplayer.player.domain.ModuleInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static com.jplayer.MainLauncher.*;

/**
 * @author Willard
 * @date 2019/9/26
 */
@Slf4j
public class ModuleSelectController {
    @FXML
    private HBox moduleBox;

    private Scene scene;
    public static CourseSelectController con;

    public void initialize(){

    }

    public void initModuleInfo(String path){
        ArrayList<ModuleInfo> moduleInfos = CommonUtils.getModuleInfo(path);
        for (ModuleInfo moduleInfo: moduleInfos){
            Button imageView = createModuleBtn(moduleInfo);
            this.moduleBox.getChildren().add(imageView);
        }
    }

    private Button createModuleBtn(ModuleInfo moduleInfo){
        Button button =  new Button();
        Image image = new Image(moduleInfo.getBgUrl());
        ImageView imageView = new ImageView(image);
        imageView.setId(moduleInfo.getBgUrl());
        button.setGraphic(imageView);
        button.setUserData(moduleInfo);
        button.setOnMouseClicked(e->{
            Button btn = (Button) e.getSource();
            ModuleInfo userData = (ModuleInfo)btn.getUserData();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CourseSelect.fxml"));
                Parent root = (Pane) fxmlLoader.load();
                con = fxmlLoader.<CourseSelectController>getController();
                this.scene = new Scene(root);
                showScene(userData.getModulePath());
            }catch (Exception ex){
                log.error("打开课程选择窗口错误",ex);
            }
        });
        return button;
    }

    public void showScene(String modulePath){
        Platform.runLater(()-> {
            Stage stage = (Stage) moduleBox.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setMaximized(true);
            stage.setWidth(globalAppWidth);
            stage.setHeight(globalAppHeight);
            con.initCourseInfo(modulePath);
        });
    }

}
