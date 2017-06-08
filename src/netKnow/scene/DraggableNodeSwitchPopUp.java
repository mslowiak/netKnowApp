package netKnow.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netKnow.Class.routing.DraggableNodeData;

/**
 * Created by MQ on 2017-06-07.
 */
public class DraggableNodeSwitchPopUp {
    private static TextField nameField = new TextField();

    private static TextField octetFirstField = new TextField();
    private static TextField octetSecondField = new TextField();
    private static TextField octetThirdField = new TextField();
    private static TextField octetFourthField = new TextField();
    private static TextField maskField = new TextField();


    private static TextField hostField = new TextField();
    private static DraggableNodeData draggableNodeData;
    private static Label errorLabel;
    public static DraggableNodeData display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wprowadź dane dotyczące switch'a");
        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);

        Label dot1 = new Label(" . ");
        Label dot2 = new Label(" . ");
        Label dot3 = new Label(" . ");
        Label slash = new Label(" / ");


        Label descriptionLabel = new Label("Podaj nazwę switch'a oraz adres ip");
        descriptionLabel.setPadding(new Insets(0, 0, 20, 0));
        descriptionLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        octetFirstField.setPrefSize(55, 25);
        octetSecondField.setPrefSize(55, 25);
        octetThirdField.setPrefSize(55, 25);
        octetFourthField.setPrefSize(55, 25);
        maskField.setPrefSize(55, 25);

        Label nameLabel = new Label("Nazwa switch'a: ");
        Label hostLabel = new Label("Adres ip z maską: ");
        nameLabel.setFont(Font.font(null, FontWeight.BOLD, 14));
        hostLabel.setFont(Font.font(null, FontWeight.BOLD, 14));


        HBox ipInfo = new HBox();
        ipInfo.setAlignment(Pos.CENTER);
        ipInfo.getChildren().addAll(hostLabel, octetFirstField, dot1, octetSecondField, dot2, octetThirdField, dot3, octetFourthField, slash, maskField);

        HBox namePcInfo = new HBox();
        namePcInfo.setAlignment(Pos.CENTER);
        namePcInfo.getChildren().addAll(nameLabel, nameField);

        GridPane options = new GridPane();
        options.setAlignment(Pos.CENTER);
        options.setHgap(20);
        options.setVgap(10);
        options.addRow(0, namePcInfo);
        options.addRow(1, ipInfo);

        errorLabel = new Label();
        errorLabel.setTextFill(Paint.valueOf("#fc0000"));
        errorLabel.setPadding(new Insets(10, 10, 10, 10));

        Button applyButton = new Button("Zatwierdź");
        Button cancelButton = new Button("Anuluj");
        applyButton.setPrefSize(100, 40);
        cancelButton.setPrefSize(100, 40);
        applyButton.setStyle("-fx-font-size: 16 px");
        cancelButton.setStyle("-fx-font-size: 16 px");

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 0, 0));

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(25);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(100);
        ColumnConstraints column3 = new ColumnConstraints();
        column2.setPercentWidth(25);
        buttons.getColumnConstraints().addAll(column1, column2, column3);

        buttons.add(cancelButton, 0, 0);
        buttons.add(new Label(""), 1, 0);
        buttons.add(new Label(""), 1, 0);
        buttons.add(applyButton, 2, 0);

        layout.getChildren().addAll(descriptionLabel, options, errorLabel, buttons);

        resetTextFields();
        applyButton.setOnAction(e -> {

            if (!nameField.getText().equals("") && !octetFirstField.getText().equals("")
                    && !octetSecondField.getText().equals("") && !octetThirdField.getText().equals("")
                    && !octetFourthField.getText().equals("") && !maskField.getText().equals("")) {
                boolean isCorrectIP = checkCorrectnessOfOctetValue();
                if (isCorrectIP) {
                    String sendString = octetFirstField.getText() + "."
                            + octetSecondField.getText() + "."
                            + octetThirdField.getText() + "."
                            + octetFourthField.getText() + "/"
                            + maskField.getText();


                    draggableNodeData = new DraggableNodeData(nameField.getText(), sendString);
                    resetTextFields();
                    window.close();
                } else {
                    errorLabel.setText("Wprowadziłeś niepoprawne ip!");
                }
            } else {
                errorLabel.setText("Niektore pola sa puste!");
            }
        });

        cancelButton.setOnAction(e -> {
            draggableNodeData = null;
            window.close();

            resetTextFields();
        });

        octetFirstField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                applyButton.fire();
            }
        });

        octetSecondField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                applyButton.fire();
            }
        });

        octetThirdField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                applyButton.fire();
            }
        });

        octetFourthField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                applyButton.fire();
            }
        });

        maskField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                applyButton.fire();
            }
        });

        nameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                applyButton.fire();
            }
        });

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return draggableNodeData;
    }

    private static void resetTextFields() {
        octetFirstField.setText("");
        octetSecondField.setText("");
        octetThirdField.setText("");
        octetFourthField.setText("");
        maskField.setText("");
    }

    private static boolean checkCorrectnessOfOctetValue() {
        if ( octetFirstField.getText().matches("[0-9]+")) {
            int tmpInteger = Integer.parseInt(octetFirstField.getText());
            if (tmpInteger < 0 || tmpInteger > 255) {
                return false;
            }
        } else {
            return false;
        }

        if ( octetSecondField.getText().matches("[0-9]+")) {
            int tmpInteger = Integer.parseInt(octetSecondField.getText());
            if (tmpInteger < 0 || tmpInteger > 255) {
                return false;
            }
        }else {
            return false;
        }

        if ( octetThirdField.getText().matches("[0-9]+")) {
            int tmpInteger = Integer.parseInt(octetThirdField.getText());
            if (tmpInteger < 0 || tmpInteger > 255) {
                return false;
            }
        }else {
            return false;
        }
        if ( octetFourthField.getText().matches("[0-9]+")) {
            int tmpInteger = Integer.parseInt(octetFourthField.getText());
            if (tmpInteger < 0 || tmpInteger > 255) {
                return false;
            }
        }else {
            return false;
        }
        if ( maskField.getText().matches("[0-9]+")) {
            int tmpInteger = Integer.parseInt(maskField.getText());
            if (tmpInteger < 0 || tmpInteger > 255) {
                return false;
            }
        }else {
            return false;
        }

        return true;
    }
}
