package com.jplayer.player.controller;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jplayer.player.domain.*;
import com.jplayer.player.utils.CommonUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.jplayer.player.utils.CommonUtils.SALT;

/**
 * 计算
 *
 * @author
 * @create 2019-11-25 17:26
 **/
@Slf4j
public class EncryptViewController {

    @FXML
    private Button calBtn;
    @FXML
    private TextField courseDir;

    private String coursePath;


    public void encrypt(){
        this.calMD5(this.coursePath);
    }


    public void initialize() {
        this.coursePath = getCoursePath();
        System.out.println(this.coursePath);
        this.courseDir.setText(this.coursePath);
    }

    private void calMD5(String coursePath){
        List<String> md5ValList = Lists.newArrayList();
        ArrayList<ModuleInfo> moduleInfoList = CommonUtils.getModuleInfo(coursePath);
        for(ModuleInfo moduleInfo : moduleInfoList){
            ArrayList<ThemeInfo> themeInfos = CommonUtils.getThemeInfo(moduleInfo.getModulePath());
            for(ThemeInfo themeInfo : themeInfos){
                for(CourseBaseInfo baseInfo: themeInfo.getCourse()){
                    ArrayList<ChapterInfo> chapterInfos = CommonUtils.getChapterInfo(baseInfo.getCoursePath());
                    for(ChapterInfo chapterInfo: chapterInfos){
                        for(ChapterFile chapterFile : chapterInfo.getChapterFiles()){
                            String bgMd5 = CommonUtils.getMD5(chapterFile.getBgUrl(),SALT);
                            String thumbBgMd5 = CommonUtils.getMD5(chapterFile.getThumbUrl(),SALT);
                            String playMd5 = CommonUtils.getMD5(chapterFile.getPlayUrl(),SALT);
                            if(!Strings.isNullOrEmpty(bgMd5) && !md5ValList.contains(bgMd5)){
                                md5ValList.add(bgMd5);
                            }
                            if(!Strings.isNullOrEmpty(thumbBgMd5) && !md5ValList.contains(thumbBgMd5)){
                                md5ValList.add(thumbBgMd5);
                            }
                            if(!Strings.isNullOrEmpty(playMd5) && !md5ValList.contains(playMd5)){
                                md5ValList.add(playMd5);
                            }
                        }
                    }
                }
            }
        }
        writeMD5(md5ValList);
    }

    private void writeMD5(List<String> md5ValList){
        String str = Joiner.on(",").join(md5ValList);
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
}
