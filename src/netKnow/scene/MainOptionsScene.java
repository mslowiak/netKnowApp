package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import netKnow.HeaderRoot;
import netKnow.controller.MainOptionsController;

import java.io.IOException;

public class MainOptionsScene {
    private Scene scene;
    private FXMLLoader loader;
    private MainOptionsController mainOptionsController;

    public MainOptionsScene(Scene scene) {
        this.scene = scene;
        setScene();
        setController();
    }

    private void setScene() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/main_options_scene.fxml"));

        try {
            VBox view = new VBox();
            view.setAlignment(Pos.CENTER);
            VBox content = loader.load();
            VBox header = HeaderRoot.getHeader();

            view.getChildren().addAll(header, content);

            scene.setRoot(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        mainOptionsController = loader.getController();
        mainOptionsController.setScene(scene);
    }
}
