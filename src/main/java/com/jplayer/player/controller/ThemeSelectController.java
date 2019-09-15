package com.jplayer.player.controller;

import javafx.application.Platform;

/**
 * @author Wyatt
 * @date 2019-09-15
 */
public class ThemeSelectController {

    public void closeSystem(){
        Platform.exit();
        System.exit(0);
    }
}
