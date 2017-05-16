package netKnow;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import netKnow.controller.HeaderController;

import java.io.IOException;

public class HeaderRoot {

    private static VBox header;
    private static FXMLLoader loader;
    private static HeaderController headerController;
    private static String login;


    public static void setHeader(String x){
        login = x;
        loader = new FXMLLoader();
        loader.setLocation(HeaderRoot.class.getResource("/netKnow/fxml/user_header.fxml"));
        try {
            header = loader.load();
            setController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setController(){
        headerController = loader.getController();
        headerController.doStartup(login);
    }

    public static VBox getHeader(){
        return header;
    }
}
