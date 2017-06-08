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
import netKnow.Class.IP;
import netKnow.Class.routing.DraggableNodeData;

import java.io.IOException;

/**
 * Created by MQ on 2017-05-18.
 */
public class AggregationPopUp {

    private static IP[] ipNumbers;

    public static void setIpNumbers(IP[] ipNumbers) {
        //System.out.println(ipNumbers.length);
        AggregationPopUp.ipNumbers = ipNumbers;
    }

    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        try{
            window.setTitle("Twoja zaagregowana siec to:");

            for (int i = 0; i < ipNumbers.length; i++) {
                if (ipNumbers[i] != null) {
                    Label label = new Label(ipNumbers[i].getIP() + "");
                    layout.getChildren().add(label);
                }
            }
        }
        catch(Exception e){
            window.setTitle("Nieudana agregacja");
            Label label = new Label("Nie udało się zagregować");
            layout.getChildren().add(label);
        }
        Button cancelButton = new Button("Anuluj");
        cancelButton.setPrefSize(100, 40);
        cancelButton.setStyle("-fx-font-size: 16 px");

        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 0, 0));

        buttons.add(cancelButton, 1, 0);
        buttons.add(new Label(""), 1, 0);

        layout.getChildren().addAll(buttons);

        cancelButton.setOnAction(e -> {
            window.close();
        });

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
