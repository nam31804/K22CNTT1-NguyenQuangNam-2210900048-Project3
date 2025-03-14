package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class NqnHomeServlet
 */
@WebServlet("/HomeServlet")
public class NqnHomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("nguoiDung") == null) {
            response.sendRedirect("Nqnlogin.jsp");  // Chưa đăng nhập
            return;
        }
        
        model.NqnNguoiDung nguoiDung = (model.NqnNguoiDung) session.getAttribute("nguoiDung");
        String role = nguoiDung.getVaiTro();
        
        if ("Admin".equals(role)) {
            response.sendRedirect("Nqntrang-chu-admin.jsp");
        } else {
            response.sendRedirect("Nqntrang-chu-nhan-vien.jsp");
        }
    }
}
