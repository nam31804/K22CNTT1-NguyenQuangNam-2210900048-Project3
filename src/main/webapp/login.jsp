<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<% 
    if (session.getAttribute("nguoiDung") != null) {
        response.sendRedirect("dashboard.jsp"); // Hoặc chuyển hướng theo vai trò
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="style.css"> 
</head>
<body>
    <div class="container">
        <h2>Đăng nhập</h2>
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <p><%= error %></p>
        <% } %>
        <form action="login" method="post">
            <input type="email" name="email" placeholder="Nhập email" required>
            <input type="password" name="matKhau" placeholder="Nhập mật khẩu" required>
            <button type="submit">Đăng nhập</button>
        </form>
    </div>
</body>

</html>
