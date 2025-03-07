<%@page import="java.sql.*" %>
<%@page import="database.DBConnection" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
    // 🛠 Kiểm tra session
    HttpSession sess = request.getSession(false); // 🚀 Dùng `false` để tránh tạo session mới
    if (sess == null || sess.getAttribute("nguoiDung") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // 🛠 Lấy thông tin người dùng từ session
    model.NguoiDung nguoiDung = (model.NguoiDung) sess.getAttribute("nguoiDung");
    Integer userId = nguoiDung.getId();
    String role = nguoiDung.getVaiTro();

    if (userId == null || !"NhanVien".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // 🛠 Kết nối database
        conn = DBConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Không thể kết nối đến database!");
        }

        // 🛠 Truy vấn dữ liệu
        String query = "SELECT nv.ho_ten, l.thang, l.nam, l.tong_luong " +
                       "FROM nhan_vien nv JOIN luong l ON nv.ma_nv = l.ma_nv " +
                       "WHERE nv.nguoi_dung_id = ?";
        ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        rs = ps.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Lương của tôi</title>
    <meta charset="UTF-8">
</head>
<body>
    <h2>Bảng lương cá nhân</h2>
    <table border="1">
        <tr>
            <th>Họ tên</th>
            <th>Tháng</th>
            <th>Năm</th>
            <th>Lương</th>
        </tr>
        <%
            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("ho_ten") %></td>
            <td><%= rs.getInt("thang") %></td>
            <td><%= rs.getInt("nam") %></td>
            <td><%= rs.getDouble("tong_luong") %> VND</td>
        </tr>
        <% } %>
    </table>
</body>
</html>
<%
    } catch (Exception e) {
        e.printStackTrace();
%>
    <p style="color: red;">Lỗi khi tải dữ liệu: <%= e.getMessage() %></p>
<%
    } finally {
        // 🛠 Đóng kết nối an toàn
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>
