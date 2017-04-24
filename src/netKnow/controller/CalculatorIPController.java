package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import netKnow.Class.IP;

public class CalculatorIPController {
    @FXML
    Button countButton;
    @FXML
    TextField octetFirst;
    @FXML
    TextField octetSecond;
    @FXML
    TextField octetThird;
    @FXML
    TextField octetFourth;
    @FXML
    TextField mask;

    IP myIP;

    @FXML
    void initialize(){
        System.out.println("initialize method");
        countButton.setOnAction(e -> {
            System.out.println("on action");
            String fullIPAdress[] = {octetFirst.getText(), octetSecond.getText(), octetThird.getText(),
                    octetFourth.getText(), mask.getText()};
            myIP = new IP(fullIPAdress);
        });
    }

    public Button getCountButton() {
        return countButton;
    }

    public TextField getOctetFirst() {
        return octetFirst;
    }

    public TextField getOctetSecond() {
        return octetSecond;
    }

    public TextField getOctetThird() {
        return octetThird;
    }

    public TextField getOctetFourth() {
        return octetFourth;
    }

    public TextField getMask() {
        return mask;
    }
}
