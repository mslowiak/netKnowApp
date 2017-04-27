package netKnow.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import netKnow.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HeaderController {

    private String login;
    @FXML
    private Label userLoginLabel;
    @FXML
    private Label registrationDateLabel;
    @FXML
    private Label lastVisitDateLabel;

    public void doStartup(String login){
        this.login = login;
        setDates();
    }

    private void setDates(){
        Connection connection = DatabaseConnection.getConenction();
        try{
            Statement statement = connection.createStatement();
            String query = "SELECT login, registrationDate, lastVisitDate FROM Users" + " WHERE login='"+login+"'";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            userLoginLabel.setText(login);
            registrationDateLabel.setText(rs.getString("registrationDate"));
            lastVisitDateLabel.setText(rs.getString("lastVisitDate"));
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection();
        }
    }
}
