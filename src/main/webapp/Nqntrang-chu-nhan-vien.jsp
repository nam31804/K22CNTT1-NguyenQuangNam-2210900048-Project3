<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.NqnNguoiDung" %>

<% 
    // Kiểm tra session
    NqnNguoiDung nguoiDung = (NqnNguoiDung) session.getAttribute("nguoiDung");
    if (nguoiDung == null || !"NhanVien".equals(nguoiDung.getVaiTro())) {
        response.sendRedirect("Nqnlogin.jsp"); // Nếu chưa đăng nhập, đá về trang login
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ Nhân Viên</title>
     <link rel="stylesheet" href="css/homead.css">
</head>
<body>
    <h2>Chào mừng, <%= nguoiDung.getHoTen() %>!</h2> <!-- ✅ Hiển thị tên nhân viên -->
    <nav>
    <ul>
        <li><a href="Nqntrang-chu-nhan-vien.jsp">🏠 Trang chủ</a></li>
        <li><a href="Nqnluong-cua-toi.jsp">👥 Thông Tin Cá Nhân</a></li>
        <li><a href="NqnAllLuong.jsp">💰 Thông Tin lương</a></li>
        
        <li><a href="Nqnql-tk-baocao.jsp">📊 Liên Hệ</a></li>
        <li><a href="LogoutServlet" style="color: red;">🚪 Đăng xuất</a></li>
    </ul>
</nav>