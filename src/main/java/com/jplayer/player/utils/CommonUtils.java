package com.jplayer.player.utils;

import com.jplayer.player.domain.CourseBaseInfo;
import com.jplayer.player.domain.ModuleInfo;
import com.jplayer.player.domain.ThemeInfo;

import java.io.File;
import java.util.ArrayList;

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




    public static void main(String[] args) {
        ArrayList<ThemeInfo> themeInfos = getThemeInfo("C:\\hk\\course\\0.FS未来素养课程");
        System.out.println(themeInfos.size());
    }
}
