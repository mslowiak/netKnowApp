package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import netKnow.scene.LoginScene;

public class SuccessfulRegistrationController {

    private Scene scene;
    @FXML
    private Label infoLabel;

    @FXML
    private Button loginButton;


    @FXML
    void initialize(){
        loginButton.setOnAction(e ->{
            new LoginScene(scene);
        });
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
}
