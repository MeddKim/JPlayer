package com.jplayer.player.controller;

import com.jplayer.player.domain.ModuleInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/**
 * @author Willard
 * @date 2019/9/26
 */
public class ModuleSelectController {
    @FXML
    private HBox moduleBox;

    public void initialize(){
        ArrayList<ModuleInfo> moduleInfos = CommonUtils.getModuleInfo("E:\\course");
        for (ModuleInfo moduleInfo: moduleInfos){
            ImageView imageView = createModuleBtn(moduleInfo);
            this.moduleBox.getChildren().add(imageView);
        }
    }

    private ImageView createModuleBtn(ModuleInfo moduleInfo){
        Image image = new Image(moduleInfo.getBgUrl());
        ImageView imageView = new ImageView(image);
        imageView.setId(moduleInfo.getBgUrl());
        imageView.setUserData(moduleInfo);
        imageView.setOnMouseClicked(e -> {
            ImageView source = (ImageView)e.getSource();
            ModuleInfo userData = (ModuleInfo)source.getUserData();
            System.out.println("点击模块:" + userData.getModuleName());
        });
        return imageView;
    }

}
