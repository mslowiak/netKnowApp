package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import netKnow.controller.CalculatorIPController;

import java.io.IOException;

public class IPCalculatorScene {

    private Scene scene;
    private FXMLLoader loader;
    private CalculatorIPController calculatorIPController;

    public IPCalculatorScene(Scene scene){
        this.scene = scene;
        setScene();
        setController();
    }

    private void setScene(){
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/calculator_ip.fxml"));

        try {
            StackPane stackPane = loader.load();
            scene.setRoot(stackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController(){
        System.out.println("setController");
        calculatorIPController = loader.getController();
    }

    private void doSomething(){

    }
}
