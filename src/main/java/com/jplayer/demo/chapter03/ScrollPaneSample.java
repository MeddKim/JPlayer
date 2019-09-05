package com.jplayer.demo.chapter03;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * @author Willard
 * @date 2019/9/5
 */
public class ScrollPaneSample extends Application {

    final ScrollPane sp = new ScrollPane();
    final Image[] images = new Image[5];
    final ImageView[] pics = new ImageView[5];
    final VBox vb = new VBox();
    final Label fileName = new Label();
    final String [] imageNames = new String [] {"fw1.jpg", "fw2.jpg",
            "fw3.jpg", "fw4.jpg", "fw5.jpg"};

    @Override
    public void start(Stage stage) {
        VBox box = new VBox();
        Scene scene = new Scene(box, 180, 180);
        stage.setScene(scene);
        stage.setTitle("ScrollPaneSample");
        box.getChildren().addAll(sp, fileName);
        VBox.setVgrow(sp, Priority.ALWAYS);

        fileName.setLayoutX(30);
        fileName.setLayoutY(160);

        Image roses = new Image(getClass().getResourceAsStream("/demo/chapter03/roses.jpg"));
        sp.setContent(new ImageView(roses));
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);



        for (int i = 0; i < 5; i++) {
            images[i] = new Image(getClass().getResourceAsStream("/demo/chapter03/" + imageNames[i]));
            pics[i] = new ImageView(images[i]);
            pics[i].setFitWidth(100);
            pics[i].setPreserveRatio(true);
            vb.getChildren().add(pics[i]);
        }

        sp.setVmax(440);
        sp.setPrefSize(115, 150);
        sp.setContent(vb);
        sp.vvalueProperty().addListener((ObservableValue<? extends Number> ov,
                                         Number old_val, Number new_val) -> {
            fileName.setText(imageNames[(new_val.intValue() - 1)/100]);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
