package com.jplayer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Wyatt
 * @date 2019-09-15
 */
public class MainLauncher extends Application {
    /**
     * 主舞台
     */
    public static Stage primaryStageObj;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStageObj = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/CourseMain.fxml"));
//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/ModuleSelect.fxml"));
//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/CourseSelect.fxml"));
        Scene mainScene = new Scene(root);
        //最大化窗口
        primaryStage.setMaximized(true);
        //取消所有默认设置（最大最小化，logo image等等）
        primaryStage.initStyle(StageStyle.UNDECORATED);

        //设置任务栏logo
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/plug.png").toString()));
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }
}
