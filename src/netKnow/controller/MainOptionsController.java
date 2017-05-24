package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import netKnow.scene.AggregationScene;
import netKnow.scene.IPCalculatorScene;
import netKnow.scene.RoutingScene;

public class MainOptionsController {

    private Scene scene;

    @FXML
    private Button calculatorIPButton;
    @FXML
    private Button routingButton;
    @FXML
    private Button aggregationButton;

    @FXML
    void initialize(){
        calculatorIPButton.setOnAction(e -> new IPCalculatorScene(scene));
        routingButton.setOnAction(e -> new RoutingScene(scene));
        aggregationButton.setOnAction(event -> new AggregationScene(scene));
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
}
