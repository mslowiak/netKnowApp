package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import netKnow.DatabaseConnection;
import netKnow.HeaderRoot;
import netKnow.PasswordEncrypter;
import netKnow.scene.MainOptionsScene;
import netKnow.scene.RegistrationScene;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginController {

    private Scene scene;

    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private Label logErrorLabel;
    @FXML
    private Button registerButton;

    @FXML
    void initialize(){
        loginButton.setOnAction(e ->{
            String enteredLogin = loginField.getText();
            String enteredPassword = passwordField.getText();

            if(enteredLogin.isEmpty() || enteredPassword.isEmpty()){
                logErrorLabel.setText("Wprowadz login i hasło!");
            }

            int result = loginUser(enteredLogin, enteredPassword);

            if(result == 1) {
                logErrorLabel.setText("");
                updateLastVisitDate(enteredLogin);
                HeaderRoot.setHeader(enteredLogin);
                new MainOptionsScene(scene);
            }else if(result == 2){
                logErrorLabel.setText("Wprowadziłeś złe hasło!");
            }else{
                logErrorLabel.setText("Nie ma takiego użytkownika!");
            }
        });

        registerButton.setOnAction(e -> new RegistrationScene(scene));

        passwordField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                loginButton.fire();
            }
        });
    }


    private int loginUser(String login, String password){
        Connection connection = DatabaseConnection.getConenction();
        String dbLogin = "";
        String dbPassword = "";
        try{
            Statement statement = connection.createStatement();
            String query = "SELECT login, password FROM Users" + " WHERE login='"+login+"'";
            ResultSet rs = statement.executeQuery(query);
            if(!rs.next()){
                System.out.println("!rs.next()");
                return 3;
            }else{
                rs.beforeFirst();
                while(rs.next()){
                    dbLogin = rs.getString("login");
                    dbPassword = rs.getString("password");
                }
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection();
        }

        if(login == null || login.isEmpty()){
            return 4;
        }else{
            if(login.equals(dbLogin)){
                if(PasswordEncrypter.isPasswordMatching(password, dbPassword)){
                    return 1; // login i pass matches
                }else{
                    return 2; // login good, password bad
                }
            }else{
                return 3; // bad login
            }
        }
    }

    private boolean validateLicenseKey(String userLogin){
        Connection connection = DatabaseConnection.getConenction();
        Boolean dbKey = false;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT licenseActivation FROM Users WHERE login='"+userLogin+"'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                dbKey = rs.getBoolean("licenseActivation");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection();
        }

        return dbKey;
    }

    private void activateLicense(String userLogin){
        Connection connection = DatabaseConnection.getConenction();
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Users SET licenseActivation=true WHERE login='"+userLogin+"'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection();
        }
    }

    private void updateLastVisitDate(String login){
        Connection connection = DatabaseConnection.getConenction();
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = dtf.format(now);

            Statement statement = connection.createStatement();
            String query = "UPDATE Users SET lastVisitDate='" + date + "' WHERE login='"+login+"';";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
}