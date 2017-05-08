package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import netKnow.Class.routing.RoutingLayout;
import netKnow.scene.IPCalculatorScene;

public class MainOptionsController {

    private Scene scene;

    @FXML
    private Button calculatorIPButton;
    @FXML
    private Button routingButton;

    @FXML
    void initialize(){
        calculatorIPButton.setOnAction(e -> {
            new IPCalculatorScene(scene);
        });
        routingButton.setOnAction(e ->{
            new RoutingLayout(scene);
        });
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
}
