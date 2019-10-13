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
public class WaitCallable implements Callable<String> {

    public WaitCallable(){
    }
    @Override
    public String call() throws Exception {
//        while (true){
//            Thread.sleep(5000);
//            log.info("线程{}正在等待：" + Thread.currentThread().getName());
//        }
        Thread.sleep(3000);
         try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ModuleSelect.fxml"));
            Parent root = (Pane) fxmlLoader.load();
             ModuleSelectController controller = fxmlLoader.<ModuleSelectController>getController();
           Scene newScene = new Scene(root);
            Platform.runLater(()-> {

                MainLauncher.primaryStageObj.setScene(newScene);
                MainLauncher.primaryStageObj.setResizable(false);
                MainLauncher.primaryStageObj.setMaximized(true);
                MainLauncher.primaryStageObj.setWidth(MainLauncher.screenWidth);
                MainLauncher.primaryStageObj.setHeight(MainLauncher.screenHeight);
                controller.initModuleInfo(MainLauncher.coursePath);
            });
        }catch (Exception e){
            log.info("跳转错误");
        }
        return "success";
    }
}
