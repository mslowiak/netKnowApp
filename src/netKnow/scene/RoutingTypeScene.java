package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import netKnow.Class.routing.DraggableNode;
import netKnow.controller.RoutingController;
import netKnow.controller.RoutingTypeController;

import java.io.IOException;
import java.util.List;

/**
 * Created by MQ on 2017-05-19.
 */
public class RoutingTypeScene {
    private Scene scene;
    private FXMLLoader loader;
    private RoutingTypeController routingTypeController;
    private List<DraggableNode> nodelist;

    public RoutingTypeScene(Scene scene, List<DraggableNode> nodeList) {
        this.scene = scene;
        this.nodelist = nodeList;
        setScene();
        setController();
    }

    private void setScene() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/routing_type_layout.fxml"));

        try {
            VBox content = loader.load();
            scene.setRoot(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        routingTypeController = loader.getController();
        routingTypeController.setScene(scene);
    }
}
