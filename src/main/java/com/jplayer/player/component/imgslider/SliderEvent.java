package com.jplayer.player.component.imgslider;

import lombok.Data;

/**
 * @author Wyatt
 * @date 2019-10-06
 */
@Data
public class SliderEvent {
    private String imagePath;
    private Object data;
    private Boolean isSelected;
}
