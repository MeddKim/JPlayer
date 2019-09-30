package com.jplayer;

/**
 * @author Willard
 * @date 2019/9/29
 */

import com.jplayer.player.component.media.ProtoMediaPlayer;
import com.jplayer.player.component.simple.SimpleMediaPlayer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

/** 测试文件
 * */
public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        my(primaryStage);
//        other(primaryStage);
    }

    public static void my(Stage primaryStage) throws Exception{
        //创建测试窗口
        primaryStage.setTitle("Test Meida");
        BorderPane mainPane = new BorderPane();
        ProtoMediaPlayer pane = new ProtoMediaPlayer(960,540);
        URL url = new URL("file:/C:\\dev\\app\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)\\1.导入\\0.导入视频\\play.mp4");
        pane.start(url.toString(),false);
        mainPane.setCenter(pane);
        BorderPane.setAlignment(pane,Pos.CENTER);
        primaryStage.setScene(new Scene(mainPane, 1000, 800));
        primaryStage.show();

        System.out.println("窗口高度：" + pane.getHeight());
        System.out.println("窗口宽度：" + pane.getWidth());
    }

    public static void other(Stage primaryStage) throws Exception{
        URL url = new URL("file:/C:\\dev\\app\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)\\1.导入\\0.导入视频\\play.mp4");
        SimpleMediaPlayer player = SimpleMediaPlayer.newInstance(url.toString(),800,600);
        BorderPane.setAlignment(player,Pos.CENTER);
        primaryStage.setScene(new Scene(player, 1000, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}