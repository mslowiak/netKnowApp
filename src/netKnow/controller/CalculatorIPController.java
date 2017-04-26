package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import netKnow.Class.IP;

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


    IP myIP;

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
    }

}
