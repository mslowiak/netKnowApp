package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import netKnow.Class.IP;
import netKnow.scene.MainOptionsScene;

public class CalculatorIPController {

    @FXML
    private TextField octetFirst;
    @FXML
    private TextField octetSecond;
    @FXML
    private TextField octetThird;
    @FXML
    private TextField octetFourth;
    @FXML
    private TextField mask;
    @FXML
    private Button countButton;
    @FXML
    private TextArea networkField;
    @FXML
    private TextArea broadcastField;
    @FXML
    private TextArea numberOfHostsField;
    @FXML
    private TextArea minHostField;
    @FXML
    private TextArea maxHostField;
    @FXML
    private Button goBackButton;

    private Scene scene;
    private IP myIP;

    @FXML
    void initialize(){
        countButton.setOnAction(e -> {
            String fullIPAdress[] = {octetFirst.getText(), octetSecond.getText(), octetThird.getText(),
                    octetFourth.getText(), mask.getText()};
            myIP = new IP(fullIPAdress);
            myIP.computeData();
            networkField.setText(myIP.getNetwork());
            broadcastField.setText(myIP.getBroadcast());
            numberOfHostsField.setText(myIP.getNumberOfHosts());
            minHostField.setText(myIP.getMinHost());
            maxHostField.setText(myIP.getMaxHost());
        });

        mask.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                countButton.fire();
            }
        });
        goBackButton.setOnAction(e -> {
            new MainOptionsScene(scene);
        });
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
}
