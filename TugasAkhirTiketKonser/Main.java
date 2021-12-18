import Model.Customer_Model;
import Model.Order_Model;
import Model.Ticket_Model;


import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {
    static Order_Model orderModel;
    static Ticket_Model ticketModel;
    static Customer_Model customerModel;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    public static void main(String[] args) throws IOException {
        try {
            orderModel = new Order_Model();
            ticketModel = new Ticket_Model();
            customerModel = new Customer_Model();
            System.out.println("Koneksi Database Berhasil");
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = formatter.format(date);
            System.out.println("===== TIKET ONLINE =====");
            System.out.print("1.Registrasi \n" +
                    "2.Login \n" +
                    "Pilih menu anda: ");
            String login = input.readLine().trim();
            if (login.equals("1")){
                System.out.println("===== REGISTRASI AKUN =====");
                createCustomer();
            }
            else if (login.equals("2")){
                ResultSet viewcustomer = customerModel.getCustomer();
                System.out.println("===== LOGIN AKUN =====");
                System.out.print("Username: ");
                String username = input.readLine().trim();
                System.out.print("Password: ");
                String password = input.readLine().trim();
                String cek = "done";
                if (!viewcustomer.wasNull()) {
                    while (viewcustomer.next()) {
                        if (username.equals(viewcustomer.getString("username")) && (password.equals(viewcustomer.getString("userpassword")))) {
                            System.out.println("Selamat datang, " + viewcustomer.getString("nama"));
                            while (true) {
                                System.out.println("=== MENU ORDER TIKET ONLINE ===");
                                System.out.println("1. Lihat Tiket yang Tersedia \n" +
                                        "2. Tambah Pesanan Tiket \n" +
                                        "3. Lihat Pesanan Saya \n" +
                                        "4. Hapus Pesanan \n" +
                                        "5. Lihat Total \n" +
                                        "6. Keluar Sistem dan Cetak Nota \n" +
                                        "Pilih opsi menu: ");
                                String opsi = input.readLine().trim();
                                if (opsi.equals("6")){
                                    System.out.println("Metode Pembayaran via:");
                                    String bayar = input.readLine().trim();
                                    viewOrder();
                                    viewTotal();
                                    System.out.println("Pembayaran via: " + bayar);
                                    break;
                                }
                                else{
                                    switch (opsi) {
                                        case "1":
                                            viewTiket();
                                            break;
                                        case "2":
                                            orderModel.addOrder(viewcustomer.getInt("customerId"), tanggal);
                                            break;
                                        case "3":
                                            viewOrder();
                                            break;
                                        case "4":
                                            orderModel.delOrder();
                                            break;
                                        case "5":
                                            viewTotal();
                                            break;
                                        default:
                                            System.out.println("Menu Tidak Tersedia");
                                            break;
                                    }
                                }


                            }
                        }
                        else {
                            cek = "fail";
                        }
                    }
                }
                else {
                    System.out.println("Silahkan registrasi terlebih dahulu");
                }
                if (cek.equals("fail")){
                    System.out.println("Gagal login");
                }
                System.out.println("Program Selesai");

            }


        } catch (SQLException e) {
            System.out.println("Terjadi Kesalahan: " + e.getMessage());
        }
    }

    public static void createCustomer() throws SQLException, IOException {
        System.out.print("Nama anda: ");
        String nama = input.readLine().trim();
        System.out.print("No hp anda: ");
        String nohp = input.readLine().trim();
        System.out.print("Email anda: ");
        String email = input.readLine().trim();
        System.out.print("Username akun: ");
        String username = input.readLine().trim();
        System.out.print("Password akun: ");
        String userpassword = input.readLine().trim();

        int result = customerModel.addCustomer(nama, nohp, email, username, userpassword);
        if (result > 0) {
            System.out.println("Berhasil update " + result + " baris");
        }
    }
    public static void viewTiket () throws SQLException {
        ResultSet viewtiket = ticketModel.gettiket();
        System.out.println("IDTiket   Nama Event    Harga    Stok");
        while (viewtiket.next()) {
            System.out.printf("%-4s | %8s | %10d | %3d %n", viewtiket.getString("ticketId"),
                    viewtiket.getString("namaevent"),
                    viewtiket.getInt("harga"),
                    (viewtiket.getInt("stok")));
        }

    }
    public static void viewOrder() throws SQLException, IOException{
        ResultSet vieworder = orderModel.getOrderDetail();
        System.out.println("===== KERANJANG TIKET ANDA =====");
        System.out.println("Nomor Pesanan   ID Tiket    Jumlah    Subtotal");
        while (vieworder.next()) {
            System.out.printf("%-8s | %8s | %10d | %3d %n", vieworder.getInt("orderId"),
                    vieworder.getString("ticketId"),
                    vieworder.getInt("jumlah"),
                    vieworder.getInt("subtotal"));
        }
    }
    public static void viewTotal() throws SQLException, IOException {
        System.out.println("Nomor pesanan: ");
        int id = Integer.parseInt(input.readLine());
        ResultSet vieworder = orderModel.getOrderTotal(id);
        int count = 0;
        while(vieworder.next()){
            count += vieworder.getInt("subtotal");
        }
        System.out.println("Total keranjang adalah Rp " + count);

    }






}
