package netKnow.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import netKnow.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoggedInScene {

    Scene scene;
    String login;
    Label userLogin, registrationDate, lastVisitDate;

    public LoggedInScene(Scene scene, String login){
        this.scene = scene;
        this.login = login;
        setScene();
    }

    public void setScene(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(50,50,50,50));
        HBox userInfo = new HBox();
        userInfo.setAlignment(Pos.CENTER);


        userLogin = new Label();
        registrationDate = new Label();
        lastVisitDate = new Label();
        userLogin.setPadding(new Insets(0,100,0,0));
        lastVisitDate.setPadding(new Insets(0,0,0,100));

        setUserInfoHeader();

        userInfo.getChildren().addAll(userLogin, registrationDate, lastVisitDate);
        vbox.getChildren().add(userInfo);

        scene.setRoot(vbox);
    }

    public void setUserInfoHeader(){
        Connection connection = DatabaseConnection.getConenction();
        try{
            Statement statement = connection.createStatement();
            String query = "SELECT login, registrationDate, lastVisitDate FROM Users" + " WHERE login='"+login+"'";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            userLogin.setText("Witaj " + login);
            registrationDate.setText("Jesteś z nami od:  " + rs.getString("registrationDate"));
            lastVisitDate.setText("Ostatnie logowanie: " + rs.getString("lastVisitDate"));
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection();
        }
    }
}
