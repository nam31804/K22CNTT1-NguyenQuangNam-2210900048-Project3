package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.NguoiDung;
import model.NhanVien;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;

@WebServlet("/NhanVienServlet")
public class NhanVienServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("nguoiDung") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
        String role = nguoiDung.getVaiTro();

        if (role == null || (!role.equals("Admin") && !role.equals("NhanSu"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;

        // Load danh sách nhân viên
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            String query = "SELECT nv.*, nd.email, nd.mat_khau FROM nhan_vien nv JOIN nguoi_dung nd ON nv.nguoi_dung_id = nd.id";
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhanVien nv = new NhanVien(
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
                danhSachNhanVien.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }

        request.setAttribute("danhSachNhanVien", danhSachNhanVien);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    // 🔹 XỬ LÝ THÊM NHÂN VIÊN (DOPOST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String hoTen = request.getParameter("ho_ten");
        String ngaySinh = request.getParameter("ngay_sinh");
        String gioiTinh = request.getParameter("gioi_tinh");
        String phongBan = request.getParameter("phong_ban");
        String chucVu = request.getParameter("chuc_vu");
        String heSoLuongStr = request.getParameter("he_so_luong");
        String luongCoBanStr = request.getParameter("luong_co_ban");
        String email = request.getParameter("email");
        String matKhau = request.getParameter("mat_khau");

        if (hoTen == null || email == null || matKhau == null ||
            hoTen.trim().isEmpty() || email.trim().isEmpty() || matKhau.trim().isEmpty()) {
            response.getWriter().println("Lỗi: Vui lòng điền đầy đủ thông tin!");
            return;
        }

        double heSoLuong = 1.0;
        double luongCoBan = 0;

        try {
            heSoLuong = Double.parseDouble(heSoLuongStr);
            luongCoBan = Double.parseDouble(luongCoBanStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("Lỗi: Hệ số lương hoặc lương cơ bản không hợp lệ!");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlUser = "INSERT INTO nguoi_dung (email, mat_khau, vai_tro) VALUES (?, ?, 'NhanVien')";
            ps = conn.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, matKhau);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int nguoiDungId = 0;
            if (generatedKeys.next()) {
                nguoiDungId = generatedKeys.getInt(1);
            }

            String sqlNV = "INSERT INTO nhan_vien (ho_ten, ngay_sinh, gioi_tinh, phong_ban, chuc_vu, he_so_luong, luong_co_ban, nguoi_dung_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sqlNV);
            ps.setString(1, hoTen);
            ps.setString(2, ngaySinh);
            ps.setString(3, gioiTinh);
            ps.setString(4, phongBan);
            ps.setString(5, chucVu);
            ps.setDouble(6, heSoLuong);
            ps.setDouble(7, luongCoBan);
            ps.setInt(8, nguoiDungId);

            ps.executeUpdate();
            conn.commit();
            response.sendRedirect("NhanVienServlet");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi thêm nhân viên!");
        }
    }
}
