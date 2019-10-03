package com.jplayer.player.component.pdf;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.PDimension;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.pobjects.PageTree;
import org.icepdf.core.util.GraphicsRenderingHints;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Wyatt
 * @date 2019-10-03
 */
@Slf4j
public class PdfReaderPane extends ScrollPane {
    private Document document;
    private VBox contentBox;
    private ImageView contentView;
    private List<ImageView> imageViews;

    private String filePath;

    private double height;
    private double width;
    private PageTree pageTree;
    private double pageWidth =  0.0 ;
    private double pageHeight = 0.0 ;

    private double zoom = 1.0 ;

    public PdfReaderPane(){
        this.contentBox = new VBox();
        this.contentView = new ImageView();
        this.contentBox.getChildren().add(this.contentView);
        this.setContent(this.contentBox);
        loadPdfDocument("C:\\Users\\Administrator\\Desktop\\深入理解c指针.pdf");
    }
    private void loadPdfDocument( String filePath ){
        destroy();
        this.document = new Document();
        try {
            document.setFile( filePath );
            pageTree = document.getPageTree();

//            showPage(0);
            showAllPage();
        } catch (Exception ex) {
            log.error("read pdf fail :",ex);
            destroy();
            return ;
        }

    }

    private void showPage(int page) {
        int totalPage = document.getNumberOfPages();
        if (page >= totalPage) {
            page = totalPage - 1;
        } else if (page < 0) {
            page = 0;
        }
        try{
            BufferedImage image = (BufferedImage)document.getPageImage(page, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, 0f, 1.0f);
            PDimension pageSize = document.getPageDimension( page , 0f, 1.0f );
            this.pageWidth  = pageSize.getWidth() ;
            this.pageHeight = pageSize.getHeight() ;
            contentView.setFitWidth(  pageWidth  * zoom );
            contentView.setFitHeight( pageHeight * zoom );
            contentView.setImage(SwingFXUtils.toFXImage(image,null));;
        }catch (Exception e){

        }

    }

    private void showAllPage(){
        int totalPage = document.getNumberOfPages();
        for(int page = 0;page < totalPage; page++){
            try{
                ImageView imageView = new ImageView();
                BufferedImage image = (BufferedImage)document.getPageImage(page, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, 0f, 1.0f);
                PDimension pageSize = document.getPageDimension( page , 0f, 1.0f );
                this.pageWidth  = pageSize.getWidth() ;
                this.pageHeight = pageSize.getHeight() ;
                imageView.setFitWidth(  pageWidth  * zoom );
                imageView.setFitHeight( pageHeight * zoom );
                imageView.setImage(SwingFXUtils.toFXImage(image,null));;
                this.contentBox.getChildren().add(imageView);
            }catch (Exception e){

            }
        }

    }


    private void destroy(){
        if( document != null ){
            document.dispose();
        }
    }

}
