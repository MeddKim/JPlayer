package com.jplayer.demo.chapter01;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * @author Willard
 * @date 2019/9/16
 */
public class TabPaneApp03 extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tab 测试");
        BorderPane mainPane = new BorderPane();
        Button extBtn = new Button("退出");
        extBtn.setOnMouseClicked(event -> {
            Platform.exit();
            System.exit(0);
        });
        mainPane.setBottom(extBtn);

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab();
        tab1.setText("测试一");
        BorderPane borderPane1 = new BorderPane();
        Image image1 = new Image(getClass().getResourceAsStream("/demo/chapter03/fw1.jpg"));
        ImageView view1 = new ImageView(image1);
        borderPane1.setCenter(view1);
        tab1.setContent(borderPane1);
        tabPane.getTabs().add(tab1);


        Tab tab2 = new Tab();
        tab2.setText("测试二");
        BorderPane borderPane2 = new BorderPane();
        Image image2 = new Image(getClass().getResourceAsStream("/demo/chapter03/fw2.jpg"));
        ImageView view2 = new ImageView(image2);
        borderPane2.setCenter(view2);
        tab2.setContent(borderPane2);
        tabPane.getTabs().add(tab2);

        Tab tab3 = new Tab();
        tab3.setText("测试三");
        BorderPane borderPane3 = new BorderPane();
        Image image3 = new Image(getClass().getResourceAsStream("/demo/chapter03/fw3.jpg"));
        ImageView view3 = new ImageView(image3);
        borderPane3.setCenter(view3);
        tab3.setContent(borderPane3);
        tabPane.getTabs().add(tab3);

        mainPane.setCenter(tabPane);
        Scene scene = new Scene(mainPane,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
