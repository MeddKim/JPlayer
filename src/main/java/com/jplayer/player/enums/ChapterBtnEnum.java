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
    @Deprecated
    AROUSE("启发","arouse-btn"),
    /**
     * 练习
     */
    PRACTISE("练习","practise-btn"),

    /**
     * 复习
     */
    @Deprecated
    REVIEW("复习","review-btn"),
    /**
     * 学习
     */
    STUDY("学习","study-btn"),
    /**
     * 互动
     */
    INTERACT("互动","interact-btn"),
    /**
     * 家庭
     */
    FAMILY("家庭","family-btn"),
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
        if(PRACTISE.dirName.equals(dirName)){
            return PRACTISE;
        }
        if(LESSON_PLAN.dirName.equals(dirName)){
            return LESSON_PLAN;
        }
        if(REVIEW.dirName.equals(dirName)){
            return REVIEW;
        }
        if(STUDY.dirName.equals(dirName)){
            return STUDY;
        }
        if(INTERACT.dirName.equals(dirName)){
            return INTERACT;
        }
        if(FAMILY.dirName.equals(dirName)){
            return FAMILY;
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
