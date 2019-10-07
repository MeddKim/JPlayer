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

import java.io.*;
import java.util.Properties;

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


    public static String coursePath;
    public static String bootImgPath;
    public static String bootImg;
    public static String bootSlogan;

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        initScreenInfo();
//        primaryStageObj = primaryStage;
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ModuleSelect.fxml"));
//        Parent root = (Pane) fxmlLoader.load();
//        Scene mainScene;
//        if(isMaximized){
//            //最大化窗口
//            primaryStage.setMaximized(true);
//            mainScene = new Scene(root);
//        }else{
//            mainScene = new Scene(root,globalAppWidth,globalAppHeight);
//        }
//
//        //取消所有默认设置（最大最小化，logo image等等）
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        //设置任务栏logo
//        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/plug.png").toString()));
//        primaryStage.setScene(mainScene);
//        primaryStage.show();
//
//
//        moduleSelectCon = fxmlLoader.<ModuleSelectController>getController();
//
//
//        initModuleInfo();
//
//        primaryStage.setOnCloseRequest(e -> Platform.exit());
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initScreenInfo();
        primaryStageObj = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/BootPage.fxml"));
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



    public static void main(String[] args) {
        loadConfig();
        launch(args);
    }

    public static void loadConfig(){
        BufferedReader bufferedReader = null;
        try {
            String projectPath = System.getProperty("user.dir");
            String configPath = projectPath + File.separator + "config.properties";
            Properties props = new Properties();
            bufferedReader = new BufferedReader(new FileReader(configPath));
            props.load(bufferedReader);
            coursePath = props.getProperty("coursePath",System.getProperty("user.dir") + File.separator + "course");
            bootImgPath = props.getProperty("bootImgPath",System.getProperty("user.dir") + File.separator + "bg");
            bootImg = props.getProperty("bootImg","1");
            bootSlogan = props.getProperty("bootSlogan","柱石计划跆拳道馆");
        }catch (Exception e){
            log.error("加载配置文件错误",e);
        }finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                }catch (Exception e){

                }
            }
        }

    }
}
