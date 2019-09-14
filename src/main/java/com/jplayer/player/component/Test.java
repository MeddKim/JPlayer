package com.jplayer.player.component;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.RuntimeUtil;

public class Test {
    public static void main(String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "D:\\dev\\lib\\vlc\\x64");


//        Native.loadLibrary("D:/dev/lib/vlc/x64", LibVlc.class);
        System.out.println(LibVlc.libvlc_get_version());
    }
}
