package com.jplayer;

/**
 * @author Willard
 * @date 2019/9/29
 */

import com.jplayer.player.component.media.MusicPlayer;
import com.jplayer.player.component.media.ProtoMediaPlayer;
import com.jplayer.player.component.pdf.PdfReaderPane;
import com.jplayer.player.component.simple.SimpleMediaPlayer;
import com.jplayer.player.controller.CourseMainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

/** 测试文件
 * */
public class MainTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

//        my(primaryStage);
//        other(primaryStage);
//        pane(primaryStage);
//        imageSlider(primaryStage);
//        pdf(primaryStage);
//        bootBg(primaryStage);
        fullScreen(primaryStage);
    }


    public static void my(Stage primaryStage) throws Exception{
        //创建测试窗口
        primaryStage.setTitle("Test Meida");
        BorderPane mainPane = new BorderPane();
        MusicPlayer pane = new MusicPlayer(960,540);
        URL url = new URL("file:/D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)\\2.教学\\0.图片\\voice.mp3");
        String bg = "file:D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)\\2.教学\\0.图片\\voice_play_bg.jpg";
        pane.start(url.toString(),bg,false);
        mainPane.setCenter(pane);
        BorderPane.setAlignment(pane,Pos.CENTER);
        primaryStage.setScene(new Scene(mainPane, 1000, 800));
        primaryStage.show();

        System.out.println("窗口高度：" + pane.getHeight());
        System.out.println("窗口宽度：" + pane.getWidth());
    }

    public static void other(Stage primaryStage) throws Exception{
//        URL url = new URL("file:/E:\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)\\1.导入\\0.导入视频\\play.mp4");
        URL url = new URL("file:/C:/dev/app/JPlayer/course/0.FS未来素养课程/01.欺凌预防/01.识别欺凌(1)/01.导入/05.视频/video.mp4");
        SimpleMediaPlayer player = SimpleMediaPlayer.newInstance(url.toString(),800,600);
        BorderPane.setAlignment(player,Pos.CENTER);
        primaryStage.setScene(new Scene(player, 1000, 800));
        primaryStage.show();
    }

    public static void pane(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(MainTest.class.getResource("/views/CourseMain.fxml"));
        Parent root = (Pane) fxmlLoader.load();

        CourseMainController controller = fxmlLoader.<CourseMainController>getController();
//        controller.initChapterInfo("E:\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)");
        Scene mainScene = new Scene(root);

        //最大化窗口
        primaryStage.setMaximized(true);
        //取消所有默认设置（最大最小化，logo image等等）
//        primaryStage.initStyle(StageStyle.UNDECORATED);

        //设置任务栏logo
        primaryStage.getIcons().add(new Image(MainTest.class.getClassLoader().getResource("images/plug.png").toString()));
        primaryStage.setScene(mainScene);
        primaryStage.show();


        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    public static void imageSlider(Stage primaryStage) throws Exception{

//        ImageSlider slider = new ImageSlider(1000);
//        BorderPane.setAlignment(slider,Pos.CENTER);
//        primaryStage.setScene(new Scene(slider));
//
//        System.out.println("slider width:"+slider.getWidth());
//        primaryStage.show();
    }


    public static void pdf(Stage primaryStage) throws Exception{
        PdfReaderPane pdfReader = new PdfReaderPane(960,800);
        primaryStage.setScene(new Scene(pdfReader, 1000, 800));
        primaryStage.show();
    }

    public static void bootBg(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(MainTest.class.getResource("/views/BootPage.fxml"));
        Parent root = (Pane) fxmlLoader.load();

//        controller.initChapterInfo("E:\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)");
        Scene mainScene = new Scene(root);

        //最大化窗口
        primaryStage.setMaximized(true);
        //取消所有默认设置（最大最小化，logo image等等）
//        primaryStage.initStyle(StageStyle.UNDECORATED);

        //设置任务栏logo
        primaryStage.getIcons().add(new Image(MainTest.class.getClassLoader().getResource("images/plug.png").toString()));
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void fullScreen(Stage primaryStage) throws Exception{
        primaryStage.setTitle("");
        FXMLLoader fxmlLoader = new FXMLLoader(MainTest.class.getResource("/views/FullScreenImageView.fxml"));
        Parent root = (Pane) fxmlLoader.load();


//        MusicPlayer pane = new MusicPlayer(960,540);
//        URL url = new URL("file:/D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)\\2.教学\\0.图片\\voice.mp3");
//        String bg = "file:D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)\\2.教学\\0.图片\\voice_play_bg.jpg";
//        pane.start(url.toString(),bg,false);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(MainTest.class.getClassLoader().getResource("images/plug.gif").toString()));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}