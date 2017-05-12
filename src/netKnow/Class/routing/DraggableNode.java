package netKnow.Class.routing;

/**
 * Created by MQ on 2017-05-12.
 */
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

public class DraggableNode extends AnchorPane{

    @FXML AnchorPane root_pane;
    @FXML AnchorPane nodeBody;

    private DragIconType mType = null;

    public DraggableNode() {
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
    private void initialize() {}

    public void relocateToPoint (Point2D p) {

        //relocates the object to a point that has been converted to
        //scene coordinates
        Point2D localCoords = getParent().sceneToLocal(p);

        relocate (
                (int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
                (int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
        );
    }

    public DragIconType getType () { return mType; }

    public void setType(DragIconType type) {

        mType = type;

        nodeBody.getStyleClass().clear();
        nodeBody.getStyleClass().add("dragicon");
        //getStyleClass().clear();
        //getStyleClass().add("dragicon");
        switch (mType) {
            case pcIco:
                //getStyleClass().add("icon-pc");
                nodeBody.getStyleClass().add("icon-pc");
                break;
            case routerIco:
                //getStyleClass().add("icon-router");
                nodeBody.getStyleClass().add("icon-router");
                break;
            case switchIco:
                //getStyleClass().add("icon-switch");
                nodeBody.getStyleClass().add("icon-switch");
                break;
            case labelIco:
                //getStyleClass().add("icon-label");
                nodeBody.getStyleClass().add("icon-label");
                break;

            default:
                break;
        }
    }

}