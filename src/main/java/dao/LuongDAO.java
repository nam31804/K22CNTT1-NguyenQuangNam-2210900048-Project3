package dao;

import database.DBConnection;
import model.Luong;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LuongDAO {
    public List<Luong> getAllLuong() {
        List<Luong> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT nv.ma_nv, nv.ho_ten, l.thang, l.nam, l.tong_luong " +
                       "FROM nhan_vien nv JOIN luong l ON nv.ma_nv = l.ma_nv";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Luong(
                    rs.getInt("ma_nv"),
                    rs.getString("ho_ten"),
                    rs.getInt("thang"),
                    rs.getInt("nam"),
                    rs.getDouble("tong_luong")
                ));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Luong> getLuongByUserId(int userId) {
        List<Luong> list = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String query = "SELECT nv.ma_nv, nv.ho_ten, l.thang, l.nam, l.tong_luong " +
                       "FROM nhan_vien nv JOIN luong l ON nv.ma_nv = l.ma_nv " +
                       "WHERE nv.nguoi_dung_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Luong(
                    rs.getInt("ma_nv"),
                    rs.getString("ho_ten"),
                    rs.getInt("thang"),
                    rs.getInt("nam"),
                    rs.getDouble("tong_luong")
                ));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
