package netKnow.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import netKnow.DatabaseConnection;
import netKnow.MailSender;
import netKnow.PasswordEncrypter;
import netKnow.scene.LoginScene;

import javax.mail.MessagingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RegistrationController {

    private Scene scene;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String passwordConfirmation;
    private String email;
    private String dbLogin;
    private String dbPassword;
    private String dbEmail;
    private String encryptedPassword;

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmationField;
    @FXML
    private TextField emailField;
    @FXML
    private Button goBackButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label registrationErrorLabel;


    @FXML
    void initialize(){
        goBackButton.setOnAction(e -> new LoginScene(scene));

        registerButton.setOnAction(e ->{

            firstName = firstNameField.getText();
            lastName = lastNameField.getText();
            login = loginField.getText();
            password = passwordField.getText();
            email = emailField.getText();

            passwordConfirmation = passwordConfirmationField.getText();

            if(checkIfEmpty(firstName, lastName, login, password, passwordConfirmation, email)){
                registrationErrorLabel.setText("Nie podałeś nam wszystkich danych");
            }else{

                if(!checkIfLoginExists()){
                    if(!checkIfEmailExists()){
                        if(checkIfPasswordMatch()){
                            encryptedPassword = PasswordEncrypter.encryptPassword(password);
                            registerUser(login, encryptedPassword, firstName, lastName, email);
                            MailSender mailSender = new MailSender(email, getMessageContent());
                            try {
                                mailSender.sendMail();
                            } catch (MessagingException e1) {
                                e1.printStackTrace();
                            }
                        }else{
                            registrationErrorLabel.setText("Hasła nie pasują do siebie");
                        }
                    }else{
                        registrationErrorLabel.setText("Ten adres email już jest w bazie!");
                    }
                }else{
                    registrationErrorLabel.setText("Użytkownik o takiej nazwie już istnieje!");
                }

                DatabaseConnection.closeConnection();
            }
        });
    }

    private static void registerUser(String login, String password, String firstName, String lastName, String email){
        Connection connection = DatabaseConnection.getConenction();
        try {
            Statement statement = connection.createStatement();
            String licenseKey = generateLicenseKey();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = dtf.format(now);

            String query = "INSERT INTO `Users` (login, password, registrationDate, lastVisitDate, firstname, lastname, email, licenseKey) VALUES ('"+login+"', '"+password+"', '"+date+"', '"+date+"', '"+firstName+"', '"+lastName+"', '"+email+"', '"+licenseKey+"');";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    private static String generateLicenseKey(){
        String licenseKey = "";
        Random rndGenerator = new Random();
        int range = 122 - 48 + 1;
        for(int i=0; i<10; ++i){
            licenseKey = licenseKey + (char) (rndGenerator.nextInt(122 - 97 + 1) + 97); // from a to z
            licenseKey = licenseKey + (char) (rndGenerator.nextInt(90 - 65 + 1) + 65); // from A to Z
            licenseKey = licenseKey + (char) (rndGenerator.nextInt(57 - 48 + 1) + 48); // from 0 to 9
        }
        return licenseKey;
    }

    private boolean checkIfEmpty(String... args){
        for(String x : args){
            if(x.isEmpty()){
                return true;
            }
        }
        return false;
    }

    private String getMessageContent(){
        return "Witaj " + firstName + " " + lastName + "!\n\n" +
                "Dziękujemy za założenie konta.\nCieszymy się, że chcesz używać naszej aplikacji!\n\n" +
                "Oto Twoje dane podane podczas rejestracji: \n\n"+
                "\nLoginScene: " + login +
                "\nHasło: " + password +
                "\nAdres email: " + email;
    }

    private boolean checkIfLoginExists(){
        Connection connection = DatabaseConnection.getConenction();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT login, password FROM Users" + " WHERE login='"+login+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                dbLogin = rs.getString("login");
                dbPassword = rs.getString("password");
            }
            rs.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return login.equals(dbLogin);
    }

    private boolean checkIfEmailExists(){
        Connection connection = DatabaseConnection.getConenction();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT email FROM Users" + " WHERE email='"+email+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                dbEmail = rs.getString("email");
            }
            rs.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return email.equals(dbEmail);
    }

    private boolean checkIfPasswordMatch(){
        return passwordConfirmation.equals(password);
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
}
