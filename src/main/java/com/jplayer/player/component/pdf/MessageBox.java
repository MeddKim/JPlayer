package com.jplayer.player.component.pdf;

/**
 * @author Wyatt
 * @date 2019-10-03
 */
import java.io.File;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jiu
 */
public class MessageBox {
    private static Stage messageDialog = new Stage();
    private static Text   text  = new Text();
    static{
        messageDialog.initModality(Modality.APPLICATION_MODAL);
        messageDialog.setTitle("提示");
        Group dRoot = new Group();
        Scene dScene = new Scene(dRoot);
        messageDialog.setScene(dScene);
        messageDialog.setWidth(200);

        BorderPane bPane = new BorderPane();

        HBox buttonBox = new HBox();
        buttonBox.setPadding( new Insets(15 , 0 , 10 , 0));
        buttonBox.setAlignment( Pos.CENTER );

        Button nButton = new Button("确定");
        EventHandler noHandler = new EventHandler() {
            @Override
            public void handle(Event e) {
                messageDialog.hide();
            }
        };

        nButton.setOnAction(noHandler);
        buttonBox.getChildren().addAll( nButton );
        messageDialog.setOnCloseRequest(noHandler);

        bPane.setCenter(text);
        bPane.setBottom(buttonBox);
        dRoot.getChildren().add(bPane);
    }

    public static void show( Stage stage , String message ){
        messageDialog.initOwner(stage);
        text.setText(message);
        messageDialog.show();
    }
}
