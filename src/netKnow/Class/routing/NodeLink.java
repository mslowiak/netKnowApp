package netKnow.Class.routing;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by MQ on 2017-05-12.
 */
public class NodeLink extends AnchorPane{

    @FXML Line nodeLink;
    public Label infoLabel;
    public String startIDNode;
    public String endIDNode;
    public String typeOfConnection;

    public NodeLink() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/netKnow/fxml/node_link.fxml"));
        infoLabel = new Label();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setId(UUID.randomUUID().toString());
        this.getChildren().add(infoLabel);
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

        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        double textLength = fontLoader.computeStringWidth(infoLabel.getText(), infoLabel.getFont());
        double cordX = (nodeLink.getStartX() + nodeLink.getEndX() - textLength/2) / 2 ;
        double cordY = (nodeLink.getStartY() + nodeLink.getEndY()) / 2 ;
        infoLabel.setLayoutX(cordX);
        infoLabel.setLayoutY(cordY);
    }

    public void bindEnds (DraggableNode source, DraggableNode target) {
        nodeLink.startXProperty().bind(Bindings.add(source.layoutXProperty(), (source.getWidth() / 2.0)));
        nodeLink.startYProperty().bind(Bindings.add(source.layoutYProperty(), (source.getWidth() / 2.0)));

        nodeLink.endXProperty().bind(Bindings.add(target.layoutXProperty(), (target.getWidth() / 2.0)));
        nodeLink.endYProperty().bind(Bindings.add(target.layoutYProperty(), (target.getWidth() / 2.0)));

        source.registerLink(getId());
        target.registerLink(getId());
    }

    public void setStartAndEnd(String start, String end){
        System.out.println("\n\tMyID: " + this.getId());
        System.out.println("\tStart: " + start);
        System.out.println("\tEnd: " + end);
        startIDNode = start;
        endIDNode = end;
    }

    public void relocateLabelCoords(AnchorPane right_pane){
        DraggableNode source = null;
        DraggableNode target = null;

        for(Node n: right_pane.getChildren()){
            if (n.getId() == null)
                continue;

            if (n.getId().equals(this.startIDNode)){
                source = (DraggableNode) n;
            }
            if (n.getId().equals(this.endIDNode)){
                target = (DraggableNode) n;
            }
        }
        if (source != null && target != null){
            FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
            double textLength = fontLoader.computeStringWidth(infoLabel.getText(), infoLabel.getFont());
            double cordX = (source.getLayoutX() + target.getLayoutX() - textLength/2) / 2 ;
            double cordY = (source.getLayoutY() + target.getLayoutY()) / 2 ;
            infoLabel.setLayoutX(cordX);
            infoLabel.setLayoutY(cordY);
        }
    }
}
