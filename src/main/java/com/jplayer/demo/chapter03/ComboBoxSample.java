package com.jplayer.demo.chapter03;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 * @author Willard
 * @date 2019/9/5
 */
public class ComboBoxSample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    final Button button = new Button ("Send");
    final Label notification = new Label ();
    final TextField subject = new TextField("");
    final TextArea text = new TextArea ("");


    String address = " ";

    @Override public void start(Stage stage) {
        stage.setTitle("ComboBoxSample");
        Scene scene = new Scene(new Group(), 500, 270);

        final ComboBox emailComboBox = new ComboBox();
        emailComboBox.getItems().addAll(
                "jacob.smith@example.com",
                "isabella.johnson@example.com",
                "ethan.williams@example.com",
                "emma.jones@example.com",
                "michael.brown@example.com"
        );

        emailComboBox.setPromptText("Email address");
        emailComboBox.setEditable(true);
        emailComboBox.setOnAction((Event ev) -> {
            address =
                    emailComboBox.getSelectionModel().getSelectedItem().toString();
        });


        final ComboBox priorityComboBox = new ComboBox();
        priorityComboBox.getItems().addAll(
                "Highest",
                "High",
                "Normal",
                "Low",
                "Lowest"
        );
        priorityComboBox.setValue("Normal");
        priorityComboBox.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override public ListCell<String> call(ListView<String> param) {
                        final ListCell<String> cell = new ListCell<String>() {
                            {
                                super.setPrefWidth(100);
                            }
                            @Override public void updateItem(String item,
                                                             boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item);
                                    if (item.contains("High")) {
                                        setTextFill(Color.RED);
                                    }
                                    else if (item.contains("Low")){
                                        setTextFill(Color.GREEN);
                                    }
                                    else {
                                        setTextFill(Color.BLACK);
                                    }
                                }
                                else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }

                });


        button.setOnAction((ActionEvent e) -> {
            if (emailComboBox.getValue() != null &&
                    !emailComboBox.getValue().toString().isEmpty()){
                notification.setText("Your message was successfully sent"
                        + " to " + address);
                emailComboBox.setValue(null);
                if (priorityComboBox.getValue() != null &&
                        !priorityComboBox.getValue().toString().isEmpty()){
                    priorityComboBox.setValue(null);
                }
                subject.clear();
                text.clear();
            }
            else {
                notification.setText("You have not selected a recipient!");
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("To: "), 0, 0);
        grid.add(emailComboBox, 1, 0);
        grid.add(new Label("Priority: "), 2, 0);
        grid.add(priorityComboBox, 3, 0);
        grid.add(new Label("Subject: "), 0, 1);
        grid.add(subject, 1, 1, 3, 1);
        grid.add(text, 0, 2, 4, 1);
        grid.add(button, 0, 3);
        grid.add (notification, 1, 3, 3, 1);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();

    }
}