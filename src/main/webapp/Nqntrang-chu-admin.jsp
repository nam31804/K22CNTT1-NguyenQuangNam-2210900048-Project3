<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.NqnNguoiDung" %>

<%
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("nguoiDung") == null) {
        response.sendRedirect("Nqnlogin.jsp");
        return;
    }

    NqnNguoiDung nguoiDung = (NqnNguoiDung) sess.getAttribute("nguoiDung");
    String hoTen = nguoiDung.getHoTen();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hệ thống quản lý</title>
    <link rel="stylesheet" href="css/Nqnhomead.css">
</head>
<body>

<h2>Chào mừng, Quản Trị Viên!</h2>

<nav>
    <ul>
        <li><a href="Nqnadtt.jsp">🏠 Trang chủ</a></li>
        <li><a href="Nqndashboard.jsp">👥 Quản lý nhân viên</a></li>
        <li><a href="NqnAllLuong.jsp">💰 Quản lý bảng lương</a></li>
        <li><a href="LogoutServlet" style="color: red;">🚪 Đăng xuất</a></li>
    </ul>
</nav>
