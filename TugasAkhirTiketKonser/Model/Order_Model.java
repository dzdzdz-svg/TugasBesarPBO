package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order_Model extends Base_Model{
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);
    public Order_Model() throws SQLException {
        super();
    }
    public void addOrder(int customerId, String tanggal) throws SQLException, IOException {
        String query = "INSERT INTO customerOrder" + " (orderId, customerId, ticketId, jumlah, tanggal) " + "VALUES (?, ?, ?, ?, ?::date );" +
                "UPDATE ticket " + "SET stok = stok - ? where ticketId=?";
        System.out.print("Masukkan nomer pesanan: ");
        int orderId = Integer.parseInt(input.readLine());
        System.out.print("ID Tiket yang ingin dibeli: ");
        String ticketId = input.readLine().trim();
        System.out.print("Jumlah tiket yang ingin dibeli: ");
        int jumlah = Integer.parseInt(input.readLine());

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, orderId);
        preparedStatement.setInt(2, customerId);
        preparedStatement.setString(3, ticketId);
        preparedStatement.setInt(4, jumlah);
        preparedStatement.setDate(5, Date.valueOf(tanggal));
        preparedStatement.setInt(6, jumlah);
        preparedStatement.setString(7, ticketId);

        preparedStatement.executeUpdate();



    }
    public void delOrder() throws SQLException, IOException{
        System.out.print("Nomor pesanan tiket: ");
        int orderId = Integer.parseInt(input.readLine());
        System.out.print("ID tiket yang akan dihapus: ");
        String id = input.readLine().trim();
        System.out.println("Jumlah tiket yang dipesan tadi: ");
        int jumlah = Integer.parseInt(input.readLine());
        String query = "DELETE from customerOrder where orderId=? AND ticketId=?;" +
                "UPDATE ticket " + "SET stok = stok + ? where ticketId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, orderId);
        preparedStatement.setString(2, id);
        preparedStatement.setInt(3, jumlah);
        preparedStatement.setString(4, id);

        preparedStatement.executeUpdate();

    }
    public ResultSet getOrderDetail() throws SQLException, IOException {
        System.out.println("Nomer pesanan yang ingin dilihat: ");
        int id = Integer.parseInt(input.readLine());
        String query = "SELECT * from detailOrder where orderId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    public ResultSet getOrderTotal(int id) throws SQLException {
        String query = "SELECT * from detailOrder where orderId=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        return preparedStatement.executeQuery();
    }




}
