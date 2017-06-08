package netKnow.scene;

import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import netKnow.Class.routing.DragIconType;
import netKnow.Class.routing.DraggableNode;
import netKnow.Class.routing.NodeLink;
import netKnow.Class.routing.RIPWay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017-06-07.
 */
public class SimulationScene {
    private Scene scene;
    private GridPane gridPane;
    private List<DraggableNode> nodeList;
    private AnchorPane context;

    public SimulationScene(Scene scene, GridPane gridPane, List<DraggableNode> nodeList, AnchorPane context){
        this.scene = scene;
        this.gridPane = gridPane;
        this.nodeList = nodeList;
        this.context = context;
        setScene();
        disableMouseEvents();
    }

    public void setScene(){
        gridPane.getChildren().remove(1);
        gridPane.addRow(1, getSimulationHandler());
        scene.setRoot(gridPane);
    }

    public void disableMouseEvents(){
        for(int i=0; i<nodeList.size(); ++i){
            DraggableNode draggableNode = nodeList.get(i);
            draggableNode.titleBar.setOnDragDetected(null);
            draggableNode.leftLinkHandle.setOnDragDetected(null);
            draggableNode.rightLinkHandle.setOnDragDetected(null);
            draggableNode.mLinkHandleDragDetected = null;
            draggableNode.mLinkHandleDragDropped = null;
            draggableNode.mContextLinkDragOver = null;
            draggableNode.mContextLinkDragDropped = null;
            draggableNode.closeButton.setOnMouseClicked(null);
        }
    }

    public GridPane getSimulationHandler(){
        GridPane out = new GridPane();
        out.setAlignment(Pos.CENTER);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(15);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(10);
        ColumnConstraints column3 = new ColumnConstraints();
        column2.setPercentWidth(25);
        ColumnConstraints column4 = new ColumnConstraints();
        column2.setPercentWidth(10);
        ColumnConstraints column5 = new ColumnConstraints();
        column2.setPercentWidth(25);
        ColumnConstraints column6 = new ColumnConstraints();
        column2.setPercentWidth(15);
        out.getColumnConstraints().addAll(column1, column2, column3, column4, column5,column6);

        Button menuButton = new Button("Menu główne");
        Button simulateButton = new Button("Symuluj");

        Label fromNodeNameLabel = new Label("Od:");
        Label toNodeNameLabel = new Label("Do:");

        TextField fromNodeNameTextField = new TextField();
        TextField toNodeNameTextField = new TextField();

        out.addRow(0, menuButton, fromNodeNameLabel, fromNodeNameTextField, toNodeNameLabel, toNodeNameTextField, simulateButton);

        menuButton.setOnAction(e ->{
            new MainOptionsScene(scene);
        });

        simulateButton.setOnAction(e -> {
            String fromId = fromNodeNameTextField.getText();
            String toId = toNodeNameTextField.getText();
            DraggableNode fromNode = null;
            DraggableNode toNode = null;
            for(int i=0; i<nodeList.size(); ++i){
                DraggableNode iterNode = nodeList.get(i);
                if(iterNode.titleBar.getText().equals(fromId)){
                    fromNode = iterNode;
                }
                if(iterNode.titleBar.getText().equals(toId)){
                    toNode = iterNode;
                }
            }
            simulateRoute(fromNode, toNode);
        });
        return out;
    }

    private void simulateRoute(DraggableNode start, DraggableNode stop){
        RIPWay ripWay = null;
        Point2D stopPc = null;
        List<Double> doubleList = new ArrayList<>();
        if(start.getType().equals(DragIconType.pcIco)){
            doubleList.add(start.getLayoutX()+60);
            doubleList.add(start.getLayoutY()+70);

            NodeLink pc = start.nodePCLink.get(0);
            start = getNodeWithSameID(start.getId(), pc);
        }
        if(stop.getType().equals(DragIconType.pcIco)){
            stopPc = new Point2D(stop.getLayoutX()+60, stop.getLayoutY()+70);
            NodeLink pc = stop.nodePCLink.get(0);
            stop = getNodeWithSameID(start.getId(), pc);
        }

        Point2D startPoint = new Point2D(start.getLayoutX()+60, start.getLayoutY()+70);
        Point2D tmpPoint = null;
        doubleList.add(startPoint.getX());
        doubleList.add(startPoint.getY());

        Circle circle = new Circle(30);
        circle.setFill(Color.RED);

        Polyline polyline = new Polyline();
        polyline.getPoints().add(startPoint.getX());
        polyline.getPoints().add(startPoint.getY());

        context.getChildren().add(circle);

        for(int i=0; i<start.ripInfo.ripWayList.size(); ++i){
            if(start.ripInfo.ripWayList.get(i).getDestination().equals(stop)){
                ripWay = start.ripInfo.ripWayList.get(i);
            }
        }

        for(int i=1; i<ripWay.way.size(); ++i){
            DraggableNode tmpNode = ripWay.way.get(i);
            tmpPoint = new Point2D(tmpNode.getLayoutX()+60, tmpNode.getLayoutY()+70);
            polyline.getPoints().add(tmpPoint.getX());
            polyline.getPoints().add(tmpPoint.getY());
        }
        if(stopPc != null){
            polyline.getPoints().add(stopPc.getX());
            polyline.getPoints().add(stopPc.getY());
        }

        PathTransition transmission = new PathTransition();
        transmission.setNode(circle);
        transmission.setPath(polyline);
        transmission.setCycleCount(1);
        transmission.setDuration(Duration.seconds(10));
        transmission.play();
        transmission.setOnFinished(e ->{
            context.getChildren().remove(circle);
        });
    }

    private DraggableNode getNodeWithSameID(String id, NodeLink pc){
        DraggableNode nodeWithSameId = null;
        if(pc.startIDNode.equals(id)){
            for(int i=0; i<nodeList.size(); ++i){
                if(nodeList.get(i).getId().equals(pc.endIDNode)){
                    nodeWithSameId = nodeList.get(i);
                }
            }
        }else{
            for(int i=0; i<nodeList.size(); ++i){
                if(nodeList.get(i).getId().equals(pc.startIDNode)){
                    nodeWithSameId = nodeList.get(i);
                }
            }
        }
        return nodeWithSameId;
    }
}
