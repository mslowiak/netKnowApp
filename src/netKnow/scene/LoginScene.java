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
            scene.setRoot(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setController() {
        loginController = loader.getController();
        loginController.setScene(scene);
    }
/*
    public void setScene(){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        GridPane loginPanel = new GridPane();
        loginPanel.setAlignment(Pos.CENTER);
        loginPanel.setVgap(10);
        loginPanel.setHgap(10);

        Label loginLabel = new Label("Logowanie do aplikacji");
        loginLabel.setAlignment(Pos.CENTER);
        loginLabel.setFont(Font.font(null, FontWeight.BOLD, 24));
        loginLabel.setPadding(new Insets(10,10,50,10));

        Label nameLabel = new Label("Login: ");
        nameLabel.setFont(Font.font(null, FontWeight.BOLD, 12));
        TextField nameInput = new TextField();
        nameInput.setPromptText("login");
        GridPane.setConstraints(nameLabel, 0,0);
        GridPane.setConstraints(nameInput, 1,0);

        Label passLabel = new Label("Password: ");
        passLabel.setFont(Font.font(null, FontWeight.BOLD, 12));
        PasswordField passInput = new PasswordField();
        passInput.setPromptText("passsword");
        GridPane.setConstraints(passLabel, 0,1);
        GridPane.setConstraints(passInput, 1,1);

        Button loginButton = new Button("Zaloguj się");
        GridPane.setConstraints(loginButton, 1,2);
        GridPane.setHalignment(loginButton, HPos.RIGHT);

        Label logError = new Label();
        logError.setTextFill(Color.web("#f21027"));
        GridPane.setConstraints(logError, 0, 3, 3, 1);
        GridPane.setHalignment(logError, HPos.CENTER);

        Label registerLabel = new Label("Nie masz konta? ");
        Button registerButton = new Button("Zarejestruj się");
        GridPane.setConstraints(registerLabel, 0,5);
        GridPane.setConstraints(registerButton, 1, 5);
        GridPane.setHalignment(registerButton, HPos.RIGHT);

        loginPanel.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, logError, registerLabel, registerButton);
        vbox.getChildren().addAll(loginLabel, loginPanel);


        loginButton.setOnAction(e -> {
            String enteredName = nameInput.getText();
            String enteredPassword = passInput.getText();
            System.out.println(enteredName + "  " + enteredPassword);
            if(enteredName.isEmpty() || enteredPassword.isEmpty()){
                System.out.println("Result 0");
                logError.setText("Wprowadź login i hasło!");
            }
            int result = loginUser(enteredName, enteredPassword);
            if (result == 1){
                logError.setText("");
                updateLastVisitDate(enteredName);
                new LoggedInScene(scene, enteredName);
            }else if(result == 2){
                logError.setText("Wprowadziłeś złe hasło!");
            }else{
                logError.setText("Nie ma takiego użytkownika!");
            }
        });

        registerButton.setOnAction(e -> {
            new RegistrationScene(scene);
        });

        passInput.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                loginButton.fire();
            }
        });

        //window.setScene(new scene(vbox, 600, 275));
        scene.setRoot(vbox);
    }
*/
}