package com.jplayer.demo.chapter01;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * @author Willard
 * @date 2019/9/4
 */
public class Login extends Application {
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
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
        grid.add(scenetitle,0,0,2,1);

        Label userName = new Label("User Name:");
        grid.add(userName,0,1);

        TextField userTextField = new TextField();
        grid.add(userTextField,1,1);

        Label pw = new Label("Password:");
        grid.add(pw,0,2);

        PasswordField pwFiled = new PasswordField();
        grid.add(pwFiled,1,2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn,1,4);

        Text actionTarget = new Text();
        grid.add(actionTarget,0,6);
        grid.setId("actiontarget");

        btn.setOnAction(event -> {
            actionTarget.setFill(Color.RED);
            actionTarget.setText("Login Fail");
        });

        Scene scene = new Scene(grid,300,275);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Login.class.getResource("/demo/chapter01/login.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
