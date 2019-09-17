package com.jplayer.demo.chapter09;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author Willard
 * @date 2019/9/17
 */
public class PDFToHTMLExampleApp extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        File file = new File("C:\\hk\\assetAllocation.pdf");
        System.out.println(file.getParent());
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(file.toURI().toString());

        StackPane root = new StackPane();
        root.getChildren().add(webView);

        Scene scene = new Scene(root, 700, 1000);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}