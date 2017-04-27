package netKnow.scene;

import javafx.fxml.FXMLLoader;
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
            VBox content = loader.load();
            VBox view = new VBox();
            //VBox header = HeaderRoot.getHeader();

            //view.getChildren().addAll(header, content);


            //scene.setRoot(view);
            scene.setRoot(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        mainOptionsController = loader.getController();
        mainOptionsController.setScene(scene);
    }
}
