package com.jplayer.player.component;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;

import java.nio.ByteBuffer;

/**
 * @author Willard
 * @date 2019/9/6
 */
public interface PlayEventListener {
    void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat);
    void bufferFormat(int sourceWidth, int sourceHeight);
}
