package netKnow.Class.routing;

/**
 * Created by MQ on 2017-05-12.
 */
import java.io.IOException;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class DraggableNode extends AnchorPane{

    @FXML AnchorPane root_pane;
    @FXML AnchorPane nodeBody;
    @FXML Label titleBar;
    @FXML Label closeButton;

    private final DraggableNode self;

    private EventHandler mContextDragOver;
    private EventHandler mContextDragDropped;

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
    }

    @FXML
    private void initialize() {
        buildNodeDragHandlers();
    }

    public void buildNodeDragHandlers(){
        titleBar.setOnDragDetected( e ->{
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            getParent().setOnDragOver(mContextDragOver);
            getParent().setOnDragDropped(mContextDragDropped);

            mDragOffset = new Point2D(e.getX(), e.getY());

            relocateToPoint(new Point2D(e.getSceneX(), e.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("type", mType.toString());
            content.put(DragContainer.DragNode, container);

            startDragAndDrop(TransferMode.ANY).setContent(content);

            e.consume();
        });

        mContextDragOver = (EventHandler<DragEvent>) e -> {
            e.acceptTransferModes(TransferMode.ANY);
            relocateToPoint(new Point2D(e.getSceneX(), e.getSceneY()));
            e.consume();
        };

        mContextDragDropped = (EventHandler<DragEvent>) e ->{
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            e.setDropCompleted(true);

            e.consume();
        };

        closeButton.setOnMouseClicked(event -> {
            AnchorPane parent = (AnchorPane) self.getParent();
            parent.getChildren().remove(self);
        });
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