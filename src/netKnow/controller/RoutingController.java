package netKnow.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import netKnow.Class.routing.NodeLinkData;
import netKnow.Class.routing.*;
import netKnow.scene.DraggableNodePopUp;
import netKnow.scene.MainOptionsScene;
import netKnow.scene.NodeLinkPopUp;
import netKnow.scene.RoutingTypeScene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017-05-13.
 */
public class RoutingController {

    private Scene scene;

    @FXML
    SplitPane base_pane;
    @FXML
    AnchorPane right_pane;
    @FXML
    VBox left_pane;
    @FXML
    AnchorPane root_pane;

    @FXML
    Button goBackButton;
    @FXML
    Button routingTypeButton;


    private DragIcon mDragOverIcon = null;

    private EventHandler<DragEvent> mIconDragOverRoot = null;
    private EventHandler<DragEvent> mIconDragDropped = null;
    private EventHandler<DragEvent> mIconDragOverRightPane = null;

    @FXML
    private void initialize() {
        goBackButton.setOnAction(e -> new MainOptionsScene(scene));

        routingTypeButton.setOnAction(e -> {
            // sciaganie nodow do listy nodow
            List<DraggableNode> nodes = new ArrayList<>();
            for(int i=0; i<right_pane.getChildren().size(); ++i){
                if(right_pane.getChildren().get(i) instanceof DraggableNode){
                    nodes.add((DraggableNode) right_pane.getChildren().get(i));
                }
            }
            // sciaganie linkerow do odpowiednich nodow
            for(int i=0; i<nodes.size(); ++i){
                String nodeID = nodes.get(i).getId();
                for(int j=0; j<right_pane.getChildren().size(); ++j){
                    if(right_pane.getChildren().get(j) instanceof NodeLink){
                        NodeLink nodeLinkTmp = (NodeLink)right_pane.getChildren().get(j);
                        if(nodeLinkTmp.startIDNode.equals(nodeID) || nodeLinkTmp.endIDNode.equals(nodeID)){
                            nodes.get(i).nodeLinks.add(nodeLinkTmp);
                        }
                    }
                }
            }
            new RoutingTypeScene(scene, nodes);

        });

        //Add one icon that will be used for the drag-drop process
        //This is added as a child to the root anchorpane so it can be visible
        //on both sides of the split pane.
        mDragOverIcon = new DragIcon();

        mDragOverIcon.setVisible(false);
        mDragOverIcon.setOpacity(0.65);
        root_pane.getChildren().add(mDragOverIcon);

        String [] labels = {"Komputer", "Router", "Switch", "Chodar"};
        //populate left pane with multiple colored icons for testing
        for (int i = 0; i < 3; i++) {

            DragIcon icn = new DragIcon();
            Label descriptionLabel = new Label(labels[i]);
            descriptionLabel.setFont(new Font("System Bold", 12));

            addDragDetection(icn);

            icn.setType(DragIconType.values()[i]);
            left_pane.getChildren().addAll(icn, descriptionLabel);
        }
        buildDragHandlers();
    }

    private void addDragDetection(DragIcon dragIcon) {

        dragIcon.setOnDragDetected(event -> {
            // odpowiada za przeciąganie z lewego panelu na prawy panel

            // set drag event handlers on their respective objects
            base_pane.setOnDragOver(mIconDragOverRoot);
            right_pane.setOnDragOver(mIconDragOverRightPane);
            right_pane.setOnDragDropped(mIconDragDropped);

            // get a reference to the clicked DragIcon object
            DragIcon icn = (DragIcon) event.getSource();

            //begin drag ops
            mDragOverIcon.setType(icn.getType());
            mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("type", mDragOverIcon.getType().toString());
            content.put(DragContainer.AddNode, container);

            mDragOverIcon.startDragAndDrop(TransferMode.ANY).setContent(content);
            mDragOverIcon.setVisible(true);
            mDragOverIcon.setMouseTransparent(true);
            event.consume();
        });
    }

    private void buildDragHandlers() {

        //drag over transition to move widget form left pane to right pane
        mIconDragOverRoot = event -> {

            Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

            //turn on transfer mode and track in the right-pane's context
            //if (and only if) the mouse cursor falls within the right pane's bounds.
            if (!right_pane.boundsInLocalProperty().get().contains(p)) {

                event.acceptTransferModes(TransferMode.ANY);
                mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                return;
            }

            event.consume();
        };

        mIconDragOverRightPane = event -> {
            event.acceptTransferModes(TransferMode.ANY);

            //convert the mouse coordinates to scene coordinates,
            //then convert back to coordinates that are relative to
            //the parent of mDragIcon.  Since mDragIcon is a child of the root
            //pane, coodinates must be in the root pane's coordinate system to work
            //properly.
            mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
            event.consume();
        };

        mIconDragDropped = event -> {
            System.out.println("drag dropped");

            DragContainer container = (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

            container.addData("scene_coords", new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            content.put(DragContainer.AddNode, container);

            event.getDragboard().setContent(content);
            event.setDropCompleted(true);
        };

        root_pane.setOnDragDone(event -> {
            right_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
            right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
            base_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);

            mDragOverIcon.setVisible(false);

            DragContainer container = (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

            if (container != null) {
                if (container.getValue("scene_coords") != null) {
                    DraggableNode droppedIcon = new DraggableNode();
                    DraggableNodeData draggableNodeData = DraggableNodePopUp.display();

                    if(draggableNodeData != null) {
                        droppedIcon.draggableNodeData = draggableNodeData;
                        droppedIcon.setTitleBar(draggableNodeData.getName());
                        droppedIcon.setHostLabels("."+draggableNodeData.getHost());
                        droppedIcon.setType(DragIconType.valueOf(container.getValue("type")));
                        right_pane.getChildren().add(droppedIcon);

                        Point2D cursorPoint = container.getValue("scene_coords");

                        droppedIcon.relocateToPoint(
                                new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
                        );
                    }
                }
            }

            container = (DragContainer) event.getDragboard().getContent(DragContainer.DragNode);

            if (container != null) {
                if (container.getValue("type") != null){
                    System.out.println ("Moved node " + container.getValue("type"));
                }
            }

            container = (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

            if (container != null) {

                String sourceId = container.getValue("source");
                String targetId = container.getValue("target");

                //System.out.println(sourceId + "   " + targetId);

                if (sourceId != null && targetId != null) {

                    NodeLink link = new NodeLink();
                    right_pane.getChildren().add(0,link);

                    DraggableNode source = null;
                    DraggableNode target = null;

                    for (Node n: right_pane.getChildren()) {

                        if (n.getId() == null)
                            continue;

                        if (n.getId().equals(sourceId))
                            source = (DraggableNode) n;

                        if (n.getId().equals(targetId))
                            target = (DraggableNode) n;
                    }

                    if (source != null && target != null){
                        link.setStartAndEnd(sourceId, targetId);
                        link.bindEnds(source, target);

                        NodeLinkData ipAddress = NodeLinkPopUp.display();

                        if(ipAddress != null){
                            link.infoLabel.setText(ipAddress.getAddress());
                            link.relocateLabelCoords(right_pane);
                            link.nodeLinkData = ipAddress;
                        }else{
                            right_pane.getChildren().remove(0);
                        }
                    }
                }
            }
            event.consume();
        });
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

}
