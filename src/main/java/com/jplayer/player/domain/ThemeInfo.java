package com.jplayer.player.domain;

import lombok.Data;

/**
 * @author Willard
 * @date 2019/9/17
 */
@Data
public class ThemeInfo {
    private Long themeId;
    private String themeName;
    private Long moduleId;
    private String thumbUrl;
}
