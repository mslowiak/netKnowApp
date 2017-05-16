package netKnow.Class.routing;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by MQ on 2017-05-12.
 */
public class NodeLink extends AnchorPane{

    @FXML Line nodeLink;

    public NodeLink() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/netKnow/fxml/node_link.fxml"));

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
    private void initialize(){
    }

    public void setStart(Point2D startPoint) {

        nodeLink.setStartX(startPoint.getX());
        nodeLink.setStartY(startPoint.getY());
    }

    public void setEnd(Point2D endPoint) {

        nodeLink.setEndX(endPoint.getX());
        nodeLink.setEndY(endPoint.getY());
    }

    public void bindEnds (DraggableNode source, DraggableNode target) {
        nodeLink.startXProperty().bind(Bindings.add(source.layoutXProperty(), (source.getWidth() / 2.0)));
        nodeLink.startYProperty().bind(Bindings.add(source.layoutYProperty(), (source.getWidth() / 2.0)));

        nodeLink.endXProperty().bind(Bindings.add(target.layoutXProperty(), (target.getWidth() / 2.0)));
        nodeLink.endYProperty().bind(Bindings.add(target.layoutYProperty(), (target.getWidth() / 2.0)));

        source.registerLink(getId());
        target.registerLink(getId());
    }
}
