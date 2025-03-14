package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.NqnDBConnection;
import model.NqnNguoiDung;

public class NqnNguoiDungDAO {
    public NqnNguoiDung dangNhap(String email, String matKhau) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = NqnDBConnection.getConnection();
            String sql = "SELECT nd.id, nd.email, nd.mat_khau, nd.vai_tro, nv.ho_ten " +
                         "FROM nguoi_dung nd " +
                         "LEFT JOIN nhan_vien nv ON nd.id = nv.nguoi_dung_id " +
                         "WHERE nd.email = ? AND nd.mat_khau = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, matKhau);
            rs = ps.executeQuery();

            if (rs.next()) {
                NqnNguoiDung nguoiDung = new NqnNguoiDung();
                nguoiDung.setId(rs.getInt("id"));
                nguoiDung.setEmail(rs.getString("email"));
                nguoiDung.setMatKhau(rs.getString("mat_khau"));
                nguoiDung.setVaiTro(rs.getString("vai_tro"));
                nguoiDung.setHoTen(rs.getString("ho_ten")); // ✅ Lấy họ tên
                
                System.out.println("Đăng nhập thành công! Người dùng: " + nguoiDung.getHoTen() + " | Vai trò: " + nguoiDung.getVaiTro()); // 🛠 Debug
                return nguoiDung;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Đăng nhập thất bại! Sai email hoặc mật khẩu."); // 🛠 Debug
        return null;
    }
}
