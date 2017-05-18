package netKnow.Class.routing;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netKnow.Class.IPAddress;

/**
 * Created by MQ on 2017-05-18.
 */
public class NodeLinkData {

    private static TextField octetFirstField = new TextField();
    private static TextField octetSecondField = new TextField();
    private static TextField octetThirdField = new TextField();
    private static TextField octetFourthField = new TextField();
    private static TextField maskField = new TextField();
    private static ChoiceBox<String> typeOfConnectionChoiceBox;
    private static Button cancelButton;
    private static Button applyButton;
    private static IPAddress someIP;

    public static IPAddress display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wprowadź dane dotyczące połączenia");
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        typeOfConnectionChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Kabel lan", "Kabel se"));
        Label label = new Label("IP: ");
        Label dot1 = new Label(" . ");
        Label dot2 = new Label(" . ");
        Label dot3 = new Label(" . ");
        Label slash = new Label(" / ");
        Button btn = new Button("XD");
        HBox hboxhere = new HBox();
        hboxhere.getChildren().addAll(label, octetFirstField, dot1, octetSecondField, dot2, octetThirdField, dot3, octetFourthField, slash, maskField);
        vbox.getChildren().addAll(hboxhere, typeOfConnectionChoiceBox, btn);

        btn.setOnAction(e ->{
            System.out.println("dupa");
            someIP = new IPAddress(octetFirstField.getText(), octetSecondField.getText(), octetThirdField.getText(), octetFourthField.getText(), maskField.getText(), "dupka");
            window.close();
        });

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();

        return someIP;
    }
}
