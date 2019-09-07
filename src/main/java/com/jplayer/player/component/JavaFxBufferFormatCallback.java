package com.jplayer.player.component;

import javafx.application.Platform;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;

/**
 * 视频格式发生变化的时候，CallbackVideoSurface会调用该回调
 * @author Willard
 * @date 2019/9/6
 */
public class JavaFxBufferFormatCallback implements BufferFormatCallback {


    private PlayEventListener listener;

    JavaFxBufferFormatCallback(PlayEventListener listener){
        this.listener = listener;
    }

    @Override
    public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
        Platform.runLater(()->
            listener.bufferFormat(sourceWidth,sourceHeight)
        );
        return new RV32BufferFormat(sourceWidth, sourceHeight);
    }
}