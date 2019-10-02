package com.jplayer;

import com.jplayer.player.controller.ModuleSelectController;
import com.jplayer.player.enums.ScreenScaleEnum;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author Wyatt
 * @date 2019-09-15
 */
@Slf4j
public class MainLauncher extends Application {
    /**
     * 主舞台
     */
    public static Stage primaryStageObj;
    public static double screenHeight;
    public static double screenWidth;


    /**
     * 应用设置 宽，高，和是否全屏
     * 采用 1366 * 768  1920 * 1080    1600 * 900
     */
    public static double globalAppWidth;
    public static double globalAppHeight;
    public static Boolean isMaximized = Boolean.FALSE;



    public static ModuleSelectController moduleSelectCon;


    @Override
    public void start(Stage primaryStage) throws Exception {
        initScreenInfo();
        primaryStageObj = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ModuleSelect.fxml"));
        Parent root = (Pane) fxmlLoader.load();
        Scene mainScene;
        if(isMaximized){
            //最大化窗口
            primaryStage.setMaximized(true);
            mainScene = new Scene(root);
        }else{
            mainScene = new Scene(root,globalAppWidth,globalAppHeight);
        }

        //取消所有默认设置（最大最小化，logo image等等）
        primaryStage.initStyle(StageStyle.UNDECORATED);
        //设置任务栏logo
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/plug.png").toString()));
        primaryStage.setScene(mainScene);
        primaryStage.show();


        moduleSelectCon = fxmlLoader.<ModuleSelectController>getController();
        initModuleInfo();

        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public void initScreenInfo(){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        screenHeight = primaryScreenBounds.getHeight();
        screenWidth = primaryScreenBounds.getWidth();
        ScreenScaleEnum screenScaleEnum = ScreenScaleEnum.getAppScale(screenWidth);
        globalAppWidth = screenScaleEnum.getAppWidth();
        globalAppHeight = screenScaleEnum.getAppHeight();
        isMaximized = screenScaleEnum.getFullScreen();

        log.info("应用宽应为{}，高应为：{}，是否展示全屏：{}",globalAppWidth,globalAppHeight,isMaximized);
    }


    public void initModuleInfo(){
        String projectPath = System.getProperty("user.dir");
        log.info("project path : {}",projectPath);
        String coursePath = projectPath + File.separator + "course";
        log.info("course path: {}",coursePath);
        moduleSelectCon.initModuleInfo(coursePath);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
