package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import netKnow.Class.routing.CiscoConfigurationCodeGenerator;
import netKnow.Class.routing.DraggableNode;
import netKnow.Class.routing.JuniperConfigurationCodeGenerator;
import netKnow.controller.CodeGeneratorController;
import netKnow.controller.RoutingTypeController;

import java.io.IOException;
import java.util.List;

/**
 * Created by MQ on 2017-06-06.
 */
public class CodeGeneratorScene {
    private Scene scene;
    private FXMLLoader loader;
    private CodeGeneratorController codeGeneratorController;
    private List<DraggableNode> routersList;
    private List<DraggableNode> nodeList;
    private String typeOfDevice;
    private String code;

    public CodeGeneratorScene(Scene scene, String typeOfDevice, List<DraggableNode> routersList, List<DraggableNode> nodeList){
        this.scene = scene;
        this.routersList = routersList;
        this.nodeList = nodeList;
        this.typeOfDevice = typeOfDevice;

        if(typeOfDevice.equals("Cisco")){
            CiscoConfigurationCodeGenerator cisco = new CiscoConfigurationCodeGenerator(routersList, nodeList);
            code = cisco.getConfiguration();
        }else{
            JuniperConfigurationCodeGenerator juniper = new JuniperConfigurationCodeGenerator(routersList, nodeList);
            code = juniper.getConfiguration();
        }
        setScene();
        setController();
    }

    private void setScene() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/code_generator_scene.fxml"));

        try {
            BorderPane content = loader.load();
            scene.setRoot(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        codeGeneratorController = loader.getController();
        codeGeneratorController.setScene(scene);
        codeGeneratorController.setDevicesLists(code, routersList, nodeList);
    }
}
