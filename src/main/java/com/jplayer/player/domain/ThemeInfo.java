package com.jplayer.player.domain;

import lombok.Data;

import java.util.List;

/**
 * @author Willard
 * @date 2019/9/17
 */
@Data
public class ThemeInfo {
    private String themeId;
    private String themeName;
    private String themePath;
    /**
     * 配合UI
     */
    private Boolean isSelected;
    List<CourseBaseInfo> course;
}
