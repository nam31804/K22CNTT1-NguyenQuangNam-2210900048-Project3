package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.NqnDBConnection;
import model.NqnBaoHiem;

public class NqnBaoHiemDAO {
    public List<NqnBaoHiem> getAllBaoHiem() {
        List<NqnBaoHiem> list = new ArrayList<>();
        String query = "SELECT * FROM bao_hiem";
        
        try (Connection conn = NqnDBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                list.add(new NqnBaoHiem(
                    rs.getInt("id"),
                    rs.getInt("ma_nv"),
                    rs.getString("loai_bao_hiem"),
                    rs.getDouble("muc_dong")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void updateBaoHiem(int maNv, String loaiBaoHiem, double mucDong) {
        String query = "UPDATE bao_hiem SET muc_dong = ? WHERE ma_nv = ? AND loai_bao_hiem = ?";
        try (Connection conn = NqnDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, mucDong);
            ps.setInt(2, maNv);
            ps.setString(3, loaiBaoHiem);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
