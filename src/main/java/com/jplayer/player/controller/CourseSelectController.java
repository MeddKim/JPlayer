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
public class CourseSelectController {

    @FXML
    private HBox themeBox;

    @FXML
    private HBox courseBox;

    public void closeSystem(){
        Platform.exit();
        System.exit(0);
    }

    public void initialize() {

    }



}
