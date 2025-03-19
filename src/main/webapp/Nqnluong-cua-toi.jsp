<%@page import="java.sql.*" %>
<%@page import="database.NqnDBConnection" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<%
    // 🛠 Kiểm tra session
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("nguoiDung") == null) {
        response.sendRedirect("Nqnlogin.jsp");
        return;
    }

    // 🛠 Lấy thông tin người dùng từ session
    model.NqnNguoiDung nguoiDung = (model.NqnNguoiDung) sess.getAttribute("nguoiDung");
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
        conn = NqnDBConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Không thể kết nối đến database!");
        }

        // 🛠 Truy vấn dữ liệu lương, chấm công, bảo hiểm, phụ cấp
        String query = "SELECT nv.ho_ten, l.thang, l.nam, l.tong_luong, c.so_ngay_cong, " +
                       "COALESCE(SUM(b.muc_dong), 0) AS tong_bao_hiem, " +
                       "COALESCE(SUM(p.so_tien), 0) AS tong_phu_cap " +
                       "FROM nhan_vien nv " +
                       "JOIN luong l ON nv.ma_nv = l.ma_nv " +
                       "LEFT JOIN cham_cong c ON nv.ma_nv = c.ma_nv AND l.thang = c.thang AND l.nam = c.nam " +
                       "LEFT JOIN bao_hiem b ON nv.ma_nv = b.ma_nv " +
                       "LEFT JOIN phu_cap p ON nv.ma_nv = p.ma_nv " +
                       "WHERE nv.nguoi_dung_id = ? " +
                       "GROUP BY nv.ho_ten, l.thang, l.nam, l.tong_luong, c.so_ngay_cong";

        ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        NumberFormat currencyFormat = NumberFormat.getNumberInstance(Locale.US); // Định dạng số tiền
%>

<!DOCTYPE html>
<html>
<head>
    <title>Lương của tôi</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/Nqnlct.css">
</head>
<body>
<jsp:include page="Nqntrang-chu-nhan-vien.jsp" />
<div class="container-dashboard">
    <h2>Bảng lương cá nhân</h2>
    
    <table border="1">
        <tr>
            <th>Họ tên</th>
            <th>Tháng</th>
            <th>Năm</th>
            <th>Lương</th>
            <th>Số ngày công</th>
            <th>Tổng bảo hiểm</th>
            <th>Tổng phụ cấp</th>
        </tr>
        <%
            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("ho_ten") %></td>
            <td><%= rs.getInt("thang") %></td>
            <td><%= rs.getInt("nam") %></td>
            <td><%= currencyFormat.format(rs.getDouble("tong_luong")) %> VND</td>
            <td><%= rs.getInt("so_ngay_cong") %> ngày</td>
            <td><%= currencyFormat.format(rs.getDouble("tong_bao_hiem")) %> VND</td>
            <td><%= currencyFormat.format(rs.getDouble("tong_phu_cap")) %> VND</td>
        </tr>
        <% } %>
    </table>
    </div>
</body>
<jsp:include page="Nqnfooter.jsp" />
</html>

<%
    } catch (Exception e) {
        e.printStackTrace();
%>
    <p style="color: red;">Lỗi khi tải dữ liệu: <%= e.getMessage() %></p>
<%
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>
