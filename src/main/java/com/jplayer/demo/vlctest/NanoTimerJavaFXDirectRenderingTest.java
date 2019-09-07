/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009, 2010, 2011, 2012, 2013, 2014, 2015 Caprica Software Limited.
 */

package com.jplayer.demo.vlctest;

import com.sun.jna.NativeLibrary;
import javafx.application.Application;
import uk.co.caprica.vlcj.binding.RuntimeUtil;

/**
 * Implementation of a JavaFX direct rendering media player that uses the {@link NanoTimer}.
 * <p>
 * In principle, this should be better performing than the corresponding JavaFX Timeline example.
 */
public final class NanoTimerJavaFXDirectRenderingTest extends JavaFXDirectRenderingTest {

    /**
     *
     */
    private static final double FPS = 60.0;

    /**
     *
     */
    private final NanoTimer nanoTimer = new NanoTimer(1000.0 / FPS) {
        @Override
        protected void onSucceeded() {
            renderFrame();
        }
    };

    @Override
    protected void startTimer() {
        nanoTimer.start();
    }

    @Override
    protected void stopTimer() {
        nanoTimer.cancel();
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\devFile\\vlc");
        Application.launch(args);
    }
}
