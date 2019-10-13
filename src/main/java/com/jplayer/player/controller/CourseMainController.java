package com.jplayer.player.controller;

import com.google.common.collect.Lists;
import com.jplayer.MainLauncher;
import com.jplayer.player.component.imgslider.ImageEventListener;
import com.jplayer.player.component.imgslider.ImageSlider;
import com.jplayer.player.component.imgslider.SliderEvent;
import com.jplayer.player.component.media.ProtoMediaPlayer;
import com.jplayer.player.component.pdf.PdfReaderPane;
import com.jplayer.player.domain.ChapterFile;
import com.jplayer.player.domain.ChapterInfo;
import com.jplayer.player.enums.ChapterBtnEnum;
import com.jplayer.player.enums.ScreenScaleEnum;
import com.jplayer.player.utils.CommonUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.jplayer.MainLauncher.screenHeight;
import static com.jplayer.MainLauncher.screenWidth;

/**
 * @author Willard
 * @date 2019/9/24
 */
@Slf4j
public class CourseMainController implements ImageEventListener {
    public static CourseSelectController con;

    private Scene scene;

    @FXML
    private BorderPane mainPane;
    @FXML
    private VBox chapterBox;
    @FXML
    private BorderPane containerPane;

    List<Button> chapterBtns;

    private int defaultChapter = 0;

    private ProtoMediaPlayer mediaPlayer;

    private PdfReaderPane pdfReader;


    private String currentPath;
    private String prePath;


    /**
     * 各种组件大小
     */
    private double chapterBtnWidth = 287;
    private double chapterBtnHeight = 105;
    private double homeBtnWidth = 110;
    private double homeBtnHeight = 180;

    /**
     * 一下是需要计算得到的数据
     */
    private double containerWidth = 960;
    private double containerHeight = 540;

    private double mediaWidth = 800;
    private double mediaHeight = 450;

    private double pdfReaderWidth;
    private double pdfReaderHeight;


    public void initialize() {
        calLayout();
        initComponent();
        DropShadow dropshadow = new DropShadow();// 阴影向外
        dropshadow.setRadius(10);// 颜色蔓延的距离
        dropshadow.setOffsetX(0);// 水平方向，0则向左右两侧，正则向右，负则向左
        dropshadow.setOffsetY(0);// 垂直方向，0则向上下两侧，正则向下，负则向上
        dropshadow.setSpread(0.1);// 颜色变淡的程度
        dropshadow.setColor(Color.BLACK);// 设置颜色
        this.containerPane.setEffect(dropshadow);
//        this.mainPane.setEffect(dropshadow);
        this.containerPane.setBackground(Background.EMPTY);
        this.chapterBtns = Lists.newArrayList();
    }

    private void calLayout(){
        if(ScreenScaleEnum.W_16_9_2560.getAppWidth() == MainLauncher.globalAppWidth){
            this.mediaWidth = ScreenScaleEnum.W_16_9_1366.getAppWidth();
            this.mediaHeight = ScreenScaleEnum.W_16_9_1366.getAppHeight();
        }else if(ScreenScaleEnum.W_16_9_1366.getAppWidth() == MainLauncher.globalAppWidth || ScreenScaleEnum.W_16_9_1920.getAppWidth() == MainLauncher.globalAppWidth){
            this.mediaWidth = ScreenScaleEnum.W_16_9_960.getAppWidth();
            this.mediaHeight = ScreenScaleEnum.W_16_9_960.getAppHeight();
        }
    }

    private void initComponent(){

    }

    /**
     * 初始化课程信息
     * @param coursePath 课程目录
     * @param prePath 上级目录即模块目录
     */
    public void initChapterInfo(String coursePath,String prePath){
        this.currentPath = coursePath;
        this.prePath = prePath;
        ArrayList<ChapterInfo> chapterInfos = CommonUtils.getChapterInfo(coursePath);
        initChapterBtn(chapterInfos);
    }

    /**
     * 章节按钮 包括 导入 教学启发教案
     * @param chapterInfos
     */
    void initChapterBtn(ArrayList<ChapterInfo> chapterInfos){
        this.chapterBox.getChildren().clear();

        for(int i = 0;i < chapterInfos.size();i++){
            ChapterInfo chapterInfo = chapterInfos.get(i);
            Button button = createChapterBtn(chapterInfo);
            button.setUserData(chapterInfo);
            if(defaultChapter == i){
                chapterInfo.setIsSelected(true);
                button.getStyleClass().add(chapterInfo.getChapterType().getBtnStyle() + "-selected");
                setImageSlider(chapterInfo);
            }
            button.setOnMouseClicked(e->{
                Button btn = (Button)e.getSource();
                selectButton(btn);
            });
            VBox.setMargin(button, new Insets(0,0,0,-35));
            this.chapterBtns.add(button);
            chapterBox.getChildren().add(button);
        }
        setContainer(chapterInfos.get(0).getChapterFiles().get(0));

    }

    private void setImageSlider(ChapterInfo chapterInfo){
        this.containerPane.setBottom(null);
        if(ChapterBtnEnum.LESSON_PLAN.equals(chapterInfo.getChapterType()) || CollectionUtils.isEmpty(chapterInfo.getChapterFiles())){
            return;
        }
        List<SliderEvent> imageDatas = Lists.newArrayList();
        for(ChapterFile  chapterFile: chapterInfo.getChapterFiles()){
            SliderEvent itemData = new SliderEvent();
            itemData.setData(chapterFile);
            itemData.setImagePath(chapterFile.getThumbUrl());
            imageDatas.add(itemData);
        }
        ImageSlider slider = new ImageSlider(this.mediaWidth);
        slider.addListener(this);
        slider.initImages(imageDatas);
        BorderPane.setAlignment(slider, Pos.TOP_CENTER);
        this.containerPane.setBottom(slider);
    }

    private void setContainer(ChapterFile chapterFile){
        this.destroy();
        if(chapterFile == null){
            return;
        }
        switch (chapterFile.getType()){
            case IMG:
                setImageViewToContainer(chapterFile);
                break;
            case VEDIO:
                setVideoToContainer(chapterFile);
                break;
            case PDF:
                setPdfToContainer(chapterFile);
                break;
            default:
                this.destroy();
                log.info("文件类型{}不匹配",chapterFile.getType());
        }
    }

    private void setImageViewToContainer(ChapterFile chapterFile){
        Image image = new Image(chapterFile.getPlayUrl());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(this.mediaWidth);
        imageView.setFitHeight(this.mediaHeight);
        this.containerPane.setCenter(imageView);
    }

    private void setPdfToContainer(ChapterFile chapterFile){
        this.pdfReader = new PdfReaderPane(960,800);
        this.pdfReader.loadPdfDocument(chapterFile.getPlayUrl());
        this.containerPane.setCenter(this.pdfReader);
    }

    private void setVideoToContainer(ChapterFile chapterFile){
        try {
            URL url = new URL(chapterFile.getPlayUrl());
            this.mediaPlayer = new ProtoMediaPlayer(this.mediaWidth,this.mediaHeight);
            this.mediaPlayer.start(url.toString(),false);
            this.containerPane.setCenter(this.mediaPlayer);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private Button createChapterBtn(ChapterInfo chapterInfo){
        Button button = new Button();
        button.setUserData(chapterInfo);
        button.getStyleClass().add(chapterInfo.getChapterType().getBtnStyle());
        return button;
    }

    /**
     * button选择
     * @param button
     */
    private void selectButton(Button button){
        ChapterInfo chapterInfo = (ChapterInfo)button.getUserData();
        //已经是选择状态不需要操作
        if(chapterInfo.getIsSelected()){
            return;
        }
        for(Button btn : this.chapterBtns){
            ChapterInfo chapter = (ChapterInfo)btn.getUserData();
            chapter.setIsSelected(false);
            btn.getStyleClass().clear();
            btn.getStyleClass().add(chapter.getChapterType().getBtnStyle());
        }
        chapterInfo.setIsSelected(true);
        button.getStyleClass().clear();
        button.getStyleClass().add(chapterInfo.getChapterType().getBtnStyle() + "-selected");
        setImageSlider(chapterInfo);
        setContainer(CollectionUtils.isEmpty(chapterInfo.getChapterFiles()) ? null : chapterInfo.getChapterFiles().get(0));
    }

    /**
     * 用于chapter切换时清空原有组件数据
     */
    private void destroy(){
        if(this.mediaPlayer != null){
            this.mediaPlayer.destroy();
            this.mediaPlayer = null;
        }
        if(this.pdfReader != null){
            this.pdfReader.destroy();
            this.pdfReader = null;
        }
        this.containerPane.setCenter(null);
    }

    @Override
    public void handleEvent(SliderEvent event) {
        ChapterFile chapterFile = (ChapterFile)event.getData();
        setContainer(chapterFile);
    }

    public void returnCourseSelect(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/CourseSelect.fxml"));
            Parent root = (Pane) fxmlLoader.load();
            con = fxmlLoader.<CourseSelectController>getController();
            this.scene = new Scene(root);
            Platform.runLater(()-> {
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setMaximized(true);
                stage.setWidth(screenWidth);
                stage.setHeight(screenHeight);
                con.initCourseInfo(this.prePath);
            });
        }catch (Exception e){

        }

    }
}
