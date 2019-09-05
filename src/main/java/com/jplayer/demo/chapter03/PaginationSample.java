package com.jplayer.demo.chapter03;

/**
 * @author Willard
 * @date 2019/9/5
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PaginationSample extends Application {

    private Pagination pagination;
    String[] fonts = new String[]{};

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public int itemsPerPage() {
        return 15;
    }

    public VBox createPage(int pageIndex) {
        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            Label font = new Label(fonts[i]);
            box.getChildren().add(font);
        }
        return box;
    }

    @Override
    public void start(final Stage stage) throws Exception {
        fonts = Font.getFamilies().toArray(fonts);

        pagination = new Pagination(fonts.length/itemsPerPage(), 0);
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);
        Scene scene = new Scene(anchor);
        stage.setScene(scene);
        stage.setTitle("PaginationSample");
        scene.getStylesheets().add("/demo/chapter03/controlStyle.css");
        stage.show();
    }
}