package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.NqnNguoiDung;
import dao.NqnNguoiDungDAO;
import java.io.IOException;

@WebServlet("/Login")
public class NqnLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String matKhau = request.getParameter("matKhau");

        NqnNguoiDungDAO nguoiDungDAO = new NqnNguoiDungDAO();
        NqnNguoiDung nguoiDung = nguoiDungDAO.dangNhap(email, matKhau);

        if (nguoiDung != null) {
            HttpSession session = request.getSession();
            session.setAttribute("nguoiDung", nguoiDung);
            session.setAttribute("vaiTro", nguoiDung.getVaiTro());
            session.setAttribute("hoTen", nguoiDung.getHoTen()); // ✅ Lưu họ tên vào session

            // Chuyển hướng theo vai trò
            switch (nguoiDung.getVaiTro()) {
                case "Admin":
                case "NhanSu":
                    response.sendRedirect("Nqnadtt.jsp");
                    break;
                case "NhanVien":
                    response.sendRedirect("Nqnnvtt.jsp");
                    break;
                default:
                    response.sendRedirect("Nqnlogin.jsp?error=Vai trò không hợp lệ!");
                    break;
            }
        }

        else {
            request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("Nqnlogin.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Nqnlogin.jsp"); 
    }
}
