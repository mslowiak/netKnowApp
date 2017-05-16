package netKnow.scene;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import netKnow.DatabaseConnection;
import netKnow.controller.LoginController;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginScene {

    private Scene scene;
    private FXMLLoader loader;
    private LoginController loginController;

    public LoginScene(Scene scene) {
        this.scene = scene;
        setScene();
        setController();
    }

    private void setScene() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/netKnow/fxml/login_scene.fxml"));

        try {
            VBox vBox = loader.load();
            setController();
            scene.setRoot(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        loginController = loader.getController();
        loginController.setScene(scene);
    }
}