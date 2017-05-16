package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import netKnow.HeaderRoot;
import netKnow.controller.MainOptionsController;
import netKnow.controller.RoutingController;

import java.io.IOException;

/**
 * Created by MQ on 2017-05-13.
 */
public class RoutingScene {

    private Scene scene;
    private FXMLLoader loader;
    private RoutingController routingController;

    public RoutingScene(Scene scene) {
        this.scene = scene;
        setScene();
        setController();
    }

    private void setScene() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/routing_layout.fxml"));

        try {
            GridPane content = loader.load();
            VBox header = HeaderRoot.getHeader();

            // nie wiem czy header akurat tu potrzebny bedzie, jak tak to trzeba wrzucic w vboxa

            scene.setRoot(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        routingController = loader.getController();
        routingController.setScene(scene);
    }
}
