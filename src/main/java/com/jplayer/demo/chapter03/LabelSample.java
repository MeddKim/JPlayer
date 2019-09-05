package com.jplayer.demo.chapter03;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;



/**
 * @author Willard
 * @date 2019/9/5
 */
public class LabelSample extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Label Sample");
        stage.setWidth(420);
        stage.setHeight(180);

        Image image = new Image(getClass().getResourceAsStream("/demo/chapter03/labels.jpg"));


        Label label1 = new Label("search");
        label1.setGraphic(new ImageView(image));
        label1.setFont(new Font("Arial",30));
        label1.setTextFill(Color.web("#0076a3"));
        /**
         * 可以使用setGraphicTextGap()方法来设置文本图片之间的间距。
         * 另外，可以使用setTextAlignment()方法改变其对齐方向。
         * 还可以通过setContentDisplay(ContentDisplay value)方法定义图形与文本的相对位置，
         *      ContentDisplay常量的可选值为:居左LEFT，居右RIGHT，居中CENTER，居上TOP，居下BOTTOM。
         */
        label1.setTextAlignment(TextAlignment.JUSTIFY);

        Label label2 = new Label("Values");
        label2.setFont(new Font("Cambria",32));
        label2.setRotate(270);
        label2.setTranslateY(50);

        Label label3 = new Label("A label that needs to be wrapped");
        //设置字体折叠
        label3.setWrapText(true);
        label3.setTranslateY(50);
        label3.setPrefWidth(100);

        label3.setOnMouseEntered(e ->{
            label3.setScaleX(1.5);
            label3.setScaleY(1.5);
        });

        label3.setOnMouseExited(e -> {
            label3.setScaleX(1);
            label3.setScaleY(1);
        });

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().add(label1);
        hBox.getChildren().add(label2);
        hBox.getChildren().add(label3);

        Group group = new Group();
        group.getChildren().add(hBox);
        Scene scene = new Scene(group);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
