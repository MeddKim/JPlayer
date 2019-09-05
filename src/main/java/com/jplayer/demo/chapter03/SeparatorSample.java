package com.jplayer.demo.chapter03;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * @author Willard
 * @date 2019/9/5
 */
public class SeparatorSample extends Application {

    Label caption = new Label("Weather Forecast");
    Label friday = new Label("Friday");
    Label saturday = new Label("Saturday");
    Label sunday = new Label("Sunday");

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 350, 150);
        stage.setScene(scene);
        stage.setTitle("Separator Sample");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(2);
        grid.setHgap(5);

        scene.setRoot(grid);
        scene.getStylesheets().add("/demo/chapter03/controlStyle.css");

        Image cloudImage = new Image(
                getClass().getResourceAsStream("/demo/chapter03/cloud.jpg")
        );
        Image sunImage = new Image(
                getClass().getResourceAsStream("/demo/chapter03/sun.jpg")
        );

        caption.setFont(Font.font("Verdana", 20));

        GridPane.setConstraints(caption, 0, 0);
        GridPane.setColumnSpan(caption, 8);
        grid.getChildren().add(caption);

        final Separator sepHor = new Separator();
        sepHor.setValignment(VPos.CENTER);
        GridPane.setConstraints(sepHor, 0, 1);
        GridPane.setColumnSpan(sepHor, 7);
        grid.getChildren().add(sepHor);

        friday.setFont(Font.font("Verdana", 18));
        GridPane.setConstraints(friday, 0, 2);
        GridPane.setColumnSpan(friday, 2);
        grid.getChildren().add(friday);

        final Separator sepVert1 = new Separator();
        sepVert1.setOrientation(Orientation.VERTICAL);
        sepVert1.setValignment(VPos.CENTER);
        sepVert1.setPrefHeight(80);
        GridPane.setConstraints(sepVert1, 2, 2);
        GridPane.setRowSpan(sepVert1, 2);
        grid.getChildren().add(sepVert1);

        saturday.setFont(Font.font("Verdana", 18));
        GridPane.setConstraints(saturday, 3, 2);
        GridPane.setColumnSpan(saturday, 2);
        grid.getChildren().add(saturday);

        final Separator sepVert2 = new Separator();
        sepVert2.setOrientation(Orientation.VERTICAL);
        sepVert2.setValignment(VPos.CENTER);
        sepVert2.setPrefHeight(80);
        GridPane.setConstraints(sepVert2, 5, 2);
        GridPane.setRowSpan(sepVert2, 2);
        grid.getChildren().add(sepVert2);

        sunday.setFont(Font.font("Verdana", 18));
        GridPane.setConstraints(sunday, 6, 2);
        GridPane.setColumnSpan(sunday, 2);
        grid.getChildren().add(sunday);

        final ImageView cloud = new ImageView(cloudImage);
        GridPane.setConstraints(cloud, 0, 3);
        grid.getChildren().add(cloud);

        final Label t1 = new Label("16");
        t1.setFont(Font.font("Verdana", 20));
        GridPane.setConstraints(t1, 1, 3);
        grid.getChildren().add(t1);

        final ImageView sun1 = new ImageView(sunImage);
        GridPane.setConstraints(sun1, 3, 3);
        grid.getChildren().add(sun1);

        final Label t2 = new Label("18");
        t2.setFont(Font.font("Verdana", 20));
        GridPane.setConstraints(t2, 4, 3);
        grid.getChildren().add(t2);

        final ImageView sun2 = new ImageView(sunImage);
        GridPane.setConstraints(sun2, 6, 3);
        grid.getChildren().add(sun2);

        final Label t3 = new Label("20");
        t3.setFont(Font.font("Verdana", 20));
        GridPane.setConstraints(t3, 7, 3);
        grid.getChildren().add(t3);

        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
