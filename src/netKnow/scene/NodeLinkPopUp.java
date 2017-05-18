package netKnow.scene;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netKnow.Class.routing.NodeLinkData;

/**
 * Created by MQ on 2017-05-18.
 */
public class NodeLinkPopUp {

    private static TextField octetFirstField = new TextField();
    private static TextField octetSecondField = new TextField();
    private static TextField octetThirdField = new TextField();
    private static TextField octetFourthField = new TextField();
    private static TextField maskField = new TextField();
    private static ChoiceBox<String> typeOfConnectionChoiceBox;
    private static NodeLinkData someIP;

    public static NodeLinkData display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wprowadź dane dotyczące połączenia");
        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.setPadding(new Insets(20,20,20,20));
        layout.setAlignment(Pos.CENTER);

        octetFirstField.setPrefSize(55, 25);
        octetSecondField.setPrefSize(55, 25);
        octetThirdField.setPrefSize(55, 25);
        octetFourthField.setPrefSize(55, 25);
        maskField.setPrefSize(55, 25);

        typeOfConnectionChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Kabel lan", "Kabel se"));
        Label descriptionLabel = new Label("Podaj adres ip oraz rodzaj zadanego połączenia");
        descriptionLabel.setPadding(new Insets(0,0,20,0));
        descriptionLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
        Label cableTypeLabel = new Label("Rodzaj połączenia: ");
        cableTypeLabel.setFont(Font.font(null, FontWeight.BOLD, 14));
        Label label = new Label("IP: ");
        label.setPadding(new Insets(0,10,0,0));
        label.setFont(Font.font(null, FontWeight.BOLD, 14));
        Label dot1 = new Label(" . ");
        Label dot2 = new Label(" . ");
        Label dot3 = new Label(" . ");
        Label slash = new Label(" / ");

        Button applyButton = new Button("Zatwierdź");
        Button cancelButton = new Button("Anuluj");
        applyButton.setPrefSize(100, 40);
        cancelButton.setPrefSize(100, 40);
        applyButton.setStyle("-fx-font-size: 16 px");
        cancelButton.setStyle("-fx-font-size: 16 px");

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20,0,0,0));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(25);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(100);
        ColumnConstraints column3 = new ColumnConstraints();
        column2.setPercentWidth(25);
        buttons.getColumnConstraints().addAll(column1, column2, column3);

        buttons.add(cancelButton, 0, 0);
        buttons.add(new Label(""), 1, 0);
        buttons.add(applyButton, 2, 0);

        HBox ipInfo = new HBox();
        ipInfo.setAlignment(Pos.CENTER);
        ipInfo.getChildren().addAll(label, octetFirstField, dot1, octetSecondField, dot2, octetThirdField, dot3, octetFourthField, slash, maskField);

        HBox cableType = new HBox();
        cableType.setAlignment(Pos.CENTER);
        cableType.setSpacing(10);
        cableType.getChildren().addAll(cableTypeLabel, typeOfConnectionChoiceBox);

        layout.getChildren().addAll(descriptionLabel, ipInfo, cableType, buttons);

        applyButton.setOnAction(e ->{
            someIP = new NodeLinkData(octetFirstField.getText(), octetSecondField.getText(), octetThirdField.getText(), octetFourthField.getText(), maskField.getText(), typeOfConnectionChoiceBox.getValue());
            window.close();
        });

        cancelButton.setOnAction(e ->{
            someIP = null;
            window.close();
        });

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return someIP;
    }
}
