package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.NqnNguoiDung;
import model.NqnNhanVien;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.NqnDBConnection;

/**
 * Servlet implementation class NqnSuaNhanVienServlet
 */
@WebServlet("/SuaNhanVienServlet")
public class NqnSuaNhanVienServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NqnSuaNhanVienServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("nguoiDung") == null) {
            response.sendRedirect("Nqnlogin.jsp");
            return;
        }

        NqnNguoiDung nguoiDung = (NqnNguoiDung) session.getAttribute("nguoiDung");
        String role = nguoiDung.getVaiTro();

        if (role == null || (!role.equals("Admin") && !role.equals("NhanSu"))) {
            response.sendRedirect("Nqnlogin.jsp");
            return;
        }

        String editId = request.getParameter("edit");

        if (editId != null && !editId.trim().isEmpty()) {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = NqnDBConnection.getConnection();
                int id = Integer.parseInt(editId);
                String sqlSelect = "SELECT nv.*, nd.email, nd.mat_khau FROM nhan_vien nv " +
                                   "JOIN nguoi_dung nd ON nv.nguoi_dung_id = nd.id WHERE nv.ma_nv = ?";
                ps = conn.prepareStatement(sqlSelect);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
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

                    request.setAttribute("nhanVien", nv);
                    request.getRequestDispatcher("Nqnsua-nhan-vien.jsp").forward(request, response);
                    return;
                } else {
                    response.getWriter().println("Lỗi: Không tìm thấy nhân viên!");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Lỗi: ID sửa không hợp lệ!");
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Lỗi khi lấy dữ liệu nhân viên!");
            } finally {
                try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
                try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
            }
        }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");

	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("nguoiDung") == null) {
	        response.sendRedirect("Nqnlogin.jsp");
	        return;
	    }

	    NqnNguoiDung nguoiDung = (NqnNguoiDung) session.getAttribute("nguoiDung");
	    String role = nguoiDung.getVaiTro();

	    if (role == null || (!role.equals("Admin") && !role.equals("NhanSu"))) {
	        response.sendRedirect("Nqnlogin.jsp");
	        return;
	    }

	    try {
	        int maNV = Integer.parseInt(request.getParameter("ma_nv"));
	        String hoTen = request.getParameter("ho_ten");
	        String ngaySinh = request.getParameter("ngay_sinh");
	        String gioiTinh = request.getParameter("gioi_tinh");
	        String phongBan = request.getParameter("phong_ban");
	        String chucVu = request.getParameter("chuc_vu");
	        double heSoLuong = Double.parseDouble(request.getParameter("he_so_luong"));
	        double luongCoBan = Double.parseDouble(request.getParameter("luong_co_ban"));
	        String email = request.getParameter("email");
	        String matKhau = request.getParameter("mat_khau");

	        Connection conn = null;
	        PreparedStatement psNhanVien = null;
	        PreparedStatement psNguoiDung = null;

	        try {
	            conn = NqnDBConnection.getConnection();
	            conn.setAutoCommit(false);

	            // Cập nhật bảng nhan_vien
	            String sqlUpdateNV = "UPDATE nhan_vien SET ho_ten=?, ngay_sinh=?, gioi_tinh=?, phong_ban=?, chuc_vu=?, he_so_luong=?, luong_co_ban=? WHERE ma_nv=?";
	            psNhanVien = conn.prepareStatement(sqlUpdateNV);
	            psNhanVien.setString(1, hoTen);
	            psNhanVien.setString(2, ngaySinh);
	            psNhanVien.setString(3, gioiTinh);
	            psNhanVien.setString(4, phongBan);
	            psNhanVien.setString(5, chucVu);
	            psNhanVien.setDouble(6, heSoLuong);
	            psNhanVien.setDouble(7, luongCoBan);
	            psNhanVien.setInt(8, maNV);
	            psNhanVien.executeUpdate();

	            // Cập nhật bảng nguoi_dung
	            String sqlUpdateND = "UPDATE nguoi_dung SET email=?, mat_khau=? WHERE id=(SELECT nguoi_dung_id FROM nhan_vien WHERE ma_nv=?)";
	            psNguoiDung = conn.prepareStatement(sqlUpdateND);
	            psNguoiDung.setString(1, email);
	            psNguoiDung.setString(2, matKhau);
	            psNguoiDung.setInt(3, maNV);
	            psNguoiDung.executeUpdate();

	            conn.commit();
	            response.sendRedirect("Nqndashboard.jsp");
	        } catch (SQLException e) {
	            if (conn != null) {
	                try { conn.rollback(); } catch (SQLException ignored) {}
	            }
	            e.printStackTrace();
	            response.getWriter().println("Lỗi SQL khi cập nhật nhân viên!");
	        } finally {
	            try { if (psNhanVien != null) psNhanVien.close(); } catch (SQLException ignored) {}
	            try { if (psNguoiDung != null) psNguoiDung.close(); } catch (SQLException ignored) {}
	            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
	        }
	    } catch (NumberFormatException e) {
	        response.getWriter().println("Lỗi: Dữ liệu nhập vào không hợp lệ!");
	    }
	}
}