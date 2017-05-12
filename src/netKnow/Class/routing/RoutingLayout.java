package netKnow.Class.routing;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import netKnow.HeaderRoot;

import java.io.IOException;


public class RoutingLayout extends AnchorPane {

    Scene scene;

    @FXML
    SplitPane base_pane;
    @FXML
    AnchorPane right_pane;
    @FXML
    VBox left_pane;

    private DragIcon mDragOverIcon = null;

    private EventHandler<DragEvent> mIconDragOverRoot = null;
    private EventHandler<DragEvent> mIconDragDropped = null;
    private EventHandler<DragEvent> mIconDragOverRightPane = null;

    public RoutingLayout(Scene scene) {

        this.scene = scene;

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/netKnow/fxml/routing_layout.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            VBox view = new VBox();
            view.setAlignment(Pos.CENTER);

            VBox header = HeaderRoot.getHeader();
            AnchorPane content = fxmlLoader.load();

            //view.getChildren().addAll(header, content);
            view.getChildren().add(content);
            scene.setRoot(view);

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {

        //Add one icon that will be used for the drag-drop process
        //This is added as a child to the root anchorpane so it can be visible
        //on both sides of the split pane.
        mDragOverIcon = new DragIcon();

        mDragOverIcon.setVisible(false);
        mDragOverIcon.setOpacity(0.65);
        getChildren().add(mDragOverIcon);

        String [] labels = {"Komputer", "Router", "Switch", "Chodar"};
        //populate left pane with multiple colored icons for testing
        for (int i = 0; i < 4; i++) {

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

        this.setOnDragDone(event -> {

            right_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
            right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
            base_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);

            mDragOverIcon.setVisible(false);

            DragContainer container = (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);


            if (container != null) {
                if (container.getValue("scene_coords") != null) {

                    DraggableNode droppedIcon = new DraggableNode();

                    droppedIcon.setType(DragIconType.valueOf(container.getValue("type")));
                    right_pane.getChildren().add(droppedIcon);

                    Point2D cursorPoint = container.getValue("scene_coords");

                    droppedIcon.relocateToPoint(
                            new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
                    );
                }
            }

            container = (DragContainer) event.getDragboard().getContent(DragContainer.DragNode);

            if (container != null) {
                if (container.getValue("type") != null)
                    System.out.println ("Moved node " + container.getValue("type"));
            }

            event.consume();
        });
    }
}