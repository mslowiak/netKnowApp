package netKnow.Class.routing;

/**
 * Created by MQ on 2017-05-12.
 */
import java.io.IOException;
import java.util.UUID;

import com.sun.javafx.scene.input.DragboardHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import org.omg.CORBA.TRANSACTION_MODE;
import sun.plugin.javascript.navig.Anchor;

public class DraggableNode extends AnchorPane{

    @FXML AnchorPane root_pane;
    @FXML AnchorPane nodeBody;
    @FXML Label titleBar;
    @FXML Label closeButton;
    @FXML AnchorPane leftLinkHandle;
    @FXML AnchorPane rightLinkHandle;

    private final DraggableNode self;

    private EventHandler <DragEvent> mContextDragOver;
    private EventHandler <DragEvent> mContextDragDropped;

    private EventHandler <MouseEvent> mLinkHandleDragDetected; // wykrycie przeciagania
    private EventHandler <DragEvent> mLinkHandleDragDropped; // wykrycie konca przeciagania
    private EventHandler <DragEvent> mContextLinkDragOver; // wykrycie przeciagania z drugim obiektem
    private EventHandler <DragEvent> mContextLinkDragDroppped; // wykrycie konca przeciagania z drugim obiektem

    private NodeLink mDragLink;
    private AnchorPane rightPane;

    private DragIconType mType = null;
    private Point2D mDragOffset = new Point2D(0.0, 0.0);

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

    @FXML
    private void initialize() {
        buildNodeDragHandlers();
        buildLinkDragHandlers();

        leftLinkHandle.setOnDragDetected(mLinkHandleDragDetected);
        rightLinkHandle.setOnDragDetected(mLinkHandleDragDetected);

        leftLinkHandle.setOnDragDropped(mLinkHandleDragDropped);
        rightLinkHandle.setOnDragDropped(mLinkHandleDragDropped);

        mDragLink = new NodeLink();
        mDragLink.setVisible(false);

        parentProperty().addListener((observable, oldValue, newValue) -> {
            rightPane = (AnchorPane) getParent();
        });
    }

    private void buildNodeDragHandlers(){
        titleBar.setOnDragDetected( event ->{
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
        });
    }

    private void buildLinkDragHandlers(){
        mLinkHandleDragDetected = event -> {

            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            getParent().setOnDragOver(mContextLinkDragOver);
            getParent().setOnDragDropped(mLinkHandleDragDropped);

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

        mContextLinkDragDroppped = event -> {
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
            case labelIco:
                nodeBody.getStyleClass().add("icon-label");
                break;

            default:
                break;
        }
    }

}