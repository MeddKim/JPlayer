package com.jplayer.player.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jplayer.player.domain.ChapterFile;
import com.jplayer.player.domain.ChapterInfo;
import com.jplayer.player.domain.ModuleInfo;
import com.jplayer.player.domain.ThemeInfo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Wyatt
 * @date 2019-10-27
 */
@Slf4j
public class CalMainLauncher extends Application {
    /**
     * FX应用程序有三层
     *  1. 一个 Stage 主舞台，舞台里上演了一个又一个的场景镜头 scene
     *  2. scene需要一个pane才能看得见
     *  3. pane是我们可以直接操作的地方，我们可以在里面放入不同的组件
     *
     *  所以一般是
     *      panne.add(btn)
     *      scene = new scene(panne)
     *      stage.setPane(pane)
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        Button btn = new Button("Cal MD5");
        pane.setCenter(btn);

        btn.setOnAction(event -> {
           String coursePath =  getCoursePath();
            if(Strings.isNullOrEmpty(coursePath)){
                log.warn("未加载到课程目录，计算结束");
                return;
            }
            calMD5(coursePath);
        });
        Scene scene = new Scene(pane,300,275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calMD5(String coursePath){
        log.info("course path: {}", coursePath);
        List<String> md5ValList = Lists.newArrayList();
        ArrayList<ModuleInfo> moduleInfoList = CommonUtils.getModuleInfo(coursePath);
        for(ModuleInfo moduleInfo : moduleInfoList){
            log.info("module path: {}", moduleInfo.getModulePath());
            ArrayList<ThemeInfo> themeInfos = CommonUtils.getThemeInfo(moduleInfo.getModulePath());
            log.info("module path: {}; theme size: {}", moduleInfo.getModulePath(),themeInfos.size());
            for(ThemeInfo themeInfo : themeInfos){
                log.info("theme path: {}",themeInfo.getThemePath());
                ArrayList<ChapterInfo> chapterInfos = CommonUtils.getChapterInfo(themeInfo.getThemePath(),true);
                for(ChapterInfo chapterInfo: chapterInfos){
                    log.info("chapter path: {}", chapterInfo.getChapterPath());
                    String md5 = CommonUtils.calPathMD5(chapterInfo.getChapterPath());
                    md5ValList.add(md5);
                }
            }
        }
        writeMD5(md5ValList);
    }

    private void writeMD5(List<String> md5ValList){
        String str= Joiner.on(",").join(md5ValList);
        FileWriter writer;
        try {
            String filePath = System.getProperty("user.dir") + File.separator + "validpath.txt";
            writer = new FileWriter(filePath);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCoursePath(){
        BufferedReader bufferedReader = null;
        try {
            String projectPath = System.getProperty("user.dir");
            String configPath = projectPath + File.separator + "config.properties";
            Properties props = new Properties();
            bufferedReader = new BufferedReader(new FileReader(configPath));
            props.load(bufferedReader);
            return props.getProperty("coursePath",System.getProperty("user.dir") + File.separator + "course");
        }catch (Exception e){
            log.error("加载配置文件错误",e);
        }finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                }catch (Exception e){

                }
            }
        }
        return null;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
