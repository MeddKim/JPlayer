package com.jplayer.player.domain;

import com.jplayer.player.enums.ChapterBtnEnum;
import lombok.Data;

import java.util.List;

/**
 * @author Wyatt
 * @date 2019-09-28
 */
@Data
public class ChapterInfo {
    private String chapterId;
    private String chapterName;
    private String chapterPath;
    private ChapterBtnEnum chapterType;
    /**
     * 配合UI是否选中
     */
    private Boolean isSelected;

    List<ChapterFile> chapterFiles;
}
