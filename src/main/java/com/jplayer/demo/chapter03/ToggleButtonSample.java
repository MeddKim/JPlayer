package com.jplayer.demo.chapter03;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * @author Willard
 * @date 2019/9/5
 */
public class ToggleButtonSample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Toggle Button Sample");
        stage.setWidth(250);
        stage.setHeight(180);

        HBox hbox = new HBox();
        VBox vbox = new VBox();

        Scene scene = new Scene(new Group(vbox));
        stage.setScene(scene);
        scene.getStylesheets().add("/demo/chapter03/controlStyle.css");

        Rectangle rect = new Rectangle();
        rect.setHeight(50);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.DARKGRAY);
        rect.setStrokeWidth(2);
        rect.setArcHeight(10);
        rect.setArcWidth(10);

        final ToggleGroup group = new ToggleGroup();

        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov,
                 Toggle toggle, Toggle new_toggle) -> {
                    if (new_toggle == null) {
                        rect.setFill(Color.WHITE);
                    }else {
                        rect.setFill((Color) group.getSelectedToggle().getUserData());
                    }
                });

        ToggleButton tb1 = new ToggleButton("Minor");
        tb1.setToggleGroup(group);
        tb1.setUserData(Color.LIGHTGREEN);
        tb1.setSelected(true);
        tb1.getStyleClass().add("toggle-button1");

        ToggleButton tb2 = new ToggleButton("Major");
        tb2.setToggleGroup(group);
        tb2.setUserData(Color.LIGHTBLUE);
        tb2.getStyleClass().add("toggle-button2");

        ToggleButton tb3 = new ToggleButton("Critical");
        tb3.setToggleGroup(group);
        tb3.setUserData(Color.SALMON);
        tb3.getStyleClass().add("toggle-button3");



        hbox.getChildren().addAll(tb1, tb2, tb3);

        vbox.getChildren().add(new Label("Priority:"));
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(rect);
        vbox.setPadding(new Insets(20, 10, 10, 20));


        stage.show();
        rect.setWidth(hbox.getWidth());
    }
}
