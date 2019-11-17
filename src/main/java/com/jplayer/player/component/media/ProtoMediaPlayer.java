package com.jplayer.player.component.media;

import com.jplayer.MainLauncher;
import com.jplayer.MainTest;
import com.jplayer.player.component.simple.SimpleMediaPlayer;
import com.jplayer.player.domain.ChapterFile;
import com.jplayer.player.domain.ChapterInfo;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;

/**
 * @author Willard
 * @date 2019/9/26
 */
@Slf4j
public class ProtoMediaPlayer extends BorderPane {

    /**
     * 控制按钮box
     */
    private HBox controlBox;
    private Button playBtn;
    private Button maxBtn;
    private Button volumeBtn;
    private Label timeLb;
    private Slider processSd;
    private Slider volumeSd;

    private double width;
    private double height;

    /**
     * 内容区域整体padding
     */
    private double globalPadding = 5;

    /**
     * Button默认的padding 上下5，左右10
     */
    private double defaultBtnHPadding = 5;
    private double defaultBtnWPadding = 10;

    private double boxSpace = 5;

    private double defaultBtnSize = 24;

    /**
     * 资源按钮ICON位置
     */
    private String playIconPath = getClass().getResource("/images/media/play_btn.png").toString();
    private String stopIconPath = getClass().getResource("/images/media/stop_btn.png").toString();
    private String voiceIconPath = getClass().getResource("/images/media/voice_btn.png").toString();
    private String muteIconPath = getClass().getResource("/images/media/mute_btn.png").toString();
    private String maxIconPath = getClass().getResource("/images/media/max_btn.png").toString();




    /**
     * 播放相关信息
     */
    private String url;
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;
    /**
     * 窗口弹出方式
     */
    private Boolean popup;

    /**
     * 记录视频是否重复播放
     */
    private final boolean repeat = false;
    /**
     * 储存静音操作前的音量数据
     */
    private double volumeValue;
    /**
     * 记录视频持续时间
     */
    private Duration duration ;

    /**
     * 记录视频是否处播放到结束
     */
    private boolean atEndOfMedia = false;

    private Scene scene;

    private ChapterInfo chapterInfo;
    private ChapterFile chapterFile;


    /**
     * @param width 视频宽
     * @param height 视频高
     */
    public ProtoMediaPlayer(double width,double height){
        this.width = width;
        this.height = height;
        this.setMaxWidth(calPaneWidth(width));
        this.setMaxHeight(calPaneHeight(height));
        this.getStylesheets().add(getClass().getResource("/styles/proto_media.css").toString());
        this.getStyleClass().add("main-pane");

        initComponent();

        //播放按钮
        setPlayButton();
        setProcessSlider();
        setVolumeButton();
        setVolumeSd();
        setFullScreenBtn();

    }
    double calPaneHeight(double height){
        double mainHeight = height + this.defaultBtnSize + this.globalPadding * 2 + this.defaultBtnHPadding * 2;
        return mainHeight;
    }
    double calPaneWidth(double width){
        double mainWidth = width + this.globalPadding * 2;
        return mainWidth;
    }

    /**
     * 初始化各个组件 并且计算布局
     */
    private void initComponent(){
        this.initControlBox();
        this.initMediaView();
    }

    private void initControlBox(){
        this.controlBox = new HBox();
        this.controlBox.getStyleClass().add("control-box");
        /**
         * 部分主键宽度需要计算 playBtn、volumeBtn、maxBtn已经确认为  44 * 34(width * height)
         */
        double btnWidth = this.defaultBtnSize + this.defaultBtnWPadding * 2;
        double btnWidthSum = btnWidth * 3;
        // box中除掉button剩余的宽度 = 总宽度（this.width - globalPadding * 2 ）- 按钮总宽度 - 间距
        double restWidth = this.width + this.globalPadding * 2 - btnWidthSum - this.boxSpace * 5;
        // 计算其他部分的宽度 proccessSlider : timeLabel : 2 : 2 : 6
        double processSliderWidth = restWidth * 0.6;
        double timeLabelWidth = restWidth * 0.2;
        double voiceSliderWidth = restWidth * 0.2;



        this.playBtn = new Button();
        setIcon(this.playBtn,this.playIconPath,defaultBtnSize);
        this.volumeBtn = new Button();
        setIcon(this.volumeBtn,this.voiceIconPath,defaultBtnSize);
        this.maxBtn = new Button();
        setIcon(this.maxBtn,this.maxIconPath,defaultBtnSize);

        this.processSd = new Slider();
        this.processSd.setMaxWidth(processSliderWidth);
        this.processSd.setMinWidth(processSliderWidth);

        this.timeLb = new Label("10:30/167:34");
        this.timeLb.setMaxWidth(timeLabelWidth);
        this.timeLb.setMinWidth(timeLabelWidth);

        this.volumeSd = new Slider();
        this.volumeSd.setMaxWidth(voiceSliderWidth);
        this.volumeSd.setMinWidth(voiceSliderWidth);
        this.controlBox.setPadding(new Insets(10,0,10,0));
        this.controlBox.getChildren().addAll(this.playBtn,this.processSd,this.timeLb,this.volumeBtn,this.volumeSd,this.maxBtn);
        this.controlBox.setAlignment(Pos.CENTER);

        this.setBottom(this.controlBox);
    }

    private void initMediaView(){
        this.mediaView = new MediaView();
        this.mediaView.setFitHeight(this.height);
        this.mediaView.setFitWidth(this.width);
        this.setCenter(this.mediaView);
        BorderPane.setMargin(this.mediaView,new Insets(10,10,0,10));
    }


    private void printLayout(){
        System.out.println("整体高度：" + this.getHeight());

        System.out.println("box高度：" + this.controlBox.getHeight());
        System.out.println("图片高度：" + ((ImageView)this.maxBtn.getGraphic()).getFitHeight());
        System.out.println("按钮高度：" + this.maxBtn.getHeight());

        System.out.println("--------------------------------------------");
        System.out.println("整体宽度" + this.getWidth());
        System.out.println("box宽度：" + this.controlBox.getWidth());
        System.out.println("图片宽度：" + ((ImageView)this.maxBtn.getGraphic()).getFitWidth());
        System.out.println("按钮宽度：" + this.maxBtn.getWidth());
    }


    public void start(String url,Boolean popup){
        this.url = url;
        this.popup = popup;
        try {
            URL urlObj = new URL(this.url);
            //MediaView设置
            this.media = new Media(urlObj.toString());
            this.mediaPlayer = new MediaPlayer(media);
            this.mediaView.setMediaPlayer(mediaPlayer);
        }catch (Exception e){
            log.error("读取URL错误",e);
        }


        //设置播放器，在媒体资源加载完毕后，获取相应的数据，设置组件自适应布局
        setMediaPlayer(this.width,this.height);
    }

    /**
     * 设置mediaPlayer(参数：整个播放器的尺寸)
     * @param width
     * @param height
     */
    void setMediaPlayer(double width,double height){
        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        //视频就绪时更新 进度条 、时间标签、音量条数据,设置布局尺寸
        mediaPlayer.setOnReady(new Runnable(){
            @Override
            public void run() {
                duration = mediaPlayer.getMedia().getDuration();
                volumeValue = mediaPlayer.getVolume();
                mediaView.setFitWidth(width);
                mediaView.setFitHeight(height);
                updateValues();
            }
        });
        //mediaPlayer当前进度发生改变时候，进度条 、时间标签、音量条数据
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                updateValues();
            }
        });
    }

    /**
     * 更新视频数据（进度条 、时间标签、音量条数据）
     */
    protected void updateValues(){
        if(processSd != null && timeLb!=null && volumeSd != null && volumeBtn != null){
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    //设置时间标签
                    timeLb.setText(formatTime(currentTime,duration));
                    //无法读取时间是隐藏进度条
                    processSd.setDisable(duration.isUnknown());
                    if(!processSd.isDisabled() && duration.greaterThan(Duration.ZERO) && !processSd.isValueChanging()){
                        //设置进度条
                        processSd.setValue(currentTime.toMillis()/duration.toMillis() * 100);
                    }
                    if(!volumeSd.isValueChanging()){
                        //设置音量条
                        volumeSd.setValue((int)Math.round(mediaPlayer.getVolume() *100));
                        //设置音量按钮
                        if(mediaPlayer.getVolume() == 0){
                            setIcon(volumeBtn,muteIconPath,20);
                        }else{
                            setIcon(volumeBtn,voiceIconPath,20);
                        }
                    }
                }
            });
        }
    }

    //将Duration数据格式化，用于播放时间标签
    protected String formatTime(Duration elapsed,Duration duration){
        //将两个Duartion参数转化为 hh：mm：ss的形式后输出
        int intElapsed = (int)Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        int elapsedMinutes = (intElapsed - elapsedHours *60 *60)/ 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
        if(duration.greaterThan(Duration.ZERO)){
            int intDuration = (int)Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            int durationMinutes = (intDuration - durationHours *60 * 60) / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            if(durationHours > 0){
                return String.format("%02d:%02d:%02d / %02d:%02d:%02d",elapsedHours,elapsedMinutes,elapsedSeconds,durationHours,durationMinutes,durationSeconds);
            }else{
                return String.format("%02d:%02d / %02d:%02d",elapsedMinutes,elapsedSeconds,durationMinutes,durationSeconds);
            }
        }else{
            if(elapsedHours > 0){
                return String.format("%02d:%02d:%02d / %02d:%02d:%02d",elapsedHours,elapsedMinutes,elapsedSeconds);
            }else{
                return String.format("%02d:%02d / %02d:%02d",elapsedMinutes,elapsedSeconds);
            }
        }
    }


    //设置关闭窗口时的动作，手动释放资源，回收内存
    public void destroy(){
        if(mediaPlayer == null){
            return;
        }
        if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            mediaPlayer.stop();
        }
        mediaPlayer.dispose();   //释放meidaPlayer的Media资源
        media = null;
        mediaPlayer = null;
        System.gc();    //通知JVM垃圾回收器

    }

    //设置播放按钮动作
    private void setPlayButton(){
        this.playBtn.setOnAction((ActionEvent e)->{
            if(media == null){
                return;
            }
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if(status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED ){
                return;
            }
            /**
             * 当资源处于暂停或停止状态时
             */
            if(status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY || status == MediaPlayer.Status.STOPPED){
                //当资源播放结束时，重绕资源
                if(atEndOfMedia){
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    atEndOfMedia = false;
                }
                mediaPlayer.play();
                setIcon(this.playBtn,stopIconPath,this.defaultBtnSize);
            }else{   //当资源处于播放状态时
                mediaPlayer.pause();
                setIcon(this.playBtn,playIconPath,25);
            }
        });
    }

    /**
     * 为按钮获取图标
     * @param button
     * @param path
     * @param size
     * @description button有一个默认的padding = 上下5 左右10  放入一个 24 * 24的ImageView后
     *       button成了height * width = 34 * 44
     */
    private void setIcon(Button button,String path,double size){
        Image icon = new Image(path);
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        button.setGraphic(imageView);

        //设置图标点击时发亮
        ColorAdjust colorAdjust = new ColorAdjust();
        button.setOnMousePressed(event ->  {
            colorAdjust.setBrightness(0.5);
            button.setEffect(colorAdjust);
        });
        button.setOnMouseReleased(event -> {
            colorAdjust.setBrightness(0);
            button.setEffect(colorAdjust);
        });
    }

    /**
     * 设置视频进度条动作
     */
    private void setProcessSlider(){
        this.processSd.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                /**
                 * 加入Slider正在改变的判定，否则由于update线程的存在，mediaPlayer会不停地回绕
                 */
                if(processSd.isValueChanging()){
                    mediaPlayer.seek(duration.multiply(processSd.getValue()/100.0));
                }
            }
        });
    }

    private void setVolumeButton(){
        this.volumeBtn.setOnAction((ActionEvent e)->{
            if(media == null){
                return;
            }
            if(mediaPlayer.getVolume()>0){
                volumeValue = mediaPlayer.getVolume();
                volumeSd.setValue(0);
                setIcon(volumeBtn,muteIconPath,25);
            }else{
                mediaPlayer.setVolume(volumeValue);
                volumeSd.setValue(volumeValue * 100);
                setIcon(volumeBtn,voiceIconPath,15);
            }
        });
    }

    /**
     * 设置音量滑条动作
     */
    private void setVolumeSd(){
        volumeSd.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(newValue.doubleValue()/100);
            }
        });
    }

    private void setFullScreenBtn(){
        this.maxBtn.setOnAction((ActionEvent e)->{
            if(this.popup.equals(true)){
                return;
            }
            mediaPlayer.pause();
            try {
                popup(url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
    private void popup(String url) throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("");
        FXMLLoader fxmlLoader = new FXMLLoader(MainTest.class.getResource("/views/FullScreenImageView.fxml"));
        Parent root = (Pane) fxmlLoader.load();
        MainLauncher.fullScreenImageViewController = fxmlLoader.getController();
        MainLauncher.fullScreenImageViewController.initialize(this.chapterInfo,this.chapterFile);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(MainTest.class.getClassLoader().getResource("images/plug.gif").toString()));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //检测弹出窗口关闭事件，手动销毁simpleMediaPlayer对象；
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                MainLauncher.fullScreenImageViewController.destroy();
            }
        });
        primaryStage.show();
    }
    public void setChapterInfo(ChapterInfo chapterInfo){
        this.chapterInfo = chapterInfo;
    }

    public void setChapterFile(ChapterFile chapterFile){
        this.chapterFile = chapterFile;
    }
}