package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.NqnDBConnection;
import model.NqnChamCong;

public class NqnChamCongDAO {
    public List<NqnChamCong> getAllChamCong() {
        List<NqnChamCong> list = new ArrayList<>();
        String query = "SELECT * FROM cham_cong";
        
        try (Connection conn = NqnDBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                list.add(new NqnChamCong(
                    rs.getInt("id"),
                    rs.getInt("ma_nv"),
                    rs.getInt("thang"),
                    rs.getInt("nam"),
                    rs.getInt("so_ngay_cong")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
