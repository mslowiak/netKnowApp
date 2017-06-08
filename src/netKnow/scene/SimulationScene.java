package netKnow.scene;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import netKnow.Class.routing.DraggableNode;
import netKnow.Class.routing.RIPWay;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Created by MQ on 2017-06-07.
 */
public class SimulationScene {
    private Scene scene;
    private GridPane gridPane;
    private List<DraggableNode> nodeList;
    private AnchorPane context;
/*
    TimerTask timerTask;
    Timer timer;

    final Handler handler = new Handler();
    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, INTERVAL_HAPPENED* 1000, 1000);
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTaskHappened() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                    }
                });
            }
        };
    }
*/
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
            //draggableNode.closeButton.setOnMouseClicked(null);
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
        Point2D pointOne = new Point2D(start.getLayoutX()+60, start.getLayoutY()+70);
        Point2D pointTwo = new Point2D(stop.getLayoutX()+60, stop.getLayoutY()+70);
        Circle circle = new Circle(pointOne.getX(), pointOne.getY(), 10);
        circle.setFill(Color.RED);

        context.getChildren().add(circle);
        double x1 = pointOne.getX();
        double x2 = pointTwo.getX();
        double y1 = pointOne.getY();
        double y2 = pointTwo.getY();
        System.out.println("x1: " + x1 +"\tx2: "+x2+"\ty1: "+y1+"\ty2: "+y2);

        if(x1<x2 && y1>y2){

            double t = x2-x1;
            double z = y2-y1;
            double distance = Math.sqrt(t*t+z*z);
            double interval = 10.0;
            double range = 10.0;
            double alpha = Math.toDegrees(Math.atan(z/t));

            while(distance-interval>range){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double newx = x1 + range*Math.cos(alpha);
                double newy = y1 - range*Math.sin(alpha);

                circle.setTranslateX(newx);
                circle.setTranslateY(newy);

                //System.out.println(newx + " " + newy);
                range+=interval;
            }
        }

        /*
        for(int i=0; i<start.ripInfo.ripWayList.size(); ++i){
            if(start.ripInfo.ripWayList.get(i).getDestination().equals(stop)){
                ripWay = start.ripInfo.ripWayList.get(i);
            }
        }

        for(int i=0; i+1<ripWay.way.size(); ++i){
            DraggableNode firstNode = ripWay.way.get(i);
            DraggableNode secondNode = ripWay.way.get(i+1);
            //Point2D pointOne = firstNode.localToScene(0.0, 0.0);
            //Point2D pointTwo = secondNode.localToScene(0.0, 0.0);
        }
        */
    }

}
