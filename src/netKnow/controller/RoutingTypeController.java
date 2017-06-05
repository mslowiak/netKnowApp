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
import netKnow.Class.routing.DragIconType;
import netKnow.Class.routing.DraggableNode;
import netKnow.Class.routing.NodeLink;
import netKnow.Class.routing.RIPInfo;

import java.util.ArrayList;
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
    private List<DraggableNode> routersList;


    @FXML
    void initialize(){
        generateCodeButton.setOnAction(e ->{
            List<DraggableNode> tmpRouterList = routersList;
            for(int i=0; i<routersList.size(); ++i){
                routersList.get(0).ripInfo = new RIPInfo(countRIPPaths(0), routersList);
                DraggableNode d = routersList.get(0);
                routersList.remove(0);
                routersList.add(d);
            }
            System.out.println("Tu bedzie generowanie kodu!");
        });
        simulationButton.setOnAction(e -> {
            for(int i=0; i<routersList.size(); ++i){
                countRIPPaths(i);
            }
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
        setRoutersList();
    }
    private void setRoutersList(){
        routersList = new ArrayList<>();
        for(int i=0; i<nodeList.size(); ++i){
            if(nodeList.get(i).getType() == DragIconType.routerIco){
                routersList.add(nodeList.get(i));
            }
        }
    }
    private void setMyOwnRoutersAndLinkers(){
        routersList = new ArrayList<>();
        System.out.println("Aktualny size to: "+routersList.size());
        routersList.add(new DraggableNode("R1")); // 0
        routersList.add(new DraggableNode("R2")); // 1
        routersList.add(new DraggableNode("R3")); // 2
        routersList.add(new DraggableNode("R4")); // 3
        routersList.add(new DraggableNode("R5")); // 4
        routersList.get(0).nodeLinks.add(new NodeLink("r1-connect1", "R1", "R2"));
        routersList.get(0).nodeLinks.add(new NodeLink("r1-connect2", "R1", "R3"));
        routersList.get(0).nodeLinks.add(new NodeLink("r1-connect3", "R1", "R4"));
        routersList.get(1).nodeLinks.add(new NodeLink("r2-connect1", "R2", "R1"));
        routersList.get(1).nodeLinks.add(new NodeLink("r2-connect2", "R2", "R4"));
        routersList.get(2).nodeLinks.add(new NodeLink("r3-connect1", "R3", "R1"));
        routersList.get(2).nodeLinks.add(new NodeLink("r3-connect2", "R3", "R4"));
        routersList.get(3).nodeLinks.add(new NodeLink("r4-connect1", "R4", "R1"));
        routersList.get(3).nodeLinks.add(new NodeLink("r4-connect2", "R4", "R2"));
        routersList.get(3).nodeLinks.add(new NodeLink("r4-connect3", "R4", "R3"));
        routersList.get(3).nodeLinks.add(new NodeLink("r4-connect4", "R4", "R5"));
        routersList.get(4).nodeLinks.add(new NodeLink("r5-connect1", "R5", "R4"));

    }
    private DraggableNode[] countRIPPaths(int v){
        //setMyOwnRoutersAndLinkers();

        int [] distance = new int[routersList.size()];
        DraggableNode [] previous = new DraggableNode[routersList.size()];

        for(int i=0; i<routersList.size(); ++i){
            distance[i] = 2000000000;
            previous[i] = null;
        }

        distance[v] = 0;
        for(int i=1; i<routersList.size(); ++i){
            for(int l=0; l<routersList.size(); ++l){
                DraggableNode routerNode = routersList.get(l);
                for(int j=0; j<routerNode.nodeLinks.size(); ++j){
                    NodeLink nodeLink = routerNode.nodeLinks.get(j);
                    String id = "";
                    if(!nodeLink.startIDNode.equals(routerNode.getId())){
                        id = nodeLink.startIDNode;
                    }else{
                        id = nodeLink.endIDNode;
                    }
                    int index = 0;
                    for(int k=0; k<routersList.size(); ++k){
                        if(routersList.get(k).getId().equals(id)){
                            index = k;
                        }
                    }

                    if(distance[index] > distance[l] + 1){
                        distance[index] = distance[l] + 1;
                        previous[index] = routersList.get(l);
                    }
                }
            }
        }

        /*for(int i=0; i<routersList.size(); ++i){
            System.out.print(i + ": " + distance[i] + " ");
            if(previous[i] != null)
                System.out.println(previous[i].getId());
            else
                System.out.println("NULL");
        }
        System.out.println("\n\n");*/

        return previous;
    }
}
