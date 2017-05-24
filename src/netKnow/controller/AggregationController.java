package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import netKnow.scene.SingleIP;

public class AggregationController {
    private Scene scene;

    @FXML
    private TextField amountNetwork;

    @FXML
    private Button okButton;

    @FXML
    private ScrollPane networkScrollPane;

    @FXML
    private VBox networkListVbox;

    @FXML
    private Button backButton;

    @FXML
    private Button agregujButton;

    @FXML
    void initialize(){

        amountNetwork.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER ){
                okButton.fire();
            }
        });
        okButton.setOnAction(event -> {

            int amount=0;
            amount = Integer.parseInt(amountNetwork.getText());
            networkListVbox.getChildren().clear();
            for (int i=0; i<amount ; i++){
                SingleIP singleIP = new SingleIP(networkListVbox);
            }

        });

    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

}
