package netKnow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final static String DBURL = "jdbc:mysql://sql.slowiak.nazwa.pl:3306/slowiak";
    //private final static String DBURL = "jdbc:mysql://127.0.0.1:3306/slowiak?serverTimezone=UTC";
    private final static  String DBUSER = "slowiak";
    //private final static  String DBUSER = "root";
    private final static String DBPASS = "ZaQ1XsW2";
    private static Connection connection = null;

    public static Connection getConenction(){
        try {
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            if(connection != null){
                System.out.println("Connected to the datebase :D");
                return connection;
            }
        } catch ( SQLException e) {
            System.out.println("Problem with database");
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
