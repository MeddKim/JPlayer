package com.jplayer.player.utils;

import com.jplayer.player.domain.*;
import com.jplayer.player.enums.ChapterBtnEnum;
import com.jplayer.player.enums.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Willard
 * @date 2019/9/26
 */
public class CommonUtils {

    /**
     * 编号名称分隔符
     */
    public static final String ID_NAME_SPLIT = "\\.";

    public static final String BG_PREX = "file:";
    public static final String VIDEO_PREX = "file:/";
    public static final String BG_NAME = "bg.png";

    public static ArrayList<ModuleInfo> getModuleInfo(String path){
        File root = new File(path);
        ArrayList<ModuleInfo> moduleInfos = new ArrayList<>();
        for(File dir : root.listFiles()){
            if(dir.isDirectory()){
                String[] names = dir.getName().split(ID_NAME_SPLIT);
                if(names.length == 2){
                    ModuleInfo moduleInfo = new ModuleInfo();
                    moduleInfo.setModuleId(names[0]);
                    moduleInfo.setModuleName(names[1]);
                    moduleInfo.setModulePath(dir.getAbsolutePath());
                    moduleInfo.setBgUrl(BG_PREX + dir.getAbsolutePath() + File.separator + BG_NAME);
                    moduleInfos.add(moduleInfo);
                }
            }
        }
        return moduleInfos;
    }

    public static ArrayList<ThemeInfo> getThemeInfo(String path){
        File root = new File(path);
        ArrayList<ThemeInfo> themeInfos = new ArrayList<>();
        for (File themeDir : root.listFiles()){
            if(themeDir.isDirectory()){
                String[] names = themeDir.getName().split(ID_NAME_SPLIT);
                if(names.length == 2){
                    ArrayList<CourseBaseInfo> courseBaseInfos = new ArrayList<>();
                    ThemeInfo themeInfo = new ThemeInfo();
                    themeInfo.setThemeId(names[0]);
                    themeInfo.setThemeName(names[1]);
                    themeInfo.setThemePath(themeDir.getAbsolutePath());
                    //获取课程信息
                    for(File courseDir : themeDir.listFiles()){
                        if(courseDir.isDirectory()){
                            String[] courseNames = courseDir.getName().split(ID_NAME_SPLIT);
                            if(courseNames.length == 2){
                                CourseBaseInfo courseBaseInfo = new CourseBaseInfo();
                                courseBaseInfo.setBgUrl( BG_PREX + courseDir.getAbsolutePath() + File.separator +BG_NAME);
                                courseBaseInfo.setCourseId(courseNames[0]);
                                courseBaseInfo.setCourseName(courseNames[1]);
                                courseBaseInfo.setCoursePath(courseDir.getAbsolutePath());
                                courseBaseInfos.add(courseBaseInfo);
                            }
                        }
                    }
                    themeInfo.setCourse(courseBaseInfos);
                    themeInfos.add(themeInfo);
                }
            }
        }
        return themeInfos;
    }


    public static ArrayList<ChapterInfo> getChapterInfo(String path){
        ArrayList<ChapterInfo> chapterInfos = new ArrayList<>();
        File root = new File(path);
        for(File chapterDir: root.listFiles()){
            if(chapterDir.isDirectory()){
                String[] chapterNames = chapterDir.getName().split(ID_NAME_SPLIT);
                if(chapterNames.length == 2){
                    ChapterInfo chapterInfo = new ChapterInfo();
                    chapterInfo.setChapterId(chapterNames[0]);
                    chapterInfo.setChapterName(chapterNames[1]);
                    chapterInfo.setChapterPath(chapterDir.getAbsolutePath());
                    chapterInfo.setChapterType(ChapterBtnEnum.getTypeFromDir(chapterNames[1]));
                    List<ChapterFile> chapterFiles = new ArrayList<>();
                    for(File fileDir : chapterDir.listFiles()){
                        if(fileDir.isDirectory()){
                            String[] chapterFileNames = fileDir.getName().split(ID_NAME_SPLIT);
                            if(chapterFileNames.length == 2){
                                ChapterFile chapterFile = new ChapterFile();
                                chapterFile.setFileId(chapterFileNames[0]);
                                chapterFile.setFileName(chapterFileNames[1]);
                                if(chapterFileNames[1].contains("视频")){
                                    chapterFile.setType(FileType.VEDIO);
                                    chapterFile.setPlayUrl(VIDEO_PREX + fileDir.getAbsolutePath() + File.separator + "play.mp4");
                                    chapterFile.setThumbUrl(BG_PREX + fileDir.getAbsolutePath() + File.separator +"thumb.png");
                                }else {
                                    chapterFile.setType(FileType.IMG);
                                    chapterFile.setPlayUrl(BG_PREX + fileDir.getAbsolutePath() + File.separator + "voice_play_bg.jpg");
                                    chapterFile.setThumbUrl(BG_PREX + fileDir.getAbsolutePath() + File.separator + "voice_img.jpg");
                                }
                                chapterFiles.add(chapterFile);
                            }
                        }
                    }
                    chapterInfo.setChapterFiles(chapterFiles);
                    chapterInfos.add(chapterInfo);
                }
            }
        }
        return chapterInfos;
    }


    public static void main(String[] args) {
        ArrayList<ChapterInfo> chapterInfos = getChapterInfo("E:\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)");
        System.out.println(chapterInfos.size());
    }
}
