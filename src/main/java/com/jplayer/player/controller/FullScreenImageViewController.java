package com.jplayer.player.controller;

import com.jplayer.MainLauncher;
import com.jplayer.player.component.media.MusicPlayer;
import com.jplayer.player.component.media.ProtoMediaPlayer;
import com.jplayer.player.domain.ChapterFile;
import com.jplayer.player.domain.ChapterInfo;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author Willard
 * @date 2019/11/16
 */
@Slf4j
public class FullScreenImageViewController{


    @FXML
    private BorderPane mainContainer;
    @FXML
    private Button switchPre;
    @FXML
    private Button switchNext;

    @FXML
    private VBox leftBox;
    @FXML
    private VBox rightBox;


    private MusicPlayer musicPlayer;
    private ProtoMediaPlayer mediaPlayer;


    private ChapterInfo chapterInfo;
    private ChapterFile currentFile;

    private int currentIndex;

    public void initialize(ChapterInfo chapterInfo,ChapterFile chapterFile) {
        this.chapterInfo = chapterInfo;
        this.currentFile = chapterFile;
        calCurrentIndex(this.chapterInfo,this.currentFile);
        setContainer(chapterFile);

        this.switchNext.setOnMouseClicked(e->{
            switchContainer(1);
        });
        this.switchPre.setOnMouseClicked(e->{
            switchContainer(-1);
        });

        setBoxHover();
    }
    private void calCurrentIndex(ChapterInfo chapterInfo,ChapterFile chapterFile){
        for(int i = 0 ;i < chapterInfo.getChapterFiles().size();i++){
            ChapterFile file = this.chapterInfo.getChapterFiles().get(i);
            if(file.getFileId().trim().equals(chapterFile.getFileId().trim())){
                this.currentIndex = i;
            }
        }
    }

    private void setContainer(ChapterFile chapterFile){
        this.destroy();
        if(chapterFile == null){
            return;
        }
        switch (chapterFile.getType()){
            case IMG:
                setImageViewToContainer(chapterFile);
                break;
            case VEDIO:
                setVideoToContainer(chapterFile);
                break;
            default:
                this.destroy();
                log.info("文件类型{}不匹配",chapterFile.getType());
        }
    }

    private void setImageViewToContainer(ChapterFile chapterFile){
        this.musicPlayer = new MusicPlayer(MainLauncher.screenWidth,MainLauncher.screenHeight);
        musicPlayer.start(chapterFile.getPlayUrl(),chapterFile.getBgUrl(),true);
        this.mainContainer.setCenter(this.musicPlayer);
        BorderPane.setAlignment(this.musicPlayer,Pos.BOTTOM_CENTER);
    }


    private void setVideoToContainer(ChapterFile chapterFile){
        try {
            URL url = new URL(chapterFile.getPlayUrl());
            this.mediaPlayer = new ProtoMediaPlayer(MainLauncher.screenWidth,MainLauncher.screenHeight);
            this.mediaPlayer.start(url.toString(),false);
            this.mainContainer.setCenter(this.mediaPlayer);
            BorderPane.setAlignment(this.mediaPlayer,Pos.BOTTOM_CENTER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param flag -1 是前一个，1是下一个
     */
    public void switchContainer(int flag){
        if(-1 == flag && 0 == this.currentIndex){
            return;
        }
        if(1 == flag && this.currentIndex == this.chapterInfo.getChapterFiles().size()){
            return;
        }
        this.currentIndex = this.currentIndex + flag;
        this.currentFile = this.chapterInfo.getChapterFiles().get(this.currentIndex);
        setContainer(this.currentFile);
    }

    public void destroy(){
        if(this.musicPlayer != null){
            this.musicPlayer.destroy();
        }
        if(this.mediaPlayer != null){
            this.mediaPlayer.destroy();
        }
        this.mainContainer.setCenter(null);
    }

    private void setBoxHover(){
        this.leftBox.setOnMouseEntered(e->{
            this.switchPre.getStyleClass().clear();
            this.switchPre.getStyleClass().add("switch-pre");
        });
        this.leftBox.setOnMouseExited(e->{
            this.switchNext.getStyleClass().clear();
            this.switchPre.getStyleClass().add("switch-pre-hover");
        });

        this.rightBox.setOnMouseEntered(e->{
            this.switchNext.getStyleClass().clear();
            this.switchNext.getStyleClass().add("switch-next");
        });
        this.rightBox.setOnMouseExited(e->{
            this.switchNext.getStyleClass().clear();
            this.switchNext.getStyleClass().add("switch-next-hover");
        });
    }
}
