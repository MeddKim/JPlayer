package com.jplayer.player.utils;

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

    public static ArrayList<ModuleInfo> getModuleInfo(String path){
        File root = new File(path);
        ArrayList<ModuleInfo> moduleInfos = new ArrayList<>();
        for(String dir : root.list()){
            String[] names = dir.split(ID_NAME_SPLIT);
            if(names.length == 2){
                ModuleInfo moduleInfo = new ModuleInfo();
                moduleInfo.setModuleId(names[0]);
                moduleInfo.setModuleName(names[1]);
                moduleInfo.setModulePath(path + File.separator + dir);
                moduleInfo.setBgUrl("file:" + path + File.separator + dir + File.separator + "bg.png");
                moduleInfos.add(moduleInfo);
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
                    ThemeInfo themeInfo = new ThemeInfo();
                    themeInfo.setThemeId(names[0]);
                    themeInfo.setThemeName(names[1]);
                    themeInfo.setThemePath(themeDir.getAbsolutePath());

                    //获取课程信息

                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        getThemeInfo("C:\\hk\\course\\0.FS未来素养课程");
    }
}
