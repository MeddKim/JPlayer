package com.jplayer.player.enums;

/**
 * @author Wyatt
 * @date 2019-10-02
 */
public enum ScreenScaleEnum {
    /**
     * 16 : 9
     */
    W_16_9_960(960.00,540.00,960.00,540.00,true),
    W_16_9_1366(1366.00,768.00,1366.00,768.00,true),
    W_16_9_1920(1920.00,1080.00,1920.00,1080.00,true),
    W_16_9_2560(2560.00,1440.00,2560.00,1440.00,true),

    /**
     * 4:3
     */
    W_4_3_1024(1024.00,768.00,960.00,540.00,false),
    W_4_3_1400(1400.00,1050.00,1366.00,768.00,false),
    W_4_3_1600(1600.00,1200.00,1366.00,768.00,false),
    W_4_3_2048(2048.00,1536.00,960.00,540.00,false),

    /**
     * 16 : 10
     */
    W_16_10_1024(1024.00,600.00,960.00,540.00,false),
    W_16_10_1280(1280.00,800.00,960.00,540.00,false),
    W_16_10_1440(1440.00,900.00,1366.00,768.00,false),
    W_16_10_1680(1680.00,1050.00,1366.00,768.00,false),
    W_16_10_1920(1920.00,1200.00,1920.00,1080.00,false),
    W_16_10_2560(2560.00,1600.00,2560.00,1440.00,false),


    /**
     * 5 : 4
     */
    W_5_4_1280(1280.00,1024.00,960.00,540.00,false),


    D_16_9_960(960.00,540.00,960.00,540.00,false);

    double screenWidth;
    double screenHeight;

    double appWidth;
    double appHeight;

    Boolean isFullScreen;

    ScreenScaleEnum(double screenWidth,double screenHeight,double appWidth, double appHeight,Boolean isFullScreen){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.appWidth = appWidth;
        this.appHeight = appHeight;
        this.isFullScreen = isFullScreen;
    }

    public double getAppWidth() {
        return appWidth;
    }

    public double getAppHeight() {
        return appHeight;
    }

    public Boolean getFullScreen() {
        return isFullScreen;
    }

    /**
     * 根据屏幕宽度分析app宽高
     * 默认使用 960 * 540
     * @param screenWidth
     * @return
     */
    public static ScreenScaleEnum getAppScale(double screenWidth){
        for (ScreenScaleEnum scaleEnum :ScreenScaleEnum.values()){
            if(scaleEnum.screenWidth == screenWidth){
                return scaleEnum;
            }
        }
        return ScreenScaleEnum.D_16_9_960;
    }



}
