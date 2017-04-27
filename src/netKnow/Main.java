package netKnow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import netKnow.scene.IPCalculatorScene;
import netKnow.scene.LoginScene;
import netKnow.scene.RegistrationScene;

import java.sql.Connection;

public class Main extends Application {

    private Stage window;


    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("netKnow - aplication that will change your life");
        Scene scene = new Scene(new VBox(), 1000, 800);
        window.setScene(scene);
        //new LoginScene(scene);
        new IPCalculatorScene(scene);
        //new RegistrationScene(scene);
        window.show();
        //window.setFullScreen(true);
    }


    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConenction();
        //launch(args);
    }
}
