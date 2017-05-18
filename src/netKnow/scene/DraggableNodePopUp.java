package netKnow.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netKnow.Class.routing.DraggableNodeData;

/**
 * Created by MQ on 2017-05-18.
 */
public class DraggableNodePopUp {
    private static TextField nameField = new TextField();
    private static TextField hostField = new TextField();
    private static DraggableNodeData draggableNodeData;

    public static DraggableNodeData display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Wprowadź dane dotyczące urządzenia");
        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.setPadding(new Insets(20,20,20,20));
        layout.setAlignment(Pos.CENTER);

        Label descriptionLabel = new Label("Podaj nazwę urządzenia oraz numer hosta");
        descriptionLabel.setPadding(new Insets(0,0,20,0));
        descriptionLabel.setFont(Font.font(null, FontWeight.BOLD, 20));

        Label nameLabel = new Label("Nazwa urządzenia: ");
        Label hostLabel = new Label("Numer hosta: ");

        HBox nameHBox = new HBox();
        nameHBox.setAlignment(Pos.CENTER);
        nameHBox.setSpacing(10);
        nameHBox.getChildren().addAll(nameLabel, nameField);

        HBox hostHBox = new HBox();
        hostHBox.setAlignment(Pos.CENTER);
        hostHBox.setSpacing(10);
        hostHBox.getChildren().addAll(hostLabel, hostField);

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

        layout.getChildren().addAll(descriptionLabel, nameHBox, hostHBox, buttons);

        applyButton.setOnAction(e -> {
            if(hostField.getText() != null && nameField.getText() != null){
                draggableNodeData = new DraggableNodeData(nameField.getText(), hostField.getText());
                resetTextFields();
                window.close();
            }
        });

        cancelButton.setOnAction(e -> {
            draggableNodeData = null;
            window.close();
        });

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return draggableNodeData;
    }
    private static void resetTextFields(){
        nameField.setText("");
        hostField.setText("");
    }
}
