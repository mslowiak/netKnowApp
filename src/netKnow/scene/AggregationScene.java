package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import netKnow.controller.AggregationController;

import java.io.IOException;

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
            GridPane content = loader.load();
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

