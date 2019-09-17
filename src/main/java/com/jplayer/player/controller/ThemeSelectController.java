package com.jplayer.player.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;


/**
 * @author Wyatt
 * @date 2019-09-15
 */
public class ThemeSelectController{

    @FXML
    private HBox moduleBox;
    @FXML
    private HBox themeBox;

    public void closeSystem(){
        Platform.exit();
        System.exit(0);
    }

    public void initialize() {
        setModules();
    }

    private void setModules(){

        final EventHandler<MouseEvent> myHandler = new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                Button x = (Button) event.getSource();
                Integer flag = Integer.parseInt(x.getId());
                setTheme(flag);
            }
        };

        Button btn1 = new Button();
        btn1.setId("1");
        btn1.setText("金毛狮王");

        Button btn2 = new Button();
        btn2.setId("2");
        btn2.setText("白眉鹰王");

        Button btn3 = new Button();
        btn3.setId("1");
        btn3.setText("青翼蝠王");

        Button btn4 = new Button();
        btn4.setId("2");
        btn4.setText("紫衫龙王");


        btn1.setOnMouseClicked(myHandler);
        btn2.setOnMouseClicked(myHandler);
        btn3.setOnMouseClicked(myHandler);
        btn4.setOnMouseClicked(myHandler);

        moduleBox.getChildren().add(btn1);
        moduleBox.getChildren().add(btn2);
        moduleBox.getChildren().add(btn3);
        moduleBox.getChildren().add(btn4);
    }

    private void setTheme(int i){
        themeBox.getChildren().clear();
        int one = i;
        int two = i + 2;
        int three = i + 4;
        Image img1 = new Image(getClass().getResourceAsStream("/demo/chapter03/fw"+ one +".jpg"));
        ImageView view1 = new ImageView(img1);
        view1.setOpacity(0.5);

        Image img2 = new Image(getClass().getResourceAsStream("/demo/chapter03/fw"+ two +".jpg"));
        ImageView view2 = new ImageView(img2);
        view2.setOpacity(0.5);

        Image img3 = new Image(getClass().getResourceAsStream("/demo/chapter03/fw"+ three +".jpg"));
        ImageView view3 = new ImageView(img3);
        view3.setOpacity(0.5);

        themeBox.getChildren().add(view1);
        themeBox.getChildren().add(view2);
        themeBox.getChildren().add(view3);


        view1.setOnMouseEntered(event -> {
            ImageView view = (ImageView)event.getSource();
            view.setOpacity(1.0);
        });

        view1.setOnMouseExited(event -> {
            ImageView view = (ImageView)event.getSource();
            view.setOpacity(0.5);
        });

    }

    public static void main(String[] args) throws IOException {
        System.out.println(new File("").getCanonicalPath());
    }

}
