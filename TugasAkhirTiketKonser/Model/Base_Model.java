package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Base_Model {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    public Connection connection;

    public Base_Model(){
        dbUrl = "jdbc:postgresql://localhost/EventTicket";
        dbUser = "TiketKonser";
        dbPassword = "sistemtiket";

        try{
            connection = DriverManager.getConnection(dbUrl,dbUser, dbPassword);
        }
        catch (SQLException e){
            System.out.println("Terdapat kesalahan: " + e.getMessage());
        }
    }
}
