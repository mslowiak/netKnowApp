package netKnow.controller;

/**
 * Created by MQ on 2017-06-06.
 */
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import netKnow.Class.routing.CiscoConfigurationCodeGenerator;
import netKnow.Class.routing.DraggableNode;
import netKnow.Class.routing.JuniperConfigurationCodeGenerator;
import netKnow.scene.MainOptionsScene;

import java.util.List;

public class CodeGeneratorController {

    private Scene scene;
    @FXML
    private TextArea textAreaField;
    @FXML
    private GridPane buttons;
    @FXML
    private Button menuButton;
    @FXML
    private Button jakisButton;
    List<DraggableNode> routersList;
    List<DraggableNode> nodeList;
    String code;
    String typeOfDevice;


    @FXML
    void initialize(){
        menuButton.setOnAction(e-> new MainOptionsScene(scene));
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void setDevicesLists(String code, List<DraggableNode> routersList, List<DraggableNode> nodeList){
        this.routersList = routersList;
        this.nodeList = nodeList;
        textAreaField.setText(code);
    }
}
