package com.jplayer;

import com.jplayer.player.controller.ModuleSelectController;
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

    public static ModuleSelectController moduleSelectCon;


    @Override
    public void start(Stage primaryStage) throws Exception {
        initScreenInfo();
        primaryStageObj = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ModuleSelect.fxml"));
        Parent root = (Pane) fxmlLoader.load();
        Scene mainScene = new Scene(root);

        //最大化窗口
        primaryStage.setMaximized(true);
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
    }


    public void initModuleInfo(){
        String projectPath = System.getProperty("user.dir");
        log.info("project path : {}",projectPath);
        String coursePath = projectPath + File.separator + "course";
        log.info("course path: {}",coursePath);
        moduleSelectCon.initModuleInfo(coursePath);
    }
}
