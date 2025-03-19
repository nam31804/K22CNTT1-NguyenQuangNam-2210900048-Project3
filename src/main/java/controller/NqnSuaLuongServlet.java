package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.NqnDBConnection;

@WebServlet("/NqnSuaLuongServlet")
public class NqnSuaLuongServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hoTen = request.getParameter("ho_ten");
        int thang = Integer.parseInt(request.getParameter("thang"));
        int nam = Integer.parseInt(request.getParameter("nam"));

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = NqnDBConnection.getConnection();
            String query = "SELECT nv.luong_co_ban, cc.so_ngay_cong " +
                           "FROM nhan_vien nv " +
                           "LEFT JOIN cham_cong cc ON nv.ma_nv = cc.ma_nv AND cc.thang = ? AND cc.nam = ? " +
                           "WHERE nv.ho_ten = ?";

            ps = conn.prepareStatement(query);
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ps.setString(3, hoTen);
            rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("hoTen", hoTen);
                request.setAttribute("thang", thang);
                request.setAttribute("nam", nam);
                request.setAttribute("luongCoBan", rs.getDouble("luong_co_ban"));

                
                request.setAttribute("soNgayCong", rs.getObject("so_ngay_cong") != null ? rs.getInt("so_ngay_cong") : 0);

                request.getRequestDispatcher("NqnSuaLuong.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tải dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("NqnSuaLuong.jsp").forward(request, response);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}
