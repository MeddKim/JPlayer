package com.jplayer.player.controller;

import com.jplayer.MainLauncher;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.jplayer.MainLauncher.bootSlogan;

/**
 * @author Wyatt
 * @date 2019-10-07
 */
public class BootPageController {

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
    }
}
