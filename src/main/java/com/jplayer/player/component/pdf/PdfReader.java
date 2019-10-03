package com.jplayer.player.component.pdf;

/**
 * @author Wyatt
 * @date 2019-10-03
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.icepdf.core.pobjects.*;
import org.icepdf.core.pobjects.actions.Action;
import org.icepdf.core.pobjects.actions.GoToAction;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.icepdf.core.util.Library;

/**
 *
 * @author jiu
 */
public class PdfReader extends Application {
    private Stage  primaryStage ;
    private BorderPane borderPane;
    private SplitPane splitPane;
    private ToolBar toolBar;
    private StackPane treePane;
    private VBox viewPane;
    private ScrollPane viewScrollPane;
    private double[] dividerPositions = {0.2f, 0.9f};
    private Document document;
    private PageTree pageTree;
    private TextField curPageText;
    private TreeView treeView;
    private Button btnFirst ,  btnPrev , btnNetx  , btnLast  , btnOutline , btnZoomIn , btnZoomOut ;
    private Label totalPageLabel ;
    private Slider zoomSlider;
    private ImageView imageView ;

    private double zoom = 1.0 ;
    private double pageWidth =  0.0 ;
    private double pageHeigth = 0.0 ;

    private boolean showDemo = false ;
    private String  demoFilePath = "E:\\资料\\阎宏-Java与模式.pdf";


    @Override
    public void start(Stage primaryStage) {


        initToolBar();

        treeView = new TreeView();
        treePane = new StackPane();
        treePane.setMinWidth(180);
        treePane.getChildren().add(treeView);

        imageView = new ImageView();
        viewPane = new VBox();
        viewPane.setAlignment( Pos.CENTER );
        viewPane.setStyle("-fx-border-width:1;");
        viewPane.setSpacing( 10 );
        viewPane.getChildren().add( imageView );

        viewScrollPane = new ScrollPane();
        viewScrollPane.setStyle("-fx-background-color:#808080;");
        viewScrollPane.setContent( viewPane );
        viewScrollPane.setFitToWidth(true);
        viewScrollPane.setFitToHeight(true);


        splitPane = new SplitPane();
        splitPane.getItems().addAll(treePane, viewScrollPane);
        splitPane.setDividerPositions(dividerPositions);
        borderPane = new BorderPane();
        borderPane.setTop(toolBar);
        borderPane.setCenter(splitPane);

        Scene scene = new Scene(borderPane, 821, 600);

        this.primaryStage = primaryStage ;
        primaryStage.setTitle("JavaFX Pdf 阅读器");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest( new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                destory();
            }
        });

        if( showDemo ){
            loadPdfDocument( demoFilePath );
        }
    }

    /**
     * 初始化工具栏
     */
    private void initToolBar() {
        toolBar = new ToolBar();
        //打开本地文件
        Button btnOpen = new Button("打开");
        btnOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("选择PDF文档");
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF文档", "*.pdf"));
                File file = fc.showOpenDialog( primaryStage.getOwner());
                if( file != null && file.exists() ){
                    loadPdfDocument( file.getAbsolutePath() );
                }
            }
        });

        //回到首页
        btnFirst = new Button("<<");
        btnFirst.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showPage( 0 );
            }
        });
        btnFirst.setVisible(false);

        //上一页
        btnPrev = new Button("<");
        btnPrev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str = curPageText.getText();
                int _page  = Integer.parseInt( str );
                showPage( _page - 2 );
            }
        });
        btnPrev.setVisible(false);

        //当前页码 输入框
        curPageText = new TextField();
        curPageText.setMaxWidth(50);
        curPageText.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    String str = curPageText.getText();
                    int _page  = Integer.parseInt( str );
                    showPage( _page-1  );
                }
            }
        });
        curPageText.setVisible(false);

        //总页数
        totalPageLabel = new Label("共页");
        totalPageLabel.setVisible(false);

        //下一页
        btnNetx = new Button(">");
        btnNetx.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String str = curPageText.getText();
                int _page  = Integer.parseInt( str );
                showPage( _page  );
            }
        });
        btnNetx.setVisible(false);

        //跳到最后一页
        btnLast = new Button(">>");
        btnLast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showPage( document.getNumberOfPages() - 1  );
            }
        });
        btnLast.setVisible(false);

        //显示和隐藏大纲
        btnOutline = new Button("大纲");
        btnOutline.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (splitPane.getItems().size() > 1) {
                    dividerPositions = splitPane.getDividerPositions();
                    splitPane.getItems().remove(treePane);
                } else {
                    splitPane.getItems().add(0, treePane);
                    splitPane.setDividerPositions(dividerPositions);
                }
            }
        });
        btnOutline.setVisible(false);

        //缩小功能
        btnZoomOut = new Button("缩小");
        btnZoomOut.setStyle("-fx-border-size:0px;");
        btnZoomOut.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if( zoom <= 0.25 )
                    return ;
                zoomSlider.setValue( zoom * 0.75   );
            }
        });
        btnZoomOut.setVisible(false);


        zoomSlider = new Slider( 0.25f , 2 , 1 );
        zoomSlider.setMajorTickUnit(0.25f);
        zoomSlider.setBlockIncrement(0.1f);
        zoomSlider.setMaxWidth(100);
        zoomSlider.setVisible(false);

        zoomSlider.valueProperty().addListener( new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                zoom = newValue.doubleValue();
                double newWidth = pageWidth * zoom ;
                double newHeigth = pageHeigth * zoom ;
                ObservableList<Node> list = viewPane.getChildren() ;
                for( int i = 0 ; i < list.size() ; i++){
                    Node node = list.get(i) ;
                    if( node  instanceof  ImageView ){
                        ImageView iv = ((ImageView)node) ;
                        iv.setFitHeight(newHeigth);
                        iv.setFitWidth( newWidth );
                    }
                }
            }
        });


        //放大功能
        btnZoomIn = new Button("放大");
        btnZoomIn.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if( zoom >= 2 )
                    return ;

                zoomSlider.setValue( zoom  / 0.75 );
            }
        });
        btnZoomIn.setVisible(false);


        toolBar.getItems().addAll(btnOpen, btnFirst, btnPrev, curPageText, totalPageLabel, btnNetx, btnLast, btnOutline ,  btnZoomOut , zoomSlider , btnZoomIn );
    }

    private void loadPdfDocument( String filePath ){
        destory();
        document = new Document();
        try {
            document.setFile( filePath );
            pageTree = document.getPageTree();
        } catch (Exception ex) {
            Logger.getLogger(PdfReader.class.getName()).log(Level.SEVERE, null, ex);
            MessageBox.show( this.primaryStage  , "文件打开失败！");
            destory();
            return ;
        }

        curPageText.setVisible( true );
        treeView.setVisible(true);
        btnFirst.setVisible(true);   btnPrev.setVisible(true); btnNetx.setVisible(true);   btnLast.setVisible(true);   btnOutline.setVisible(true) ; btnZoomOut.setVisible(true); btnZoomIn.setVisible(true) ;
        totalPageLabel.setVisible(true);
        zoomSlider.setVisible(true);
        totalPageLabel.setText( " 共"+document.getNumberOfPages()+"页");
        loadOutlineTree();

        showPage(0);

    }

    private void destory(){
        if( document != null ){
            document.dispose();
        }
    }

    /**
     *
     * @param page 从0开始
     */
    private void showPage(int page) {
        int totalPage = document.getNumberOfPages();
        if (page >= totalPage) {
            page = totalPage - 1;
        } else if (page < 0) {
            page = 0;
        }

        curPageText.setText(String.valueOf(page + 1));
        try{
//            java.awt.Image image = document.getPageImage(page, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, 0f, 1.0f);
            BufferedImage image = (BufferedImage)document.getPageImage(page, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, 0f, 1.0f);
            PDimension pageSize = document.getPageDimension( page , 0f, 1.0f );
            pageWidth  = pageSize.getWidth() ;
            pageHeigth = pageSize.getHeight() ;
            imageView.setFitWidth(  pageWidth  * zoom );
            imageView.setFitHeight( pageHeigth * zoom );
//            imageView.setImage(javafx.scene.image.Image.impl_fromExternalImage(image));

            imageView.setImage(SwingFXUtils.toFXImage(image,null));;

        }catch (Exception e){

        }

    }

    /**
     * 创建树节点
     *
     * @param oli
     * @return
     */
    private TreeItem< OutlineItem> createNode(final OutlineItem oli) {
        return new TreeItem< OutlineItem>(oli) {
            private boolean isLeaf;
            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override
            public ObservableList<TreeItem< OutlineItem>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;
                    super.getChildren().setAll(buildChildren(this));
                }
                return super.getChildren();
            }

            @Override
            public boolean isLeaf() {
                if (isFirstTimeLeaf) {
                    isFirstTimeLeaf = false;
                    OutlineItem f = (OutlineItem) getValue();
                    isLeaf = (f.getSubItemCount() < 1);
                }
                return isLeaf;
            }

            private ObservableList<TreeItem< OutlineItem>> buildChildren(TreeItem< OutlineItem> TreeItem) {
                OutlineItem f = TreeItem.getValue();
                if (f != null && f.getSubItemCount() > 0) {
                    ObservableList< TreeItem< OutlineItem>> children = FXCollections.observableArrayList();
                    for (int i = 0; i < f.getSubItemCount(); i++) {
                        children.add(createNode(f.getSubItem(i)));
                    }
                    return children;
                }
                return FXCollections.emptyObservableList();
            }
        };
    }

    /**
     * 创建树
     *
     * @return
     */
    private void loadOutlineTree() {

        //获取pdf文档大纲
        Outlines outlines = document.getCatalog().getOutlines();
        if (outlines != null) {
            OutlineItem rootItem = outlines.getRootOutlineItem();
            treeView.setRoot(createNode(rootItem));

            //树节点选中的监听事件
            treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<OutlineItem>>() {
                @Override
                public void changed(ObservableValue<? extends TreeItem<OutlineItem>> observable, TreeItem<OutlineItem> oldValue, TreeItem<OutlineItem> newValue) {
                    if (newValue != null) {
                        OutlineItem item = newValue.getValue();
                        Destination dest = item.getDest() ;
                        if ( dest == null && item.getAction() != null) {
                            Action action = item.getAction();
                            if (action instanceof GoToAction) {
                                dest = ((GoToAction) action).getDestination();
                            }  else {
                                Library library = action.getLibrary();
                                @SuppressWarnings("rawtypes")
//                                Hashtable entries = action.getEntries();
                                HashMap<Object, Object> entries = action.getEntries();
                                dest = new Destination(library, library.getObject(entries, new Name("D")));
                            }
                        }
                        int pageNumber = pageTree.getPageNumber( dest.getPageReference() );
                        showPage(pageNumber);
                    }
                }
            });


            //不显示根节点
            treeView.setShowRoot(false);

            //设置树节点的显示格式 （暂未找到好的方法只能通过这种方法到替代）
            treeView.setCellFactory(new Callback<TreeView<OutlineItem>, TreeCell<OutlineItem>>() {
                @Override
                public TreeCell<OutlineItem> call(TreeView<OutlineItem> p) {
                    return new TreeCell<OutlineItem>() {
                        @Override
                        public void updateItem(OutlineItem item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                setText(getString());
                                setGraphic(getTreeItem().getGraphic());
                            }
                        }

                        private String getString() {
                            return getItem() == null ? "" : getItem().getTitle();
                        }
                    };
                }
            });

        }else{
            treeView.setRoot( null );
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
