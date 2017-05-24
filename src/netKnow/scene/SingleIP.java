package netKnow.scene;

import com.sun.javafx.sg.prism.NGNode;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SingleIP extends HBox{
    HBox hBox;
    TextField tv1, tv2, tv3, tv4, tv5;
    Label dot1,dot2,dot3, backSlash;

    VBox vBox;
    public SingleIP(VBox vBox){
        hBox = new HBox();
        this.vBox = vBox;

        tv1 = new TextField();
        tv2 = new TextField();
        tv3 = new TextField();
        tv4 = new TextField();
        tv5 = new TextField();

        tv1.setPrefHeight(25);
        tv1.setPrefWidth(50);
        tv2.setPrefHeight(25);
        tv2.setPrefWidth(50);
        tv3.setPrefHeight(25);
        tv3.setPrefWidth(50);
        tv4.setPrefHeight(25);
        tv4.setPrefWidth(50);
        tv5.setPrefHeight(25);
        tv5.setPrefWidth(50);


        dot1 = new Label(".");
        dot1.setPrefHeight(25);
        dot1.setPrefWidth(10);

        dot2 = new Label(".");
        dot2.setPrefHeight(25);
        dot2.setPrefWidth(10);

        dot3 = new Label(".");
        dot3.setPrefHeight(25);
        dot3.setPrefWidth(10);

        backSlash = new Label("/");
        backSlash.setPrefHeight(25);
        backSlash.setPrefWidth(10);

        hBox.getChildren().add(tv1);
        hBox.getChildren().add(dot1);
        hBox.getChildren().add(tv2);
        hBox.getChildren().add(dot2);
        hBox.getChildren().add(tv3);
        hBox.getChildren().add(dot3);
        hBox.getChildren().add(tv4);
        hBox.getChildren().add(backSlash);
        hBox.getChildren().add(tv5);

        hBox.setSpacing(5);
        hBox.setPadding(new Insets(5,5,5,5));
        
        vBox.getChildren().add(hBox);
    }
}
