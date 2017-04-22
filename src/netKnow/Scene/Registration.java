package netKnow.Scene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import netKnow.DatabaseConnection;
import netKnow.MailSender;

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

public class Registration {
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

    Scene scene;

    public Registration(Scene scene){
        this.scene = scene;
        setScene();
    }

    public void setScene(){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        GridPane registration = new GridPane();
        registration.setAlignment(Pos.CENTER);
        registration.setVgap(10);
        registration.setHgap(10);

        Label registrationDescription = new Label("Rejestracja nowego użytkownika");
        registrationDescription.setFont(Font.font(null, FontWeight.BOLD, 24));
        registrationDescription.setPadding(new Insets(10,10,50,10));

        int labelFontSize = 16;

        Label firstNameLabel = new Label("Imię");
        firstNameLabel.setFont(Font.font(null, FontWeight.BOLD, labelFontSize));
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("Imię");

        registration.addRow(0, firstNameLabel, firstNameInput);

        Label lastNameLabel = new Label("Nazwisko");
        lastNameLabel.setFont(Font.font(null, FontWeight.BOLD, labelFontSize));
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Nazwisko");

        registration.addRow(1, lastNameLabel, lastNameInput);

        Label nickNameLabel = new Label("Twój login");
        nickNameLabel.setFont(Font.font(null, FontWeight.BOLD, labelFontSize));
        TextField nickNameInput = new TextField();
        nickNameInput.setPromptText("Login");

        registration.addRow(2, nickNameLabel, nickNameInput);

        Label passwordLabel = new Label("Twoje hasło");
        passwordLabel.setFont(Font.font(null, FontWeight.BOLD, labelFontSize));
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Hasło");

        registration.addRow(3, passwordLabel, passwordInput);

        Label confirmPasswordLabel = new Label("Potwierdź hasło");
        confirmPasswordLabel.setFont(Font.font(null, FontWeight.BOLD, labelFontSize));
        PasswordField confirmPasswordInput = new PasswordField();
        confirmPasswordInput.setPromptText("Potwierdź hasło");

        registration.addRow(4, confirmPasswordLabel, confirmPasswordInput);

        Label emailLabel = new Label("Podaj email");
        emailLabel.setFont(Font.font(null, FontWeight.BOLD, labelFontSize));
        TextField emailInput = new TextField();
        emailInput.setPromptText("Email");

        registration.addRow(5, emailLabel, emailInput);

        Button goBackButton = new Button("Wróć");
        Button registerButton = new Button("Zarejestruj!");
        GridPane.setHalignment(registerButton, HPos.RIGHT);

        Label registrationError = new Label();
        registrationError.setTextFill(Color.web("#f21027"));
        registration.addRow(6, registrationError);

        goBackButton.setOnAction(e ->{
            new Login(scene);
        });

        registerButton.setOnAction(e ->{

            firstName = firstNameInput.getText();
            lastName = lastNameInput.getText();
            nickName = nickNameInput.getText();
            password = passwordInput.getText();
            email = emailInput.getText();

            passwordConfirmation = confirmPasswordInput.getText();

            if(checkIfEmpty(firstName, lastName, nickName, password, passwordConfirmation, email)){
                registrationError.setText("Nie podałeś nam wszystkich danych");
            }else{

                if(!checkIfLoginExists()){
                    if(!checkIfEmailExists()){
                        if(checkIfPasswordMatch()){
                            System.out.println("Przed kodowaniem: " + password);
                            encryptedPassword = encryptPassword(password);
                            System.out.println("Po  zakodowaniu: " + encryptedPassword);
                            registerUser(nickName, encryptedPassword, firstName, lastName, email);
                            MailSender mailSender = new MailSender(email, getMessageContent());
                            try {
                                mailSender.sendMail();
                            } catch (MessagingException e1) {
                                e1.printStackTrace();
                            }
                        }else{
                            registrationError.setText("Hasła nie pasują do siebie");
                        }
                    }else{
                        registrationError.setText("Ten adres email już jest w bazie!");
                    }
                }else{
                    registrationError.setText("Użytkownik o takiej nazwie już istnieje!");
                }

                DatabaseConnection.closeConnection();
            }
        });

        registration.addRow(8, goBackButton, registerButton);

        vbox.getChildren().addAll(registrationDescription, registration);
        scene.setRoot(vbox);
        //window.setScene(new Scene(vbox, 600, 600));
        //window.setFullScreen(true);
    }
    public static void registerUser(String login, String password, String firstName, String lastName, String email){
        Connection connection = DatabaseConnection.getConenction();
        try {
            Statement statement = connection.createStatement();
            String licenseKey = generateLicenseKey();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = dtf.format(now);

            String query = "INSERT INTO `Users` (login, password, registrationDate, lastVisitDate, firstname, lastname, email, licenseKey) VALUES ('"+login+"', '"+password+"', '"+date+"', '"+date+"', '"+firstName+"', '"+lastName+"', '"+email+"', '"+licenseKey+"');";
            System.out.println(query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }
    }

    public static String generateLicenseKey(){
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

    public boolean checkIfEmpty(String... args){
        for(String x : args){
            if(x.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public String getMessageContent(){
        return "Witaj " + firstName + " " + lastName + "!\n\n" +
                "Dziękujemy za założenie konta.\nCieszymy się, że chcesz używać naszej aplikacji!\n\n" +
                "Oto Twoje dane podane podczas rejestracji: \n\n"+
                "\nLogin: " + nickName +
                "\nHasło: " + password +
                "\nAdres email: " + email;
    }

    public boolean checkIfLoginExists(){
        Connection connection = DatabaseConnection.getConenction();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT login, password FROM Users" + " WHERE login='"+nickName+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                dbLogin = rs.getString("login");
                dbPassword = rs.getString("password");
            }
            rs.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        if(nickName.equals(dbLogin)){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkIfEmailExists(){
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

        if(email.equals(dbEmail)){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkIfPasswordMatch(){
        return passwordConfirmation.equals(password);
    }

    public String encryptPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte byteData[]  = md.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0; i<byteData.length; i++){
                stringBuffer.append(Integer.toString((byteData[i] & 0xff) +  0x100, 16).substring(1));
            }
            password = stringBuffer.toString();

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }
}
