package com.jplayer.demo.chapter03;

/**
 * @author Willard
 * @date 2019/9/5
 */
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class TooltipSample extends Application {

    final static String[] rooms = new String[]{
            "Accommodation (BB)",
            "Half Board",
            "Late Check-out",
            "Extra Bed"
    };
    final static Integer[] rates = new Integer[]{
            100, 20, 10, 30
    };
    final CheckBox[] cbs = new CheckBox[rooms.length];
    final Label total = new Label("Total: $0");
    Integer sum = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Tooltip Sample");
        stage.setWidth(330);
        stage.setHeight(150);

        total.setFont(new Font("Arial", 20));

        for (int i = 0; i < rooms.length; i++) {
            final CheckBox cb = cbs[i] = new CheckBox(rooms[i]);
            final Integer rate = rates[i];
            final Tooltip tooltip = new Tooltip("$" + rates[i].toString());
            tooltip.setFont(new Font("Arial", 16));
            cb.setTooltip(tooltip);
            cb.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val,
                     Boolean new_val) -> {
                        if (cb.isSelected()) {
                            sum = sum + rate;
                        } else {
                            sum = sum - rate;
                        }
                        total.setText("Total: $" + sum.toString());
                    }
            );
        }

        VBox vbox = new VBox();
        vbox.getChildren().addAll(cbs);
        vbox.setSpacing(5);
        HBox root = new HBox();
        root.getChildren().add(vbox);
        root.getChildren().add(total);
        root.setSpacing(40);
        root.setPadding(new Insets(20, 10, 10, 20));

        ((Group) scene.getRoot()).getChildren().add(root);

        stage.setScene(scene);
        stage.show();
    }
}
