package netKnow;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import netKnow.controller.HeaderController;

public class HeaderRoot {

    private static VBox header;
    private static FXMLLoader loader;
    private static HeaderController headerController;


    public static void setHeader(String login){
        loader = new FXMLLoader();
        loader.setLocation(HeaderRoot.class.getResource("/netKnow/fxml/user_header.fxml"));
        System.out.println("GOOD 1");
        headerController = loader.getController();
        System.out.println("GOOD 2");
        headerController.doStartup(login);
        System.out.println("GOOD 3");
    }

    public static VBox getHeader(){
        return header;
    }
}
