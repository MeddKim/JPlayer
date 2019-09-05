package com.jplayer.demo.chapter03;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author Willard
 * @date 2019/9/5
 */
public class ButtonSample extends Application {

    private static final Color color = Color.web("#464646");
    Button button3 = new Button("Decline");
    DropShadow shadow = new DropShadow();
    Label label = new Label();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());

        stage.setTitle("Button Sample");
        stage.setWidth(300);
        stage.setHeight(190);
        scene.getStylesheets().add("/demo/chapter03/controlStyle.css");

        label.setFont(Font.font("Times New Roman", 22));
        label.setTextFill(color);

        Image imageDecline = new Image(getClass().getResourceAsStream("/demo/chapter03/not.png"));
        Image imageAccept = new Image(getClass().getResourceAsStream("/demo/chapter03/ok.png"));

        //HBox和VBox是用于布局托管，HBox是水平，VBox垂直，组件放入Box之后，原有的位置设置将失效
        VBox vbox = new VBox();
        vbox.setLayoutX(20);
        vbox.setLayoutY(20);
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();

        Button button1 = new Button("Accept", new ImageView(imageAccept));
        button1.getStyleClass().add("button1");
        button1.setOnAction((ActionEvent e) -> {
            label.setText("Accepted");
        });


        Button button2 = new Button("Accept");
        button2.setOnAction((ActionEvent e) -> {
            label.setText("Accepted");
        });

        button3.setOnAction((ActionEvent e) -> {
            label.setText("Declined");
        });

        button3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            button3.setEffect(shadow);
        });

        button3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            button3.setEffect(null);
        });



        hbox1.getChildren().add(button2);
        hbox1.getChildren().add(button3);
        hbox1.getChildren().add(label);
        hbox1.setSpacing(10);
        hbox1.setAlignment(Pos.BOTTOM_CENTER);

        Button button4 = new Button();
        button4.setGraphic(new ImageView(imageAccept));
        button4.setOnAction((ActionEvent e) -> {
            label.setText("Accepted");
        });


        Button button5 = new Button();
        button5.setGraphic(new ImageView(imageDecline));
        button5.setOnAction((ActionEvent e) -> {
            label.setText("Declined");
        });

        hbox2.getChildren().add(button4);
        hbox2.getChildren().add(button5);
        hbox2.setSpacing(25);

        vbox.getChildren().add(button1);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox2);
        vbox.setSpacing(10);
        ((Group)scene.getRoot()).getChildren().add(vbox);

        stage.setScene(scene);
        stage.show();
    }
}