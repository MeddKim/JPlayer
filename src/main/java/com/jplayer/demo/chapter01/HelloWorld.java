package com.jplayer.demo.chapter01;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author Willard
 * @date 2019/9/4
 */
public class HelloWorld extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btn = new Button();
        btn.setText("Click Me!");
        btn.setOnAction(event->{
            System.out.println("Hello World");
            handExportDateAction();
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        Scene scene = new Scene(root,300,250);

        primaryStage.setTitle("hello world");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        if(file.exists()){//文件已存在，则删除覆盖文件
            file.delete();
        }
        String exportFilePath = file.getAbsolutePath();
        System.out.println("导出文件的路径" + exportFilePath);

//
//        ObservableList<AmazonProductModel> list = tProduceView.getItems();
//        StringBuilder sBuilder=new StringBuilder();
//        if (list.size() > 0) {
//            for (AmazonProductModel model : list) {
//                sBuilder.append(model.getPid()+","+model.getPrice()+","+model.getName()+"\r\n");
//            }
//        }
//        FileWriteUtil.WriteDocument(exportFilePath, sBuilder.toString());
//        ShowDialog.showMessageDialog(FXRobotHelper.getStages().get(0), "导出成功!保存路径:\n"+exportFilePath, "提示");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
