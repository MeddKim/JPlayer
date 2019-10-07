package com.jplayer.player.enums;

/**
 * @author Willard
 * @date 2019/9/27
 */
public enum ChapterBtnEnum {

    /**
     * 导入
     */
    IMPORT("导入","import-btn"),
    /**
     * 教学
     */
    TEACH("教学","teach-btn"),
    /**
     *启发
     */
    AROUSE("启发","arouse-btn"),

    /**
     * 教案
     */
    LESSON_PLAN("教案","plan-btn");

    private String dirName;
    private String btnStyle;

    ChapterBtnEnum(String dirName, String btnStyle){
        this.dirName = dirName;
        this.btnStyle = btnStyle;
    }

    public static ChapterBtnEnum getTypeFromDir(String dirName){
        if(IMPORT.dirName.equals(dirName)){
            return IMPORT;
        }
        if(TEACH.dirName.equals(dirName)){
            return TEACH;
        }
        if(AROUSE.dirName.equals(dirName)){
            return AROUSE;
        }
        if(LESSON_PLAN.dirName.equals(dirName)){
            return LESSON_PLAN;
        }
        return null;
    }


    public String getDirName() {
        return dirName;
    }

    public String getBtnStyle() {
        return btnStyle;
    }
}
