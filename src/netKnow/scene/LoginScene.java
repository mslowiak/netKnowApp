package netKnow.scene;

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginScene {

    Scene scene;

    public LoginScene(Scene scene){
        this.scene = scene;
        setScene();
    }

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

        Label nameLabel = new Label("LoginScene: ");
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

    public static int loginUser(String login, String password){
        Connection connection = DatabaseConnection.getConenction();
        String dbLogin = "";
        String dbPassword = "";
        try{
            Statement statement = connection.createStatement();
            String query = "SELECT login, password FROM Users" + " WHERE login='"+login+"'";
            ResultSet rs = statement.executeQuery(query);
            if(!rs.next()){
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
            System.out.println("puste");
            return 4;
        }else{
            if(login.equals(dbLogin)){
                if(isPasswordMatching(password, dbPassword)){
                    return 1; // login and pass matches
                }else{
                    return 2; // login good, password bad
                }
            }else{
                return 3; // bad login
            }
        }
    }

    public static boolean validateLicenseKey(String userLogin){
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

        if(dbKey){
            return true;
        }
        return false;
    }

    public static void activateLicense(String userLogin){
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

    public static boolean isPasswordMatching(String enteredPassword, String dbPassword){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(enteredPassword.getBytes());
            byte byteData[]  = md.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0; i<byteData.length; i++){
                stringBuffer.append(Integer.toString((byteData[i] & 0xff) +  0x100, 16).substring(1));
            }
            enteredPassword = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        if(enteredPassword.equals(dbPassword)){
            return true;
        }else{
            return false;
        }
    }

    public static void updateLastVisitDate(String login){
        Connection connection = DatabaseConnection.getConenction();
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = dtf.format(now);

            Statement statement = connection.createStatement();
            String query = "UPDATE Users SET lastVisitDate='" + date + "' WHERE login='"+login+"';";
            System.out.println("QUERY: " + query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
