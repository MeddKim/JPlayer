package com.jplayer.player.component.vlc;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;

import java.nio.ByteBuffer;

/**
 * @author Wyatt
 * @date 2019-09-14
 */
public interface EventListener {
    void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat);
    void bufferFormat(int sourceWidth, int sourceHeight);
}
