package com.jplayer.player.component;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallback;

import java.nio.ByteBuffer;

/**
 * @author Willard
 * @date 2019/9/6
 */
public class JavaFxRenderCallback implements RenderCallback {

    private EventListener listener;

    JavaFxRenderCallback(EventListener listener){
        this.listener = listener;
    }

    @Override
    public void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat) {
        try {
            this.listener.display(mediaPlayer,nativeBuffers,bufferFormat);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
