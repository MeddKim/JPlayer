package com.jplayer.player.component.imgslider;

import com.google.common.collect.Lists;
import com.jplayer.player.domain.ChapterFile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author Wyatt
 * @date 2019-10-02
 */
@Slf4j
public class MainPageImageSlider extends BorderPane {

    private Button btnLeft;
    private Button btnRight;

    private List<ImageEventListener> listeners;

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


    /**
     * 课程选择页图片的宽高
     */
    private double courseImageWidth = 208;
    private double courseImageHeight = 117;

    private double selectItemHight = 135;
    private double selectItemWidth = 240;

    private HBox imgBox;

    private int currentPage;
    private int pageSize;
    private int totalItem;
    private int maxPageSize;

    private Object data;

    private List<ImageView> imageViews;


    public MainPageImageSlider(double width, boolean isCourseSelect){
        this.componentWidth = width;
        initLayout(width);
        imageSlider();
    }

    public MainPageImageSlider(double width){
        super();
        this.componentWidth = width;
        initLayout();
        imageSlider();
    }

    private void imageSlider(){
        this.listeners = Lists.newArrayList();
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
    }

    /**
     * 宽高已限定，用户课程选择页
     * @param width  整体宽度
     */
    void initLayout(double width){
        this.itemSpace = 20;
        this.itemBoxWidth = width - this.switchItemSize * 2 - this.switchPadding * 4;
        this.itemNum = (int) Math.floor(this.itemBoxWidth / (this.courseImageWidth + this.itemSpace));
        this.itemWidth = this.courseImageWidth;
        this.itemHeight = this.courseImageHeight;

        this.componentHeight = this.courseImageHeight;
    }

    /**
     * 自适应宽高，用于课程主页
     */
    void initLayout(){
        //计算相片的宽度
        this.itemBoxWidth = this.componentWidth - this.switchItemSize * 2 - this.switchPadding * 4;
        log.info("ImageSlider的宽为:{}，切换按钮的大小为：{}，切换按钮的padding为：{}，计算的itemBox的宽为：{}",this.componentWidth,this.switchItemSize,this.switchPadding,this.itemBoxWidth);
        this.itemWidth = (this.itemBoxWidth - this.itemSpace * 4) / this.itemNum;
        //相片的宽高要满足 16 ： 9
        this.itemHeight = (itemWidth / 16) * 9;
        this.componentHeight = this.itemHeight;
    }



    public void initSwitch(){
        this.btnLeft = new Button();
        btnLeft.getStyleClass().add("switch-left");

        this.btnRight = new Button();
        btnRight.getStyleClass().add("switch-right");

        BorderPane.setAlignment(this.btnLeft, Pos.CENTER);
        BorderPane.setAlignment(this.btnRight, Pos.CENTER);

        this.btnLeft.setPadding(new Insets(0,this.switchPadding,0,this.switchPadding + 10));
        this.btnRight.setPadding(new Insets(0,this.switchPadding + 10,0,this.switchPadding ));

        this.btnLeft.setOnMouseClicked(e->{
            pageChange(-1);
        });
        this.btnRight.setOnMouseClicked(e->{
            pageChange(1);
        });

        this.setLeft(this.btnLeft);
        this.setRight(this.btnRight);
    }
    private void pageChange(int flag){
        this.setImageBox(this.currentPage + flag);
        this.setControlBtn();
    }

    public void initImages(List<SliderEvent> itemDatas){
        this.imgBox.setSpacing(this.itemSpace);
        for(int i=0 ; i < itemDatas.size(); i++){
            SliderEvent itemData = itemDatas.get(i);
//        }
//        for(SliderEvent itemData : itemDatas){
            Image image = new Image(itemData.getImagePath());
            ImageView imageView = new ImageView(image);
            if( i == 0){
                imageView.setFitWidth(this.selectItemWidth);
                imageView.setFitHeight(this.selectItemHight);
                ChapterFile chapterFile = (ChapterFile) itemData.getData();
                chapterFile.setIsSelected(true);
            }else {
                imageView.setFitWidth(this.itemWidth);
                imageView.setFitHeight(this.itemHeight);
            }
            imageView.setUserData(itemData.getData());
            imageView.getStyleClass().add("item-image");
            setImageViewSize(imageView);
            imageView.setOnMouseClicked(event -> {
                ImageView source = (ImageView)event.getSource();
                this.data = source.getUserData();
                SliderEvent sliderEvent = new SliderEvent();
                sliderEvent.setData(source.getUserData());
                notifyListener(sliderEvent);
                imageViewClick(source,this.imageViews);
            });
            this.imageViews.add(imageView);
        }
        initPageInfo();
        setControlBtn();
    }

    private void imageViewClick(ImageView imageView,List<ImageView> imageViews){

        for(ImageView view : imageViews){
            view.setFitWidth(this.courseImageWidth);
            view.setFitHeight(this.courseImageHeight);
            ChapterFile file = (ChapterFile)view.getUserData();
            file.setIsSelected(false);
        }
        imageView.setFitWidth(this.selectItemWidth);
        imageView.setFitHeight(this.selectItemHight);
        ChapterFile currentFile = (ChapterFile)imageView.getUserData();
        currentFile.setIsSelected(true);
    }

    private void setImageViewSize(ImageView imageView){
        imageView.setOnMouseEntered(e->{
            ImageView source = (ImageView)e.getSource();
            ChapterFile file = (ChapterFile)source.getUserData();
            if(file.getIsSelected()){
                return;
            }
            source.setFitHeight(135);
            source.setFitWidth(240);
        });
        imageView.setOnMouseExited(e->{
            ImageView source = (ImageView)e.getSource();
            ChapterFile file = (ChapterFile)source.getUserData();
            if(file.getIsSelected()){
                return;
            }
            source.setFitHeight(126);
            source.setFitWidth(224);
        });
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
    public void notifyListener(SliderEvent event){
        for (ImageEventListener listener : this.listeners){
            listener.handleEvent(event);
        }
    }

    public void addListener(ImageEventListener listener){
        this.listeners.add(listener);
    }

    void setControlBtn(){
        if(CollectionUtils.isEmpty(this.imageViews)){
            this.btnLeft.setVisible(false);
            this.btnRight.setVisible(false);
            return;
        }
        if(this.currentPage == 1 && this.maxPageSize == 1){
            this.btnLeft.setVisible(false);
            this.btnRight.setVisible(false);
            return;
        }
        if(this.currentPage == 1){
            this.btnLeft.setVisible(false);
            this.btnRight.setVisible(true);
            return;
        }
        if(this.currentPage == this.maxPageSize){
            this.btnLeft.setVisible(true);
            this.btnRight.setVisible(false);
            return;
        }
        this.btnLeft.setVisible(true);
        this.btnRight.setVisible(true);
    }

    public static void main(String[] args) {

        System.out.println(((int)Math.ceil((double)5/(double)3)));
    }
}
