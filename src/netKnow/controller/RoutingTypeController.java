package netKnow.controller;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import netKnow.Class.routing.DraggableNode;
import netKnow.Class.routing.NodeLink;

import java.util.List;

/**
 * Created by MQ on 2017-05-19.
 */
public class RoutingTypeController {

    private Scene scene;

    @FXML
    private ComboBox<String> typeOfDeviceChoiceBox;
    @FXML
    private ComboBox<String> typeOfRoutingChoiceBox;
    @FXML
    private Button generateCodeButton;
    @FXML
    private Button simulationButton;
    private List<DraggableNode> nodeList;


    @FXML
    void initialize(){
        generateCodeButton.setOnAction(e ->{
            System.out.println("Tu bedzie generowanie kodu!");
        });
        simulationButton.setOnAction(e -> {
            System.out.println("Tu bedzie symulowanie dzialania");
        });
        typeOfDeviceChoiceBox.setItems(FXCollections.observableArrayList("Cisco", "Juniper"));
        typeOfRoutingChoiceBox.setItems(FXCollections.observableArrayList("RIP", "EIGRP", "OSPF (1 - obszarowy)"));
        typeOfDeviceChoiceBox.getStylesheets().add(
                getClass().getResource(
                        "/netKnow/resources/combo-size.css"
                ).toExternalForm()
        );
        typeOfRoutingChoiceBox.getStylesheets().add(
                getClass().getResource(
                        "/netKnow/resources/combo-size.css"
                ).toExternalForm()
        );
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
    public void setDraggableNodesList(List<DraggableNode> nodeList){
        this.nodeList = nodeList;
    }
}
