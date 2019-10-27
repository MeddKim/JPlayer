package com.jplayer.player.controller;

import com.jplayer.MainLauncher;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author Willard
 * @date 2019/10/8
 */
@Slf4j
public class WaitCallable implements Runnable {

    public WaitCallable(){
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
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
        }catch (Exception e){
            log.info("跳转错误");
        }
    }
}
