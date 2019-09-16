package com.jplayer.demo.chapter01;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Willard
 * @date 2019/9/16
 */
public class TabPaneApp01 extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tabs");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 250, Color.WHITE);

        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();
        for (int i = 0; i < 5; i++) {
            Tab tab = new Tab();
            tab.setText("Tab" + i);
            HBox hbox = new HBox();
            hbox.getChildren().add(new Label("Tab" + i));
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);

            tab.setClosable(false);

            tabPane.getTabs().add(tab);
        }
//        tabPane.setSide(Side.LEFT);
        tabPane.setSide(Side.TOP);
//        tabPane.setSide(Side.RIGHT);
        //tabPane.setSide(Side.BOTTOM);

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}


