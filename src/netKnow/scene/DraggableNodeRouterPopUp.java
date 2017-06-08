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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netKnow.Class.routing.DraggableNodeData;

/**
 * Created by MQ on 2017-05-18.
 */
public class DraggableNodeRouterPopUp {
    private static TextField nameField = new TextField();
    private static TextField hostField = new TextField();
    private static DraggableNodeData draggableNodeData;

    public static DraggableNodeData display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wprowadź dane dotyczące routera");
        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);

        Label descriptionLabel = new Label("Podaj nazwę routera oraz numer hosta");
        descriptionLabel.setPadding(new Insets(0, 0, 20, 0));
        descriptionLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label nameLabel = new Label("Nazwa routera: ");
        Label hostLabel = new Label("Numer hosta: ");
        nameLabel.setFont(Font.font(null, FontWeight.BOLD, 14));
        hostLabel.setFont(Font.font(null, FontWeight.BOLD, 14));

        GridPane options = new GridPane();
        options.setAlignment(Pos.CENTER);
        options.setHgap(20);
        options.setVgap(10);
        options.addRow(0, nameLabel, nameField);
        options.addRow(1, hostLabel, hostField);

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
        buttons.add(applyButton, 2, 0);
        Label errorLabel = new Label();
        errorLabel.setTextFill(Paint.valueOf("#fc0000"));
        errorLabel.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(descriptionLabel, options, errorLabel, buttons);

        applyButton.setOnAction(e -> {
            if (!hostField.getText().equals("") && !nameField.getText().equals("")) {
                if( hostField.getText().matches("[0-9]+") ) {
                    draggableNodeData = new DraggableNodeData(nameField.getText(), hostField.getText());
                    resetTextFields();
                    window.close();
                }
                errorLabel.setText("Zly numer!");
            } else {
                errorLabel.setText("Niektore pola sa puste!");
            }
        });

        cancelButton.setOnAction(e -> {
            draggableNodeData = null;
            resetTextFields();
            window.close();
        });

        hostField.setOnKeyPressed(e -> {
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
        nameField.setText("");
        hostField.setText("");
    }
}
