package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class NqnLogoutServlet
 */
@WebServlet("/LogoutServlet")
public class NqnLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session hiện tại
        if (session != null) {
            session.invalidate(); // Hủy session
        }
        response.sendRedirect("Nqnlogin.jsp"); // Chuyển hướng về trang đăng nhập
    }
}
