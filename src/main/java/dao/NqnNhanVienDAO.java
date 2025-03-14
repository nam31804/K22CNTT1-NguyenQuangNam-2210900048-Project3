package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import database.NqnDBConnection;
import model.NqnNguoiDung;
import model.NqnNhanVien;

public class NqnNhanVienDAO {
	public List<NqnNhanVien> getAllNhanVien() {
        List<NqnNhanVien> list = new ArrayList<>();
        Connection conn = NqnDBConnection.getConnection();
        String query = "SELECT nv.*, nd.email, nd.mat_khau FROM nhan_vien nv JOIN nguoi_dung nd ON nv.nguoi_dung_id = nd.id";
        
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NqnNhanVien nv = new NqnNhanVien(
                    rs.getInt("ma_nv"),
                    rs.getString("ho_ten"),
                    rs.getDate("ngay_sinh"),
                    rs.getString("gioi_tinh"),
                    rs.getString("phong_ban"),
                    rs.getString("chuc_vu"),
                    rs.getDouble("he_so_luong"),
                    rs.getDouble("luong_co_ban"),
                    rs.getInt("nguoi_dung_id"),
                    rs.getString("email"),
                    rs.getString("mat_khau")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean addNhanVien(NqnNhanVien nv, NqnNguoiDung nd) {
        Connection conn = NqnDBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            
            String sqlNguoiDung = "INSERT INTO nguoi_dung (email, mat_khau, vai_tro) VALUES (?, ?, ?)";
            PreparedStatement psNguoiDung = conn.prepareStatement(sqlNguoiDung, PreparedStatement.RETURN_GENERATED_KEYS);
            psNguoiDung.setString(1, nd.getEmail());
            psNguoiDung.setString(2, nd.getMatKhau());
            psNguoiDung.setString(3, "NhanVien");
            psNguoiDung.executeUpdate();
            
            ResultSet rs = psNguoiDung.getGeneratedKeys();
            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt(1);
            }
            
            String sqlNhanVien = "INSERT INTO nhan_vien (ho_ten, ngay_sinh, gioi_tinh, phong_ban, chuc_vu, he_so_luong, luong_co_ban, nguoi_dung_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psNhanVien = conn.prepareStatement(sqlNhanVien);
            psNhanVien.setString(1, nv.getHoTen());
            psNhanVien.setDate(2, new java.sql.Date(nv.getNgaySinh().getTime()));
            psNhanVien.setString(3, nv.getGioiTinh());
            psNhanVien.setString(4, nv.getPhongBan());
            psNhanVien.setString(5, nv.getChucVu());
            psNhanVien.setDouble(6, nv.getHeSoLuong());
            psNhanVien.setDouble(7, nv.getLuongCoBan());
            psNhanVien.setInt(8, userId);
            
            psNhanVien.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}