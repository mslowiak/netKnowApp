package netKnow.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import netKnow.Class.IP;
import netKnow.Code.NetworkAggregation;
import netKnow.scene.AggregationPopUp;
import netKnow.scene.MainOptionsScene;
import netKnow.scene.SingleIP;

import java.util.ArrayList;
import java.util.List;

public class
AggregationController {
    private Scene scene;

    @FXML
    private TextField amountNetwork;
    @FXML
    private Button okButton;
    @FXML
    private VBox networkListVbox;
    @FXML
    private Button backButton;
    @FXML
    private Button aggregateButton;
    @FXML
    private Label errorLabel;

    List<SingleIP> arrayListSingleIp;
    int amount=0;
    boolean clickedAggregation = false;

    public IP[] aggregatedNetwork;

    @FXML
    void initialize(){
        amountNetwork.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER ){
                okButton.fire();
            }
        });
        okButton.setOnAction(event -> {

            amount = Integer.parseInt(amountNetwork.getText());

            networkListVbox.getChildren().clear();
            if(amount > 10){
                errorLabel.setText("Liczba sieci do agregacji nie może być > 10");
            }else if(amount < 1){
                errorLabel.setText("Liczba nie może być <= 0");
            }else{
                arrayListSingleIp = new ArrayList<>();
                for (int i=0; i<amount ; i++){
                    arrayListSingleIp.add(new SingleIP(networkListVbox));
                }
                clickedAggregation = true;
            }
        });

        aggregateButton.setOnAction(event -> {

            if( clickedAggregation ){

                IP [] IPArray = new IP[amount];
                for (int i=0; i<amount ; i++){


                    String ipString[] = {
                            arrayListSingleIp.get(i).tv1.getText(),
                            arrayListSingleIp.get(i).tv2.getText(),
                            arrayListSingleIp.get(i).tv3.getText(),
                            arrayListSingleIp.get(i).tv4.getText(),
                            arrayListSingleIp.get(i).tv5.getText()

                    };

                    IPArray[i] = new IP(ipString);
                }
                NetworkAggregation networkAggregation = new NetworkAggregation(IPArray);
                aggregatedNetwork = networkAggregation.aggregateNetwork();

                AggregationPopUp.setIpNumbers(aggregatedNetwork);
                AggregationPopUp.display();

            }

        });

        backButton.setOnAction(event -> {
            new MainOptionsScene(scene);

        });

    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

}
