package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.NqnChamCong;

import java.io.IOException;
import java.util.List;

import dao.NqnChamCongDAO;

/**
 * Servlet implementation class NqnChamCongServlet
 */
@WebServlet("/ChamCongServlet")
public class NqnChamCongServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        NqnChamCongDAO dao = new NqnChamCongDAO();
        List<NqnChamCong> list = dao.getAllChamCong();
        
        request.setAttribute("chamCongList", list);
        request.getRequestDispatcher("Nqnchamcong.jsp").forward(request, response);
    }
}