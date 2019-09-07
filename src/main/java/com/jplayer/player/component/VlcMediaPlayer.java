package com.jplayer.player.component;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;

import java.nio.ByteBuffer;
import java.util.concurrent.Semaphore;

/**
 * @author Willard
 * @date 2019/9/6
 */
public class VlcMediaPlayer extends BorderPane implements PlayEventListener {

//    private String VIDEO_FILE = "C:\\devFile\\操作系统原理02.wmv";
    private String VIDEO_FILE = "C:\\hk\\4K Canada.webm";
    /**
     * canva画布，视频将输出到此处
     */
    private final Canvas canvas;

    /**
     * 像素写入类，用于向cavan中写入内容
     */
    private PixelWriter pixelWriter;

    /**
     * 用于将视频内容格式化为PixelWriter的可用内容
     */
    private WritablePixelFormat<ByteBuffer> pixelFormat;

    private final MediaPlayerFactory mediaPlayerFactory;

    /**
     * VLC播放器
     */
    private EmbeddedMediaPlayer mediaPlayer;

    /**
     *
     */
    private WritableImage img;

    private final Semaphore semaphore = new Semaphore(1);


    public VlcMediaPlayer(){
        canvas = new Canvas();

        pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        pixelFormat = PixelFormat.getByteBgraInstance();

        mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();

        mediaPlayer.videoSurface().set(new JavaFxVideoSurface(this));
        this.setCenter(canvas);

        setCenter(canvas);

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
    }

    public void startPlay(){
        this.mediaPlayer.controls().setRepeat(true);
        this.mediaPlayer.media().play(VIDEO_FILE);
    }

    public void stopPlay(){
        this.mediaPlayer.controls().stop();
        this.mediaPlayer.release();
        this.mediaPlayerFactory.release();
    }

    @Override
    public void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat) {
        try {
            semaphore.acquire();
            pixelWriter.setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixelFormat, nativeBuffers[0], bufferFormat.getPitches()[0]);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bufferFormat(int sourceWidth, int sourceHeight) {
        this.img = new WritableImage(sourceWidth,sourceHeight);
        this.pixelWriter = img.getPixelWriter();
    }

    public final void renderFrame() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        double width = canvas.getWidth();
        double height = canvas.getHeight();

        g.setFill(new Color(0, 0, 0, 1));
        g.fillRect(0, 0, width, height);

        if (img != null) {
            double imageWidth = img.getWidth();
            double imageHeight = img.getHeight();

            double sx = width / imageWidth;
            double sy = height / imageHeight;

            double sf = Math.min(sx, sy);

            double scaledW = imageWidth * sf;
            double scaledH = imageHeight * sf;

            Affine ax = g.getTransform();

            g.translate(
                    (width - scaledW) / 2,
                    (height - scaledH) / 2
            );

            if (sf != 1.0) {
                g.scale(sf, sf);
            }

            try {
                semaphore.acquire();
                g.drawImage(img, 0, 0);
                semaphore.release();
            }
            catch (InterruptedException e) {
            }

            g.setTransform(ax);
        }
    }
}
