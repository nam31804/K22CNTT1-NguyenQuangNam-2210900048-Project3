package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.NqnDBConnection;
import model.NqnPhuCap;

public class NqnPhuCapDAO {
    public List<NqnPhuCap> getAllPhuCap() {
        List<NqnPhuCap> list = new ArrayList<>();
        Connection conn = NqnDBConnection.getConnection();
        String query = "SELECT * FROM phu_cap";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new NqnPhuCap(
                    rs.getInt("id"),
                    rs.getInt("ma_nv"),
                    rs.getString("loai_phu_cap"),
                    rs.getDouble("so_tien")
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
