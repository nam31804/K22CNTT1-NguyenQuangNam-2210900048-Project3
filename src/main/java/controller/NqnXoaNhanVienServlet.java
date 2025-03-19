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
import java.sql.SQLException;
import database.NqnDBConnection;

@WebServlet("/XoaNhanVienServlet")
public class NqnXoaNhanVienServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String maNv = request.getParameter("ma_nv");

        if (maNv == null || maNv.isEmpty()) {
            response.getWriter().println("Lỗi: Không có mã nhân viên!");
            return;
        }

        Connection conn = null;
        PreparedStatement psGetUserId = null;
        PreparedStatement psDeleteLuong = null;
        PreparedStatement psDeleteBaoHiem = null;
        PreparedStatement psDeletePhuCap = null;
        PreparedStatement psDeleteChamCong = null;
        PreparedStatement psDeleteNhanVien = null;
        PreparedStatement psDeleteNguoiDung = null;

        try {
            conn = NqnDBConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            int maNvInt = Integer.parseInt(maNv);
            int nguoiDungId = -1;

            // 🔹 Lấy ID tài khoản trong bảng nguoi_dung trước khi xoá nhân viên
            String sqlGetUserId = "SELECT nguoi_dung_id FROM nhan_vien WHERE ma_nv = ?";
            psGetUserId = conn.prepareStatement(sqlGetUserId);
            psGetUserId.setInt(1, maNvInt);
            ResultSet rs = psGetUserId.executeQuery();

            if (rs.next()) {
                nguoiDungId = rs.getInt("nguoi_dung_id");
            }

            // 🔹 Xóa các bản ghi liên quan trong các bảng con
            String sqlDeleteBaoHiem = "DELETE FROM bao_hiem WHERE ma_nv = ?";
            psDeleteBaoHiem = conn.prepareStatement(sqlDeleteBaoHiem);
            psDeleteBaoHiem.setInt(1, maNvInt);
            psDeleteBaoHiem.executeUpdate();

            String sqlDeletePhuCap = "DELETE FROM phu_cap WHERE ma_nv = ?";
            psDeletePhuCap = conn.prepareStatement(sqlDeletePhuCap);
            psDeletePhuCap.setInt(1, maNvInt);
            psDeletePhuCap.executeUpdate();

            String sqlDeleteChamCong = "DELETE FROM cham_cong WHERE ma_nv = ?";
            psDeleteChamCong = conn.prepareStatement(sqlDeleteChamCong);
            psDeleteChamCong.setInt(1, maNvInt);
            psDeleteChamCong.executeUpdate();

            String sqlDeleteLuong = "DELETE FROM luong WHERE ma_nv = ?";
            psDeleteLuong = conn.prepareStatement(sqlDeleteLuong);
            psDeleteLuong.setInt(1, maNvInt);
            psDeleteLuong.executeUpdate();

            // 🔹 Xóa nhân viên
            String sqlDeleteNhanVien = "DELETE FROM nhan_vien WHERE ma_nv = ?";
            psDeleteNhanVien = conn.prepareStatement(sqlDeleteNhanVien);
            psDeleteNhanVien.setInt(1, maNvInt);
            psDeleteNhanVien.executeUpdate();

            // 🔹 Xóa tài khoản trong bảng nguoi_dung nếu tồn tại
            if (nguoiDungId != -1) {
                String sqlDeleteNguoiDung = "DELETE FROM nguoi_dung WHERE id = ?";
                psDeleteNguoiDung = conn.prepareStatement(sqlDeleteNguoiDung);
                psDeleteNguoiDung.setInt(1, nguoiDungId);
                psDeleteNguoiDung.executeUpdate();
            }

            conn.commit(); // Hoàn thành giao dịch
            response.sendRedirect("NhanVienServlet"); // Chuyển hướng về danh sách nhân viên
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ignored) {}
            }
            e.printStackTrace();
            response.getWriter().println("Lỗi khi xóa nhân viên: " + e.getMessage());
        } finally {
            try { if (psGetUserId != null) psGetUserId.close(); } catch (SQLException ignored) {}
            try { if (psDeleteLuong != null) psDeleteLuong.close(); } catch (SQLException ignored) {}
            try { if (psDeleteBaoHiem != null) psDeleteBaoHiem.close(); } catch (SQLException ignored) {}
            try { if (psDeletePhuCap != null) psDeletePhuCap.close(); } catch (SQLException ignored) {}
            try { if (psDeleteChamCong != null) psDeleteChamCong.close(); } catch (SQLException ignored) {}
            try { if (psDeleteNhanVien != null) psDeleteNhanVien.close(); } catch (SQLException ignored) {}
            try { if (psDeleteNguoiDung != null) psDeleteNguoiDung.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}
