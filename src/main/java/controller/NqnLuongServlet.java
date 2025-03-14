package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.NqnLuong;

import java.io.IOException;
import java.util.List;

import dao.NqnLuongDAO;

/**
 * Servlet implementation class NqnLuongServlet
 */
@WebServlet("/LuongServlet")
public class NqnLuongServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	    private NqnLuongDAO NqnluongDAO = new NqnLuongDAO();

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        HttpSession session = request.getSession();
	        String role = (String) session.getAttribute("role");
	        Integer userId = (Integer) session.getAttribute("userId");

	        if (role == null) {
	            response.sendRedirect("Nqnlogin.jsp");
	            return;
	        }

	        if (role.equals("Admin") || role.equals("NhanSu")) {
	            // Admin/Nhân sự xem tất cả (trừ Nhân sự không thấy Admin)
	            List<NqnLuong> list = NqnluongDAO.getAllLuong();
	            request.setAttribute("listLuong", list);
	            request.getRequestDispatcher("Nqndashboard.jsp").forward(request, response);
	        } else if (role.equals("NhanVien")) {
	            // Nhân viên chỉ xem lương của họ
	            List<NqnLuong> list = NqnluongDAO.getLuongByUserId(userId);
	            request.setAttribute("listLuong", list);
	            request.getRequestDispatcher("Nqnluong-cua-toi.jsp").forward(request, response);
	        }
	    }
	}