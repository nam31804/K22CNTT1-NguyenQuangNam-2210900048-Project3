package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.NqnDBConnection;

/**
 * Servlet implementation class NqnDoiMatKhauServlet
 */
@WebServlet("/NqnDoiMatKhauServlet")
public class NqnDoiMatKhauServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("nguoiDung") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        model.NqnNguoiDung nguoiDung = (model.NqnNguoiDung) session.getAttribute("nguoiDung");
        int nguoiDungId = nguoiDung.getId();

        String matKhauCu = request.getParameter("matKhauCu");
        String matKhauMoi = request.getParameter("matKhauMoi");
        String matKhauMoiConfirm = request.getParameter("matKhauMoiConfirm");

        String errorMessage = null;
        String successMessage = null;

        if (matKhauMoi == null || matKhauMoiConfirm == null || matKhauCu == null) {
            errorMessage = "Vui lòng điền đầy đủ thông tin!";
        } else if (!matKhauMoi.equals(matKhauMoiConfirm)) {
            errorMessage = "Mật khẩu mới và xác nhận mật khẩu không khớp!";
        } else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = NqnDBConnection.getConnection();
                if (conn == null) {
                    throw new SQLException("Không thể kết nối đến database!");
                }

                // Kiểm tra mật khẩu cũ
                String query = "SELECT mat_khau FROM nguoi_dung WHERE id = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, nguoiDungId);
                rs = ps.executeQuery();

                if (rs.next()) {
                    String matKhauCuDB = rs.getString("mat_khau");
                    if (!matKhauCu.equals(matKhauCuDB)) {
                        errorMessage = "Mật khẩu cũ không đúng!";
                    } else {
                        // Cập nhật mật khẩu mới
                        String updateQuery = "UPDATE nguoi_dung SET mat_khau = ? WHERE id = ?";
                        ps = conn.prepareStatement(updateQuery);
                        ps.setString(1, matKhauMoi);
                        ps.setInt(2, nguoiDungId);
                        int rowsUpdated = ps.executeUpdate();

                        if (rowsUpdated > 0) {
                            successMessage = "Mật khẩu đã được thay đổi thành công!";
                        } else {
                            errorMessage = "Lỗi khi thay đổi mật khẩu!";
                        }
                    }
                } else {
                    errorMessage = "Không tìm thấy người dùng!";
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "Lỗi khi cập nhật mật khẩu: " + e.getMessage();
            } finally {
                try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
                try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }

        // Gửi thông báo đến JSP
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        } else if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }

        // Chuyển hướng về lại trang đổi mật khẩu
        request.getRequestDispatcher("NqnDoiMatKhau.jsp").forward(request, response);
    }
}