package netKnow;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import netKnow.controller.HeaderController;

public class HeaderRoot {

    private VBox header;
    private FXMLLoader loader;
    private HeaderController headerController;


    public void setHeader(String login){
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/login_scene.fxml"));
        headerController = loader.getController();
        headerController.setLogin(login);
    }

    public VBox getHeader(){
        return header;
    }
}
