package netKnow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import netKnow.scene.LoginScene;
import netKnow.scene.RoutingScene;


public class Main extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("netKnow - aplication that will change your life");
        Scene scene = new Scene(new VBox(), 1000, 800);
        window.setScene(scene);
        //new LoginScene(scene);
        new RoutingScene(scene);
        window.show();
        //window.setFullScreen(true);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
