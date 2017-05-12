package netKnow.Class.routing;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DragIcon extends AnchorPane {

    private DragIconType mType;

    public DragIcon() {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/netKnow/fxml/drag_icon.fxml")
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
    }

    public DragIconType getType() { return mType;}

    public void setType(DragIconType type) {

        mType = type;

        getStyleClass().clear();
        getStyleClass().add("dragicon");
        switch (mType) {

            case pcIco:
                getStyleClass().add("icon-pc");
                break;

            case routerIco:
                getStyleClass().add("icon-router");
                break;
            case switchIco:
                getStyleClass().add("icon-switch");
                break;
            case labelIco:
                getStyleClass().add("icon-label");
                break;
            default:
                break;
        }
    }

    public void relocateToPoint (Point2D p) {

        //Point2D localCoolabelrds = new Point2D(getParent().sceneToLocal(p));
        Point2D localCoords = getParent().sceneToLocal(p);

        relocate (
                (int) (localCoords.getX() -
                        (getBoundsInLocal().getWidth() / 2)),
                (int) (localCoords.getY() -
                        (getBoundsInLocal().getHeight() / 2))
        );
    }

}