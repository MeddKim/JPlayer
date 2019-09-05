package com.jplayer.demo.chapter01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Willard
 * @date 2019/9/5
 */
public class FXMLExample extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/demo/chapter01/fxml_example.fxml"));

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(new Scene(root,300,275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(FXMLExample.class,args);
    }
}
