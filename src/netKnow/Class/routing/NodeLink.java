package netKnow.Class.routing;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;

import java.io.IOException;

/**
 * Created by MQ on 2017-05-12.
 */
public class NodeLink extends AnchorPane{

    @FXML CubicCurve nodeLink;

    public NodeLink() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/netKnow/fxml/node_link.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize(){
        nodeLink.controlX1Property().bind(
                Bindings.add(nodeLink.startXProperty(), 100)
        );

        nodeLink.controlX2Property().bind(
                Bindings.add(nodeLink.endXProperty(), -100)
        );

        nodeLink.controlY1Property().bind(
                Bindings.add(nodeLink.startYProperty(), 0)
        );

        nodeLink.controlY2Property().bind(
                Bindings.add(nodeLink.endYProperty(), 0)
        );
    }

    public void setStart(Point2D startPoint) {

        nodeLink.setStartX(startPoint.getX());
        nodeLink.setStartY(startPoint.getY());
    }

    public void setEnd(Point2D endPoint) {

        nodeLink.setEndX(endPoint.getX());
        nodeLink.setEndY(endPoint.getY());
    }

}
