package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import netKnow.controller.SuccessfulRegistrationController;

import java.io.IOException;

/**
 * Created by MQ on 2017-05-25.
 */
public class SuccessfulRegistrationScene {
    private Scene scene;
    private FXMLLoader loader;
    private SuccessfulRegistrationController successfulRegistrationController;

    public SuccessfulRegistrationScene(Scene scene) {
        this.scene = scene;
        setScene();
        setController();
    }

    private void setScene() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/successful_registration.fxml"));

        try {
            VBox content = loader.load();
            scene.setRoot(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        successfulRegistrationController = loader.getController();
        successfulRegistrationController.setScene(scene);
    }
}
