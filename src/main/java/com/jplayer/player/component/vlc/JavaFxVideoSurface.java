package com.jplayer.player.component.vlc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import uk.co.caprica.vlcj.player.embedded.videosurface.CallbackVideoSurface;
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapters;

/**
 * 接收到帧数据的时候会调用该回调接口
 * @author Willard
 * @date 2019/9/6
 */
public class JavaFxVideoSurface extends CallbackVideoSurface {


    JavaFxVideoSurface(EventListener listener, PixelWriter pixelWriter, WritableImage img){
        super(new JavaFxBufferFormatCallback(listener,pixelWriter,img), new JavaFxRenderCallback(listener), true, VideoSurfaceAdapters.getVideoSurfaceAdapter());
    }
}
