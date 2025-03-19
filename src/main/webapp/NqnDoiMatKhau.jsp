<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.sql.*" %>


<!DOCTYPE html>
<html>
<head>
    <title>NqnDashboard - Đổi mật khẩu</title>
    <meta charset="UTF-8">
    <!-- Liên kết file CSS -->
    <link rel="stylesheet" type="text/css" href="css/Nqndmk.css">
</head>
<body>
<jsp:include page="Nqntrang-chu-nhan-vien.jsp" />

    <div class="container-dashboard">
        <h2>Đổi mật khẩu</h2>

        <form method="POST" action="NqnDoiMatKhauServlet">
            <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                String successMessage = (String) request.getAttribute("successMessage");

                if (errorMessage != null) {
            %>
                <p style="color: red;"><%= errorMessage %></p>
            <% } %>

            <% if (successMessage != null) { %>
                <p style="color: green;"><%= successMessage %></p>
            <% } %>

            <div>
                <label for="matKhauCu">Mật khẩu cũ:</label>
                <input type="password" id="matKhauCu" name="matKhauCu" required />
            </div>
            <div>
                <label for="matKhauMoi">Mật khẩu mới:</label>
                <input type="password" id="matKhauMoi" name="matKhauMoi" required />
            </div>
            <div>
                <label for="matKhauMoiConfirm">Nhập mật khẩu:</label>
                <input type="password" id="matKhauMoiConfirm" name="matKhauMoiConfirm" required />
            </div>
            <div>
                <button type="submit">Đổi mật khẩu</button>
            </div>
        </form>
    </div>
</body>
<jsp:include page="Nqnfooter.jsp" />
</html>
