package com.jplayer.demo.chapter01;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Wyatt
 * @date 2019-09-14
 */
public class Demo extends Application {
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
        Image image  = new Image(getClass().getResourceAsStream(
                "/demo/chapter03/cappuccino.jpg")
        );
        ImageView imageView = new ImageView(image);
        BorderPane pane = new BorderPane();
        Slider slider = new Slider(1,11,1);
        pane.setCenter(imageView);
        pane.setBottom(slider);
        Scene scene = new Scene(pane,300,275);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Login.class.getResource("/demo/chapter01/login.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
