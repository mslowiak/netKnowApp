package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import netKnow.DatabaseConnection;
import netKnow.controller.RegistrationController;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RegistrationScene {
    String firstName;
    String lastName;
    String nickName;
    String password;
    String passwordConfirmation;
    String email;
    String dbLogin;
    String dbPassword;
    String dbEmail;
    String encryptedPassword;

    private Scene scene;
    private FXMLLoader loader;
    private RegistrationController registrationController;

    public RegistrationScene(Scene scene){
        this.scene = scene;
        setScene();
        setController();
    }
    private void setScene(){
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/registration_scene.fxml"));

        try {
            VBox vBox = loader.load();
            scene.setRoot(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController(){
        registrationController = loader.getController();
        registrationController.setScene(scene);
    }
}
