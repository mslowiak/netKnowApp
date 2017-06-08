package netKnow.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import netKnow.Class.routing.*;
import netKnow.scene.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017-05-13.
 */
public class RoutingController {

    private Scene scene;

    @FXML
    private GridPane gridPane;
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
    @FXML
    Button screenshotButton;


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
                        NodeLink searchedPCLink;
                        String searchedId;
                        if(nodeLinkTmp.startIDNode.equals(nodeID) || nodeLinkTmp.endIDNode.equals(nodeID)){
                            nodes.get(i).nodeLinks.add(nodeLinkTmp);
                        }
                    }
                }
            }
            new RoutingTypeScene(scene, nodes, gridPane, right_pane);
        });

        screenshotButton.setOnAction(e->{
            WritableImage image = right_pane.snapshot(new SnapshotParameters(), null);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDate localDate = LocalDate.now();
            String nameOfFile = dtf.format(localDate);
            nameOfFile = nameOfFile.replace(" ", "_");
            nameOfFile = nameOfFile.replace("/", "");
            nameOfFile = nameOfFile.replace(":", "");
            File file = new File("D:\\netKnowApp\\"+ nameOfFile + ".png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        //Add one icon that will be used for the drag-drop process
        //This is added as a child to the root anchorpane so it can be visible
        //on both sides of the split pane.
        mDragOverIcon = new DragIcon();
        mDragOverIcon.setVisible(false);
        mDragOverIcon.setOpacity(0.65);
        root_pane.getChildren().add(mDragOverIcon);

        String [] labels = {"Komputer", "Router", "Switch"};
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
        setTestData();
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
                    DragIconType dragIconType = DragIconType.valueOf(container.getValue("type"));
                    DraggableNodeData draggableNodeData;

                    if(dragIconType.equals(DragIconType.routerIco)){
                        draggableNodeData = DraggableNodeRouterPopUp.display();
                    }else if(dragIconType.equals(DragIconType.pcIco)){
                        draggableNodeData = DraggableNodePCPopUp.display();
                    }else{
                        draggableNodeData = DraggableNodeSwitchPopUp.display();
                    }

                    if(draggableNodeData != null) {
                        droppedIcon.draggableNodeData = draggableNodeData;
                        if(dragIconType.equals(DragIconType.routerIco)){
                            droppedIcon.setHostLabels("."+draggableNodeData.getHost());
                        }else if(dragIconType.equals(DragIconType.pcIco)){
                            droppedIcon.setIpTop(draggableNodeData.getIp());
                        }

                        droppedIcon.setTitleBar(draggableNodeData.getName());
                        droppedIcon.setType(dragIconType);
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
                        NodeLinkData ipAddress;

                        if(source.getType().equals(DragIconType.switchIco)){
                            ipAddress = new NodeLinkData(source.draggableNodeData.getIp());
                        }else if(target.getType().equals(DragIconType.switchIco)){
                            ipAddress = new NodeLinkData(target.draggableNodeData.getIp());
                        }else{
                            ipAddress = NodeLinkPopUp.display();
                        }

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


    public void setTestData(){
        DraggableNode pc1 = new DraggableNode();
        DraggableNode pc2 = new DraggableNode();
        DraggableNode router1 = new DraggableNode();
        DraggableNode router2 = new DraggableNode();
        DraggableNode router3 = new DraggableNode();
        DraggableNode router4 = new DraggableNode();
        DraggableNode switch1 = new DraggableNode();

        pc1.setType(DragIconType.pcIco);
        pc1.draggableNodeData = new DraggableNodeData("pc1", "1.0.0.0/24");
        pc1.titleBar.setText("pc1");
        pc1.setLayoutX(98.0);
        pc1.setLayoutY(71.0);

        pc2.setType(DragIconType.pcIco);
        pc2.draggableNodeData = new DraggableNodeData("pc2", "2.0.0.0/24");
        pc2.titleBar.setText("pc2");
        pc2.setLayoutX(1040.0);
        pc2.setLayoutY(279.0);

        router1.setType(DragIconType.routerIco);
        router1.draggableNodeData = new DraggableNodeData("r1", "1");
        router1.titleBar.setText("r1");
        router1.setLayoutX(79.0);
        router1.setLayoutY(268.0);

        router2.setType(DragIconType.routerIco);
        router2.draggableNodeData = new DraggableNodeData("r2", "2");
        router2.titleBar.setText("r2");
        router2.setLayoutX(120.0);
        router2.setLayoutY(679.0);

        router3.setType(DragIconType.routerIco);
        router3.draggableNodeData = new DraggableNodeData("r3", "3");
        router3.titleBar.setText("r3");
        router3.setLayoutX(622.0);
        router3.setLayoutY(705.0);

        router4.setType(DragIconType.routerIco);
        router4.draggableNodeData = new DraggableNodeData("r4", "4");
        router4.titleBar.setText("r4");
        router4.setLayoutX(940.0);
        router4.setLayoutY(542.0);

        switch1.setType(DragIconType.switchIco);
        switch1.draggableNodeData = new DraggableNodeData("s1", "10.10.10.10/24");
        switch1.titleBar.setText("s1");
        switch1.setLayoutX(351.0);
        switch1.setLayoutY(530.0);

        NodeLink link1 = new NodeLink();
        link1.setStartAndEnd(pc1.getId(), router1.getId());
        link1.bindEnds(pc1, router1);
        NodeLinkData linkData1 = new NodeLinkData("1.1.1.1/24");
        link1.nodeLinkData = linkData1;

        NodeLink link2 = new NodeLink();
        link2.setStartAndEnd(router1.getId(), router2.getId());
        link2.bindEnds(router1, router2);
        NodeLinkData linkData2 = new NodeLinkData("12.12.12.12/24");
        link2.nodeLinkData = linkData2;

        NodeLink link3 = new NodeLink();
        link3.setStartAndEnd(router1.getId(), switch1.getId());
        link3.bindEnds(router1, switch1);
        NodeLinkData linkData3 = new NodeLinkData(switch1.draggableNodeData.getIp());
        link3.nodeLinkData = linkData3;

        NodeLink link4 = new NodeLink();
        link4.setStartAndEnd(switch1.getId(), router3.getId());
        link4.bindEnds(switch1, router3);
        NodeLinkData linkData4 = new NodeLinkData(switch1.draggableNodeData.getIp());
        link4.nodeLinkData = linkData4;

        NodeLink link5 = new NodeLink();
        link5.setStartAndEnd(router2.getId(), router3.getId());
        link5.bindEnds(router2, router3);
        NodeLinkData linkData5 = new NodeLinkData("23.23.23.23/24");
        link5.nodeLinkData = linkData5;

        NodeLink link6 = new NodeLink();
        link6.setStartAndEnd(router3.getId(), router4.getId());
        link6.bindEnds(router3, router4);
        NodeLinkData linkData6 = new NodeLinkData("34.34.34.34/24");
        link6.nodeLinkData = linkData6;

        NodeLink link7 = new NodeLink();
        link7.setStartAndEnd(router4.getId(), pc2.getId());
        link7.bindEnds(router4, pc2);
        NodeLinkData linkData7 = new NodeLinkData("2.2.2.2/24");
        link7.nodeLinkData = linkData7;

        right_pane.getChildren().addAll(link1, link2, link3, link4, link5, link6, link7);
        right_pane.getChildren().addAll(pc1, pc2, router1, router2, router3, router4, switch1);
    }
}
