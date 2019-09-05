package com.jplayer.demo.chapter03;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author Willard
 * @date 2019/9/5
 */
public class TextFieldSample extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setTitle("Text Field Sample");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        scene.setRoot(grid);

        final TextField name = new TextField();
        name.setPromptText("Enter your first name.");
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);

        final TextField lastName = new TextField();
        lastName.setPromptText("Enter your last name.");
        GridPane.setConstraints(lastName, 0, 1);
        grid.getChildren().add(lastName);

        final TextField comment = new TextField();
        comment.setPromptText("Enter your comment.");
        GridPane.setConstraints(comment, 0, 2);
        grid.getChildren().add(comment);

        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);

        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 1);
        grid.getChildren().add(clear);

        final Label label = new Label();
        GridPane.setConstraints(label, 0, 3);
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);

        submit.setOnAction((ActionEvent e) -> {
            if (
                    (comment.getText() != null && !comment.getText().isEmpty())
                    ) {
                label.setText(name.getText() + " " +
                        lastName.getText() + ", "
                        + "thank you for your comment!");
            } else {
                label.setText("You have not left a comment.");
            }
        });

        clear.setOnAction((ActionEvent e) -> {
            name.clear();
            lastName.clear();
            comment.clear();
            label.setText(null);
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
