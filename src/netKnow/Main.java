package netKnow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import netKnow.Class.routing.RoutingLayout;
import netKnow.scene.LoginScene;

public class Main extends Application {

    private Stage window;

    ///*
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("netKnow - aplication that will change your life");
        Scene scene = new Scene(new VBox(), 1000, 800);
        window.setScene(scene);
        new LoginScene(scene);
        window.show();
        window.setFullScreen(true);
    }


    public static void main(String[] args) {
        //Connection connection = DatabaseConnection.getConenction();
        launch(args);

    }
    //*/
    /*
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        try {

            Scene scene = new Scene(root,640,480);
            scene.getStylesheets().add(getClass().getResource("/netKnow/resources/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

        root.setCenter(new RoutingLayout());
    }

    public static void main(String[] args) {
        launch(args);
    }
    */
}
