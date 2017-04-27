package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import netKnow.scene.IPCalculatorScene;

public class MainOptionsController {

    private Scene scene;

    @FXML
    private Button calculatorIPButton;

    @FXML
    void initialize(){
        calculatorIPButton.setOnAction(e -> {
            new IPCalculatorScene(scene);
        });
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
}
