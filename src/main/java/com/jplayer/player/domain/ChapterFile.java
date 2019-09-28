package com.jplayer.player.domain;

import com.jplayer.player.enums.FileType;
import lombok.Data;

/**
 * @author Wyatt
 * @date 2019-09-28
 */
@Data
public class ChapterFile {
    private String fileId;
    private String fileName;
    private String thumbUrl;
    private String playUrl;
    private FileType type;
}
