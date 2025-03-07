package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Luong;

import java.io.IOException;
import java.util.List;

import dao.LuongDAO;

/**
 * Servlet implementation class LuongServlet
 */
@WebServlet("/LuongServlet")
public class LuongServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	    private LuongDAO luongDAO = new LuongDAO();

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        String role = (String) session.getAttribute("role");
	        Integer userId = (Integer) session.getAttribute("userId");

	        if (role == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        if (role.equals("Admin") || role.equals("NhanSu")) {
	            // Admin/Nhân sự xem tất cả (trừ Nhân sự không thấy Admin)
	            List<Luong> list = luongDAO.getAllLuong();
	            request.setAttribute("listLuong", list);
	            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
	        } else if (role.equals("NhanVien")) {
	            // Nhân viên chỉ xem lương của họ
	            List<Luong> list = luongDAO.getLuongByUserId(userId);
	            request.setAttribute("listLuong", list);
	            request.getRequestDispatcher("luong-cua-toi.jsp").forward(request, response);
	        }
	    }
	}