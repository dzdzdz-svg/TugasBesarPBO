package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer_Model extends Base_Model{
    public Customer_Model() throws SQLException {
        super();
    }
    public int addCustomer(String nama, String nohp, String email, String username, String userpassword) throws SQLException {
        String query = "INSERT INTO customer" + " (nama, nohp, email, username, userpassword) " + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nama);
        preparedStatement.setString(2, nohp);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, username);
        preparedStatement.setString(5, userpassword);

        return preparedStatement.executeUpdate();

    }
    public ResultSet getCustomer() throws SQLException {
        String query = "SELECT * from customer";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        return preparedStatement.executeQuery();
    }


}
