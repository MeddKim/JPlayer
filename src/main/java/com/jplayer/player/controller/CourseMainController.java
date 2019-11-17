package com.jplayer.player.controller;

import com.google.common.collect.Lists;
import com.jplayer.MainLauncher;
import com.jplayer.player.component.imgslider.ImageEventListener;
import com.jplayer.player.component.imgslider.MainPageImageSlider;
import com.jplayer.player.component.imgslider.SliderEvent;
import com.jplayer.player.component.media.MusicPlayer;
import com.jplayer.player.component.media.ProtoMediaPlayer;
import com.jplayer.player.component.pdf.PdfBorderPane;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.jplayer.MainLauncher.globalAppHeight;
import static com.jplayer.MainLauncher.globalAppWidth;

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
    @FXML
    private Label bgSloganLabel;

    List<Button> chapterBtns;

    private int defaultChapter = 0;

    private ProtoMediaPlayer mediaPlayer;

    private MusicPlayer musicPlayer;

    private PdfBorderPane pdfReader;

    private String currentPath;
    private String prePath;

    private String nextCourse = getClass().getResource("/images/course/next-course.png").toString();
    private String preCourse = getClass().getResource("/images/course/pre-course.png").toString();

    private Button nextCourseBtn = new Button();
    private Button preCourseBtn = new Button();



    private double mediaWidth = 800;
    private double mediaHeight = 450;

    private ArrayList<ChapterInfo> chapterInfos;

    private ChapterInfo currentChapterInfo;

    public void initialize() {
        calLayout();
        initCourseBtn();
        initComponent();
        this.chapterBtns = Lists.newArrayList();
        this.bgSloganLabel.setText(MainLauncher.mainPageSlogan);
    }

    private void initCourseBtn(){
        ImageView next = new ImageView(new Image(nextCourse));
        ImageView pre = new ImageView(new Image(preCourse));
        this.nextCourseBtn.setGraphic(next);
        this.preCourseBtn.setGraphic(pre);
    }


    private void calLayout(){
        if(ScreenScaleEnum.W_16_9_2560.getAppWidth() == MainLauncher.globalAppWidth){
            this.mediaWidth = ScreenScaleEnum.W_16_9_1366.getAppWidth();
            this.mediaHeight = ScreenScaleEnum.W_16_9_1366.getAppHeight();
        }else if(ScreenScaleEnum.W_16_9_1366.getAppWidth() == MainLauncher.globalAppWidth || ScreenScaleEnum.W_16_9_1920.getAppWidth() == MainLauncher.globalAppWidth){
//            this.mediaWidth = ScreenScaleEnum.W_16_9_960.getAppWidth();
//            this.mediaHeight = ScreenScaleEnum.W_16_9_960.getAppHeight();
            this.mediaWidth = 1120;
            this.mediaHeight = 630;
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
        this.chapterInfos = CommonUtils.getChapterInfo(coursePath);
        initChapterBtn(this.chapterInfos);
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
                this.currentChapterInfo = chapterInfo;
            }
            button.setOnMouseClicked(e->{
                Button btn = (Button)e.getSource();
                selectButton(btn);
            });
            VBox.setMargin(button, new Insets(0,0,0,-35));
            this.chapterBtns.add(button);
            chapterBox.getChildren().add(button);
        }
        // add the pre and next button
        this.preCourseBtn.setOnMouseClicked(e -> {
            courseChange(this.currentPath,false);
        });
        this.nextCourseBtn.setOnMouseClicked(e -> {
            courseChange(this.currentPath,true);
        });
        this.chapterBox.getChildren().add(this.preCourseBtn);
        this.chapterBox.getChildren().add(this.nextCourseBtn);
        VBox.setMargin(this.preCourseBtn,new Insets(0,0,0,-35));
        VBox.setMargin(this.nextCourseBtn,new Insets(0,0,0,-35));
        setContainer(chapterInfos.get(0).getChapterFiles().get(0));
    }

    private void setImageSlider(ChapterInfo chapterInfo){
        this.containerPane.setBottom(null);
        if(ChapterBtnEnum.LESSON_PLAN.equals(chapterInfo.getChapterType())
                || ChapterBtnEnum.FAMILY.equals(chapterInfo.getChapterType())
                || CollectionUtils.isEmpty(chapterInfo.getChapterFiles())){
            return;
        }
        List<SliderEvent> imageDatas = Lists.newArrayList();
        for(ChapterFile  chapterFile: chapterInfo.getChapterFiles()){
            SliderEvent itemData = new SliderEvent();
            itemData.setData(chapterFile);
            itemData.setImagePath(chapterFile.getThumbUrl());
            imageDatas.add(itemData);
        }
        MainPageImageSlider slider = new MainPageImageSlider(this.mediaWidth,true);
        slider.addListener(this);
        slider.initImages(imageDatas);
        BorderPane.setAlignment(slider, Pos.TOP_CENTER);
        BorderPane.setMargin(slider,new Insets(50,0,50,0));
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
            this.musicPlayer = new MusicPlayer(this.mediaWidth,this.mediaHeight);
            musicPlayer.start(chapterFile.getPlayUrl(),chapterFile.getBgUrl(),false);
            musicPlayer.setChapterFile(chapterFile);
            musicPlayer.setChapterInfo(this.currentChapterInfo);
            this.containerPane.setCenter(this.musicPlayer);
            BorderPane.setAlignment(this.musicPlayer,Pos.BOTTOM_CENTER);
    }

    private void setPdfToContainer(ChapterFile chapterFile){
        this.pdfReader = new PdfBorderPane(960,800);
        this.pdfReader.loadPdfDocument(chapterFile.getPlayUrl());
        this.containerPane.setCenter(this.pdfReader);
        BorderPane.setAlignment(this.pdfReader,Pos.BOTTOM_CENTER);
    }

    private void setVideoToContainer(ChapterFile chapterFile){
        try {
            URL url = new URL(chapterFile.getPlayUrl());
            this.mediaPlayer = new ProtoMediaPlayer(this.mediaWidth,this.mediaHeight);
            this.mediaPlayer.start(url.toString(),false);
            this.mediaPlayer.setChapterFile(chapterFile);
            this.mediaPlayer.setChapterInfo(this.currentChapterInfo);
            this.containerPane.setCenter(this.mediaPlayer);
            BorderPane.setAlignment(this.mediaPlayer,Pos.BOTTOM_CENTER);
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
        this.currentChapterInfo = chapterInfo;
        button.getStyleClass().clear();
        button.getStyleClass().add(chapterInfo.getChapterType().getBtnStyle() + "-selected");
        setImageSlider(chapterInfo);
        setContainer(CollectionUtils.isEmpty(chapterInfo.getChapterFiles()) ? null : chapterInfo.getChapterFiles().get(0));
    }

    /**
     * 用于chapter切换时清空原有组件数据
     */
    private void destroy(){
        if(this.musicPlayer != null){
            this.musicPlayer.destroy();
            this.musicPlayer = null;
        }
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
                stage.setWidth(globalAppWidth);
                stage.setHeight(globalAppHeight);
                con.initCourseInfo(this.prePath);
            });
        }catch (Exception e){

        }

    }


    public void courseChange(String currentPath,Boolean isNextCourse){
        String changePath = isNextCourse ? CommonUtils.getNextCoursePath(currentPath) : CommonUtils.getPreCoursePath(currentPath);
        if(changePath == null){
            return;
        }
        this.initChapterInfo(changePath,this.prePath);
    }
}
