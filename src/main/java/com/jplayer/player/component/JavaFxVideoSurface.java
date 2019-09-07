package com.jplayer.player.component;

import uk.co.caprica.vlcj.player.embedded.videosurface.CallbackVideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapters;

/**
 * 接收到帧数据的时候会调用该回调接口
 * @author Willard
 * @date 2019/9/6
 */
public class JavaFxVideoSurface extends CallbackVideoSurface {


    JavaFxVideoSurface(PlayEventListener listener){
        super(new JavaFxBufferFormatCallback(listener), new JavaFxRenderCallback(listener), true, VideoSurfaceAdapters.getVideoSurfaceAdapter());
    }
}
