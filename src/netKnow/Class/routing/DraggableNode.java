package netKnow.Class.routing;

/**
 * Created by MQ on 2017-05-12.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class DraggableNode extends AnchorPane{

    @FXML AnchorPane root_pane;
    @FXML AnchorPane nodeBody;
    @FXML public Label titleBar;
    @FXML public Label closeButton;
    @FXML public AnchorPane leftLinkHandle;
    @FXML public AnchorPane rightLinkHandle;
    @FXML Label topHostLabel;
    @FXML Label bottomHostLabel;
    @FXML Label leftHostLabel;
    @FXML Label rightHostLabel;

    private final DraggableNode self;
    public final List<String> mLinkIds = new ArrayList();
    public List<NodeLink> nodeLinks = new ArrayList<>();
    public List<NodeLink> nodePCLink = new ArrayList<>();
    public List<DraggableNode> pcList = new ArrayList<>();

    private EventHandler <DragEvent> mContextDragOver;
    private EventHandler <DragEvent> mContextDragDropped;

    public EventHandler <MouseEvent> mLinkHandleDragDetected; // wykrycie przeciagania
    public EventHandler <DragEvent> mLinkHandleDragDropped; // wykrycie konca przeciagania
    public EventHandler <DragEvent> mContextLinkDragOver; // wykrycie przeciagania z drugim obiektem
    public EventHandler <DragEvent> mContextLinkDragDropped; // wykrycie konca przeciagania z drugim obiektem

    private NodeLink mDragLink;
    private AnchorPane rightPane;

    private DragIconType mType = null;
    private Point2D mDragOffset = new Point2D(0.0, 0.0);
    public DraggableNodeData draggableNodeData;
    public RIPInfo ripInfo;

    public DraggableNode() {
        self = this;
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/netKnow/fxml/draggable_node.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setId(UUID.randomUUID().toString());
    }

    public DraggableNode(String id){
        self = this;
        setId(id);
    }

    public void setTitleBar(String titleBarText) {
        titleBar.setText(titleBarText);
    }

    @FXML
    private void initialize() {
        buildNodeDragHandlers();
        buildLinkDragHandlers();

        leftLinkHandle.setOnDragDetected(mLinkHandleDragDetected);
        rightLinkHandle.setOnDragDetected(mLinkHandleDragDetected);

        leftLinkHandle.setOnDragDropped(mLinkHandleDragDropped);
        rightLinkHandle.setOnDragDropped(mLinkHandleDragDropped);

        parentProperty().addListener(e -> {
            rightPane = (AnchorPane) getParent();
        });

        mDragLink = new NodeLink();
        mDragLink.setVisible(false);
    }

    private void buildNodeDragHandlers(){
        titleBar.setOnDragDetected( event ->{
            System.out.println("Drag in titlebar");
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            getParent().setOnDragOver(mContextDragOver);
            getParent().setOnDragDropped(mContextDragDropped);

            mDragOffset = new Point2D(event.getX(), event.getY());

            relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("type", mType.toString());
            content.put(DragContainer.DragNode, container);

            startDragAndDrop(TransferMode.ANY).setContent(content);

            event.consume();
        });

        mContextDragOver = (EventHandler<DragEvent>) event -> {
            event.acceptTransferModes(TransferMode.ANY);
            relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

            NodeLink linkerNode = null;
            for(String n : mLinkIds){ // przejdz po wszystkich nodelinks
                for(Node x : rightPane.getChildren()){
                    if(x != null ){
                        String xNodeID = x.getId();
                        if(xNodeID.equals(n)){
                            linkerNode = (NodeLink) x ;
                            linkerNode.relocateLabelCoords(rightPane);
                        }
                    }
                }
            }
            event.consume();
        };

        mContextDragDropped = (EventHandler<DragEvent>) event ->{
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            event.setDropCompleted(true);

            event.consume();
        };

        closeButton.setOnMouseClicked(event -> {
            AnchorPane parent = (AnchorPane) self.getParent();
            parent.getChildren().remove(self);
            
            for(ListIterator iterId = mLinkIds.listIterator(); iterId.hasNext();){
                String id = (String) iterId.next();

                for(ListIterator iterNode = parent.getChildren().listIterator(); iterNode.hasNext();){
                    Node node = (Node) iterNode.next();

                    if(node.getId() == null){
                        continue;
                    }
                    if(node.getId().equals(id)){
                        iterNode.remove();
                    }
                }
                iterId.remove();
            }
        });
    }

    private void buildLinkDragHandlers(){
        mLinkHandleDragDetected = event -> {
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            getParent().setOnDragOver(mContextLinkDragOver);
            getParent().setOnDragDropped(mContextLinkDragDropped);

            rightPane.getChildren().add(0,mDragLink);
            mDragLink.setVisible(false);

            Point2D p = new Point2D(
                    getLayoutX() + (getWidth() / 2.0),
                    getLayoutY() + (getHeight() / 2.0)
            );

            mDragLink.setStart(p);

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("source", getId());
            content.put(DragContainer.AddLink, container);

            startDragAndDrop (TransferMode.ANY).setContent(content);

            event.consume();
        };

        mLinkHandleDragDropped = event -> {
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            DragContainer container = (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

            if (container == null)
                return;

            mDragLink.setVisible(false);
            rightPane.getChildren().remove(0);
            rightPane.getChildren().remove(mDragLink.infoLabel);

            AnchorPane linkHandle = (AnchorPane) event.getSource();

            ClipboardContent content = new ClipboardContent();

            container.addData("target", getId());

            content.put(DragContainer.AddLink, container);

            event.getDragboard().setContent(content);
            event.setDropCompleted(true);
            event.consume();
        };

        mContextLinkDragOver = event -> {
            event.acceptTransferModes(TransferMode.ANY);

            if (!mDragLink.isVisible())
                mDragLink.setVisible(true);

            mDragLink.setEnd(new Point2D(event.getX(), event.getY()));

            event.consume();
        };

        mContextLinkDragDropped = event -> {
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            mDragLink.setVisible(false);
            rightPane.getChildren().remove(0);

            event.setDropCompleted(true);
            event.consume();
        };
    }

    public void relocateToPoint (Point2D p) {
        //relocates the object to a point that has been converted to
        //scene coordinates
        Point2D localCoords = getParent().sceneToLocal(p);

        relocate (
                (int) (localCoords.getX() - mDragOffset.getY()),
                (int) (localCoords.getY() - mDragOffset.getY())
        );
    }

    public void registerLink(String linkId) {
        mLinkIds.add(linkId);
    }

    public DragIconType getType () { return mType; }

    public void setType(DragIconType type) {

        mType = type;

        nodeBody.getStyleClass().clear();
        nodeBody.getStyleClass().add("dragicon");
        switch (mType) {
            case pcIco:
                nodeBody.getStyleClass().add("icon-pc");
                break;
            case routerIco:
                nodeBody.getStyleClass().add("icon-router");
                break;
            case switchIco:
                nodeBody.getStyleClass().add("icon-switch");
                break;
            default:
                break;
        }
    }

    public void setHostLabels(String host){
        rightHostLabel.setText(host);
        leftHostLabel.setText(host);
        topHostLabel.setText(host);
        bottomHostLabel.setText(host);
    }

    public void setIpTop(String ip){
        topHostLabel.setText(ip);
    }

}