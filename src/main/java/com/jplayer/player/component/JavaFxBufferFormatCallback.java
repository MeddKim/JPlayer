package com.jplayer.player.component;

import javafx.application.Platform;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;

/**
 * 视频格式发生变化的时候，CallbackVideoSurface会调用该回调
 * @author Willard
 * @date 2019/9/6
 */
public class JavaFxBufferFormatCallback implements BufferFormatCallback {

    private EventListener listener;

    private PixelWriter pixelWriter;

    private WritableImage img;

    JavaFxBufferFormatCallback(EventListener listener,PixelWriter pixelWriter,WritableImage img){
        this.listener = listener;
        this.pixelWriter = pixelWriter;
        this.img = img;
    }

    @Override
    public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
        this.img = new WritableImage(sourceWidth, sourceHeight);
        this.pixelWriter = img.getPixelWriter();
        Platform.runLater(()->
                listener.bufferFormat(sourceWidth,sourceHeight)
        );
        return new RV32BufferFormat(sourceWidth, sourceHeight);
    }
}