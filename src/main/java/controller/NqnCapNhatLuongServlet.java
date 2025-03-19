package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import database.NqnDBConnection;

@WebServlet("/NqnCapNhatLuongServlet")
public class NqnCapNhatLuongServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hoTen = request.getParameter("ho_ten");
        int thang = Integer.parseInt(request.getParameter("thang"));
        int nam = Integer.parseInt(request.getParameter("nam"));
        int soNgayCong = Integer.parseInt(request.getParameter("so_ngay_cong"));
        double tongLuong = Double.parseDouble(request.getParameter("tong_luong"));

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = NqnDBConnection.getConnection();

            // Cập nhật số ngày công
            String updateChamCong = "UPDATE cham_cong SET so_ngay_cong = ? WHERE ma_nv = (SELECT ma_nv FROM nhan_vien WHERE ho_ten = ?) AND thang = ? AND nam = ?";
            ps = conn.prepareStatement(updateChamCong);
            ps.setInt(1, soNgayCong);
            ps.setString(2, hoTen);
            ps.setInt(3, thang);
            ps.setInt(4, nam);
            ps.executeUpdate();

            // Cập nhật tổng lương
            String updateLuong = "UPDATE luong SET tong_luong = ? WHERE ma_nv = (SELECT ma_nv FROM nhan_vien WHERE ho_ten = ?) AND thang = ? AND nam = ?";
            ps = conn.prepareStatement(updateLuong);
            ps.setDouble(1, tongLuong);
            ps.setString(2, hoTen);
            ps.setInt(3, thang);
            ps.setInt(4, nam);
            ps.executeUpdate();

            // Chuyển hướng về danh sách lương sau khi cập nhật
            response.sendRedirect("NqnAllLuong.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi cập nhật: " + e.getMessage());
            request.getRequestDispatcher("NqnSuaLuong.jsp").forward(request, response);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}
