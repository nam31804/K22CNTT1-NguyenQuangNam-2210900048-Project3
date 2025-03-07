package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.NguoiDung;

import java.io.IOException;

import dao.NguoiDungDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý đăng nhập
        String email = request.getParameter("email");
        String matKhau = request.getParameter("matKhau");

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
        NguoiDung nguoiDung = nguoiDungDAO.dangNhap(email, matKhau);

        if (nguoiDung != null) {
            HttpSession session = request.getSession();
            session.setAttribute("nguoiDung", nguoiDung);

            if ("Admin".equals(nguoiDung.getVaiTro()) || "NhanSu".equals(nguoiDung.getVaiTro())) {
                response.sendRedirect("dashboard.jsp"); // Trang quản lý
            } else {
                response.sendRedirect("luong-cua-toi.jsp"); // Trang dành cho nhân viên
            }
        } else {
            request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp"); // Chuyển hướng về trang login nếu có yêu cầu GET
    }
}
