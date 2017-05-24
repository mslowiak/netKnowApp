package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import netKnow.HeaderRoot;
import netKnow.controller.AggregationController;
import netKnow.controller.MainOptionsController;

import java.io.IOException;

/**
 * Created by Filip on 24.05.2017.
 */
public class AggregationScene {

    private Scene scene;
    private FXMLLoader loader;
    private AggregationController aggregationController;

    public AggregationScene(Scene scene) {
        this.scene = scene;
        setScene();
        setController();
    }

    private void setScene() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/network_aggregation.fxml"));

        try {
            VBox content = loader.load();
            scene.setRoot(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        aggregationController = loader.getController();
        aggregationController.setScene(scene);
    }
}

