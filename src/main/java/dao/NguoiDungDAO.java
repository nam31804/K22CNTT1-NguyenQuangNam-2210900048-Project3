package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.DBConnection;
import model.NguoiDung;

public class NguoiDungDAO {
	public NguoiDung dangNhap(String email, String matKhau) {
	    Connection conn = DBConnection.getConnection();
	    String sql = "SELECT * FROM nguoi_dung WHERE email = ? AND mat_khau = ?";
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, email);
	        ps.setString(2, matKhau);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            NguoiDung nguoiDung = new NguoiDung();
	            nguoiDung.setId(rs.getInt("id"));
	            nguoiDung.setEmail(rs.getString("email"));
	            nguoiDung.setMatKhau(rs.getString("mat_khau"));
	            nguoiDung.setVaiTro(rs.getString("vai_tro"));
	            System.out.println("Đăng nhập thành công! Vai trò: " + nguoiDung.getVaiTro()); // 🛠 Debug
	            return nguoiDung;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    System.out.println("Đăng nhập thất bại! Sai email hoặc mật khẩu."); // 🛠 Debug
	    return null;
	}
}