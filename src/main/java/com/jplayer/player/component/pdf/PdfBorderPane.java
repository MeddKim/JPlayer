package com.jplayer.player.component.pdf;

import com.jplayer.player.utils.CommonUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;


/**
 * @author Willard
 * @date 2019/11/3
 */
@Slf4j
public class PdfBorderPane extends BorderPane {

    private PdfReaderPane readerPane;
    private Button downloadBtn;
    private String pdfPath;

    public PdfBorderPane(double width,double height){
        this.readerPane = new PdfReaderPane(width,height);
        this.setCenter(this.readerPane);
        Image image = new Image("/images/download.png");
        ImageView imageView = new ImageView(image);
        this.downloadBtn = new Button();
        this.downloadBtn.setGraphic(imageView);
        this.setBottom(this.downloadBtn);
        BorderPane.setAlignment(this.downloadBtn, Pos.CENTER);
        BorderPane.setMargin(this.downloadBtn,new Insets(0,0,20,0));

        this.downloadBtn.setOnMouseClicked(e->{
            handExportDateAction();
        });
    }

    public void loadPdfDocument(String path){
        this.pdfPath = path;
        this.readerPane.loadPdfDocument(path);
    }

    public void destroy(){
        this.readerPane.destroy();
    }


    protected void handExportDateAction() {
        // ShowDialog.showConfirmDialog(FXRobotHelper.getStages().get(0),
        // "是否导出数据到txt？", "信息");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage s = new Stage();
        File file = fileChooser.showSaveDialog(s);
        if (file == null){
            return;
        }

        if(file.exists()){
            file.delete();
        }
        String exportFilePath = file.getAbsolutePath();
        log.info("导出文件的路径{}",exportFilePath);

        File source = new File(this.pdfPath);

        CommonUtils.copyFileUsingFileChannels(source,file);
//        ShowDialog.showMessageDialog(FXRobotHelper.getStages().get(0), "导出成功!保存路径:\n"+exportFilePath, "提示");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText("文件导出成功");

        alert.showAndWait();
    }
}
