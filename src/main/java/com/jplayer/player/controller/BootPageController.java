package com.jplayer.player.controller;

import com.jplayer.MainLauncher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.jplayer.MainLauncher.*;

/**
 * @author Wyatt
 * @date 2019-10-07
 */
@Slf4j
public class BootPageController {

    public static ModuleSelectController moduleSelectController;
    public static BootPageSelectController bootPageSelectController;

    private Scene scene;

    @FXML
    private BorderPane mainPane;
    @FXML
    private Label logoLabel;

    public void initialize() {
        String bgPath = " file:" + MainLauncher.bootImgPath + File.separator + MainLauncher.bootImg;
        try {
            URL url = new URL(bgPath);
            Image image = new Image(url.toString());
            BackgroundImage bg = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);
            this.mainPane.setBackground(new Background(bg));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        logoLabel.setText(bootSlogan);
        logoLabel.setPadding(new Insets(0,0,50,0));

    }
    public void changeBootBackground(){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/BootPageSelect.fxml"));
            Parent root = (Pane) fxmlLoader.load();
            bootPageSelectController = fxmlLoader.<BootPageSelectController>getController();
            this.scene = new Scene(root);
            Platform.runLater(()-> {
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setMaximized(true);
                stage.setWidth(screenWidth);
                stage.setHeight(screenHeight);
            });
        }catch (Exception e){
            log.info("跳转错误");
        }
    }
    public void enterMain(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ModuleSelect.fxml"));
            Parent root = (Pane) fxmlLoader.load();
            moduleSelectController = fxmlLoader.<ModuleSelectController>getController();
            this.scene = new Scene(root);
            Platform.runLater(()-> {
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setMaximized(true);
                stage.setWidth(screenWidth);
                stage.setHeight(screenHeight);
                moduleSelectController.initModuleInfo(coursePath);
            });
        }catch (Exception e){
            log.info("跳转错误");
        }
    }

}
