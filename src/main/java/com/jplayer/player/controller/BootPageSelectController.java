package com.jplayer.player.controller;

import com.google.common.base.Strings;
import com.jplayer.MainLauncher;
import com.jplayer.player.utils.CommonUtils;
import com.jplayer.player.utils.SafeProperties;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import static com.jplayer.MainLauncher.*;

/**
 * @author Wyatt
 * @date 2019-10-07
 */
@Slf4j
public class BootPageSelectController {
    @FXML
    BorderPane mainPane;
    @FXML
    HBox historyImgBox;
    @FXML
    ImageView currentImg;
    @FXML
    private GridPane containerPane;
    @FXML
    public TextField bootSloganTextFiled;

    public static BootPageController bootPageController;

    private Scene scene;


    private String bootImg;
    private String bootSlogan;

    private double imgWidth = 160;
    private double imgHeight = 90;

    public void initialize() {
        this.bootImg = MainLauncher.bootImg;
        this.bootSlogan = MainLauncher.bootSlogan;

        this.bootSloganTextFiled.setText(this.bootSlogan);
        containerPane.setMaxWidth(800);
        containerPane.setMaxHeight(600);

        containerPane.setMinWidth(800);
        containerPane.setMinHeight(600);

        initHistoryImg();
        setSelectImg("file:" + MainLauncher.bootImgPath + File.separator + MainLauncher.bootImg);
    }
    private void initHistoryImg(){
        try {
            File bgRootDir = new File(MainLauncher.bootImgPath);
            for(File file:bgRootDir.listFiles()){
                if(!file.isDirectory()){
                    URL url = new URL("file:" + file.getAbsolutePath());
                    Image image = new Image(url.toString());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(this.imgWidth);
                    imageView.setFitHeight(this.imgHeight);
                    imageView.setUserData(url.toString());
                    imageView.setOnMouseClicked(event -> {
                        ImageView source = (ImageView)event.getSource();
                        String path = (String)source.getUserData();
                        changeBootImg(path);
                    });
                    this.historyImgBox.getChildren().add(imageView);
                }
            }
        }catch (Exception e){
            log.error("初始化错误",e);
        }
    }

    public void changeBootImg(String path){
        File file = new File(path);
        this.bootImg = file.getName();
        setSelectImg(path);
    }

    private void setSelectImg(String path){
        try {
            URL url = new URL(path);
            Image image = new Image(url.toString());
            this.currentImg.setImage(image);
        }catch (Exception e){
            log.error("更换图片异常",e);
        }
    }

    public void cancel(){
        bootPage();
    }

    public void confirm(){
        if(Strings.isNullOrEmpty(bootSloganTextFiled.getText().trim())){
            return;
        }
        this.bootSlogan = bootSloganTextFiled.getText().trim();
        if(!this.bootImg.equals(MainLauncher.bootImg) || !this.bootSlogan.equals(MainLauncher.bootSlogan)){
            saveNewConfig();
        }
        bootPage();
    }

    void saveNewConfig(){
        try {
            String projectPath = System.getProperty("user.dir");
            String configPath = projectPath + File.separator + "config.properties";
            ///保存属性到b.properties文件
            SafeProperties props = new SafeProperties();
            InputStream fis = new FileInputStream(configPath);
            props.load(fis);
            //一定要在修改值之前关闭fis
            fis.close();
            FileOutputStream oFile = new FileOutputStream(configPath);
            if(!this.bootImg.equals(MainLauncher.bootImg)){
                props.setProperty("bootImg",this.bootImg);
            }
            if(!this.bootSlogan.equals(MainLauncher.bootSlogan)){
                props.setProperty("bootSlogan",this.bootSlogan);
            }
            props.store(oFile,"");
            oFile.close();

            MainLauncher.bootImg = this.bootImg;
            MainLauncher.bootSlogan = this.bootSlogan;
        }catch (Exception e){
            log.error("加载配置文件错误",e);
        }finally {

        }
    }

    public void choseFile(){
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("jpg&png","*.jpg","*.png");

        Stage stage = (Stage)this.mainPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("All file", "*.*")
        );
        fileChooser.setTitle("选择引导图");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){
            String sourceName = selectedFile.getName();
            String suffix = sourceName.substring(sourceName.lastIndexOf("."));
            String name = CommonUtils.generateShortUuid();
            try {
                Files.copy(selectedFile.toPath(),new File(MainLauncher.bootImgPath+ File.separator + name + suffix).toPath());
                this.bootImg = name + suffix;
                this.setSelectImg("file:" + MainLauncher.bootImgPath+ File.separator + name + suffix);
            }catch (Exception e){
                log.error("copy error",e);
            }
        }
    }

    public void bootPage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/BootPage.fxml"));
            Parent root = (Pane) fxmlLoader.load();
            bootPageController = fxmlLoader.<BootPageController>getController();
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
            log.info("跳转错误",e);
        }
    }
}
