<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="model.NqnNguoiDung" %>

<% 
    NqnNguoiDung nguoiDung = (NqnNguoiDung) session.getAttribute("nguoiDung");
    if (nguoiDung != null) {
        String vaiTro = nguoiDung.getVaiTro();
        if ("Admin".equals(vaiTro) || "NhanSu".equals(vaiTro)) {
            response.sendRedirect("Nqntrang-chu-admin.jsp"); // ✅ Chuyển về trang admin
        } else if ("NhanVien".equals(vaiTro)) {
            response.sendRedirect("Nqntrang-chu-nhan-vien.jsp"); // ✅ Chuyển về trang nhân viên
        }
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="css/Nqndn.css"> 
</head>
<body>
    <div class="container">
        <h2>Đăng nhập</h2>
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <p style="color:red;"><%= error %></p>
        <% } %>
        <form action="Login" method="post">
            <input type="email" name="email" placeholder="Nhập email" required>
            <input type="password" name="matKhau" placeholder="Nhập mật khẩu" required>
            <button type="submit">Đăng nhập</button>
        </form>
    </div>
</body>
</html>
