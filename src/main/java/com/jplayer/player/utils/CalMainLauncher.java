package com.jplayer.player.utils;

import com.jplayer.MainTest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wyatt
 * @date 2019-10-27
 */
@Slf4j
public class CalMainLauncher extends Application {
    /**
     * FX应用程序有三层
     *  1. 一个 Stage 主舞台，舞台里上演了一个又一个的场景镜头 scene
     *  2. scene需要一个pane才能看得见
     *  3. pane是我们可以直接操作的地方，我们可以在里面放入不同的组件
     *
     *  所以一般是
     *      panne.add(btn)
     *      scene = new scene(panne)
     *      stage.setPane(pane)
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(MainTest.class.getResource("/views/EncryptView.fxml"));
        Parent root = (Pane) fxmlLoader.load();

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(MainTest.class.getClassLoader().getResource("images/plug.gif").toString()));
        Scene scene = new Scene(root,600,200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
