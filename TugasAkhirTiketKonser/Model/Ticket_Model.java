package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ticket_Model extends Base_Model{
    public Ticket_Model() throws SQLException{
            super();
    }
    public ResultSet gettiket() throws SQLException {
        String query = "SELECT * from detailtiket";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        return preparedStatement.executeQuery();
    }



}
