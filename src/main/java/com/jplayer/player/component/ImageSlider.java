package com.jplayer.player.component;

import com.google.common.collect.Lists;
import com.jplayer.player.domain.ChapterFile;
import com.jplayer.player.domain.ChapterInfo;
import com.jplayer.player.utils.CommonUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Wyatt
 * @date 2019-10-02   |  |  |  |  |
 */
@Slf4j
public class ImageSlider extends BorderPane {

    private String switchRight = getClass().getResource("/images/switch_right.png").toString();
    private String switchLeft = getClass().getResource("/images/switch_left.png").toString();


    /**
     * 组件的宽高
     */
    private double componentWidth;
    private double componentHeight;

    /**
     * 展示的相片数目
     */
    private int itemNum = 5;
    /**
     * 相片的宽高
     */
    private double itemBoxWidth;
    private double itemWidth;
    private double itemHeight;

    /**
     * 切换按钮size
     */
    private double switchItemSize = 50;

    private double switchPadding = 10;

    /**
     * 相片的间隔
     */
    private double itemSpace = 10;

    private HBox imgBox;

    private int currentPage;
    private int pageSize;
    private int totalItem;
    private int maxPageSize;

    private List<ImageView> imageViews;

    public ImageSlider(double width){
        super();
        this.componentWidth = width;
        initLayout();
        this.setWidth(this.componentWidth);
        this.setHeight(this.componentHeight);
        this.setMaxWidth(this.componentWidth);
        this.getStylesheets().add(getClass().getResource("/styles/image-slider.css").toString());

        this.imgBox = new HBox();
        this.imgBox.setAlignment(Pos.CENTER);
        this.setCenter(this.imgBox);
        this.imageViews = Lists.newArrayList();
        BorderPane.setAlignment(this.imgBox,Pos.CENTER);
        initSwitch();
        initImages();
        initPageInfo();
    }

    void initLayout(){
        //计算相片的宽度
        this.itemBoxWidth = this.componentWidth - this.switchItemSize * 2 - this.switchPadding * 4;
        log.info("ImageSlider的宽为:{}，切换按钮的大小为：{}，切换按钮的padding为：{}，计算的itemBox的宽为：{}",this.componentWidth,this.switchItemSize,this.switchPadding,this.itemBoxWidth);
        //  | | | | |
        this.itemWidth = (this.itemBoxWidth - this.itemSpace * 4) / 5;
        //相片的宽高要满足 16 ： 9
        this.itemHeight = (itemWidth / 16) * 9;
        this.componentHeight = this.itemHeight;
    }



    public void initSwitch(){
        Button btnLeft = new Button();
        btnLeft.getStyleClass().add("switch-left");

        Button btnRight = new Button();
        btnRight.getStyleClass().add("switch-right");

        BorderPane.setAlignment(btnLeft, Pos.CENTER);
        BorderPane.setAlignment(btnRight, Pos.CENTER);

        btnLeft.setPadding(new Insets(0,this.switchPadding,0,this.switchPadding));
        btnRight.setPadding(new Insets(0,this.switchPadding,0,this.switchPadding));

        btnLeft.setOnMouseClicked(e->{
            pageChange(-1);
        });
        btnRight.setOnMouseClicked(e->{
            pageChange(1);
        });

        this.setLeft(btnLeft);
        this.setRight(btnRight);
    }
    private void pageChange(int flag){
        this.setImageBox(this.currentPage + flag);
    }

    public void initImages(){
        this.imgBox.setSpacing(10);
        List<ChapterInfo> chapterInfo = CommonUtils.getChapterInfo("E:\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)");
        List<ChapterFile> chapterFiles = chapterInfo.get(1).getChapterFiles();
        for(int i = 0; i < chapterFiles.size(); i++){
            ChapterFile chapterFile = chapterFiles.get(i);
            Image image = new Image(chapterFile.getThumbUrl());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(this.itemWidth);
            imageView.setFitHeight(this.itemHeight);
            this.imageViews.add(imageView);
        }
    }

    public void initPageInfo(){
        this.totalItem = this.imageViews.size();
        this.pageSize = this.itemNum;
        this.maxPageSize = (int)Math.ceil((double) this.totalItem / (double) this.pageSize);

        setImageBox(1);
    }
    public void setImageBox(int currentPage){
        if(currentPage > this.maxPageSize || currentPage < 1){
            return;
        }
        int startItem;
        int endItem;
        startItem = this.pageSize * (currentPage  - 1);
        if(currentPage == this.maxPageSize){
            endItem = imageViews.size();
        }else {
            endItem = this.pageSize * currentPage;
        }
        this.currentPage = currentPage;
        this.imgBox.getChildren().clear();
        for(int i = startItem;i < endItem; i ++){
            this.imgBox.getChildren().add(imageViews.get(i));
        }

    }

    public static void main(String[] args) {

        System.out.println(((int)Math.ceil((double)5/(double)3)));
    }
}
