package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.DBConnection;

@WebServlet("/XoaNhanVienServlet") // Đảm bảo đúng đường dẫn!
public class XoaNhanVienServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public XoaNhanVienServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String maNV = request.getParameter("ma_nv");

        if (maNV == null || maNV.trim().isEmpty()) {
            response.getWriter().println("❌ Lỗi: Mã nhân viên không hợp lệ!");
            return;
        }

        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DBConnection.getConnection();
            if (conn == null) {
                response.getWriter().println("❌ Lỗi: Không thể kết nối database!");
                return;
            }
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 🛠 Xóa nhân viên trước
            String sqlDeleteNhanVien = "DELETE FROM nhan_vien WHERE ma_nv = ?";
            ps1 = conn.prepareStatement(sqlDeleteNhanVien);
            ps1.setInt(1, Integer.parseInt(maNV));
            int rows1 = ps1.executeUpdate();

            // 🛠 Xóa tài khoản người dùng
            String sqlDeleteNguoiDung = "DELETE FROM nguoi_dung WHERE id = (SELECT nguoi_dung_id FROM nhan_vien WHERE ma_nv = ?)";
            ps2 = conn.prepareStatement(sqlDeleteNguoiDung);
            ps2.setInt(1, Integer.parseInt(maNV));
            int rows2 = ps2.executeUpdate();

            if (rows1 > 0) {
                conn.commit(); // Nếu xóa thành công thì commit
                response.sendRedirect("dashboard.jsp"); 
            } else {
                response.getWriter().println("❌ Không tìm thấy nhân viên để xóa!");
            }

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ignored) {}
            response.getWriter().println("❌ Lỗi SQL: " + e.getMessage());
        } finally {
            try { if (ps1 != null) ps1.close(); } catch (SQLException ignored) {}
            try { if (ps2 != null) ps2.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    }
}
