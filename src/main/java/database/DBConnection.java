package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/NguyenQuangNam_K22CNTT1_2210900048";
    private static final String USER = "root"; // Thay bằng tài khoản MySQL của bạn
    private static final String PASSWORD = "nam31804"; // Nếu có mật khẩu thì điền vào

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Kết nối database
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối MySQL thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối MySQL!");
        }
        return conn;
    }

    public static void main(String[] args) {
        // Test kết nối
        getConnection();
    }
}