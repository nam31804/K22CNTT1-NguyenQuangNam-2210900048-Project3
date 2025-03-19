<%@page import="java.sql.*" %>
<%@page import="database.NqnDBConnection" %>
<%@page import="java.text.NumberFormat" %>
<%@page import="java.util.Locale" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%
    // Kiểm tra session
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("nguoiDung") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Lấy thông tin người dùng từ session
    model.NqnNguoiDung nguoiDung = (model.NqnNguoiDung) sess.getAttribute("nguoiDung");
    String role = nguoiDung.getVaiTro();

    if (role == null || (!role.equals("Admin") && !role.equals("NhanSu"))) {
        response.sendRedirect("login.jsp");
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // Kết nối database
        conn = NqnDBConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Không thể kết nối đến database!");
        }

        // Truy vấn bảng lương
        String query = "SELECT nv.ho_ten, l.thang, l.nam, l.tong_luong, " +
                       "COALESCE(cc.so_ngay_cong, 0) AS so_ngay_cong, " +
                       "COALESCE(SUM(bh.muc_dong), 0) AS bao_hiem, COALESCE(SUM(pc.so_tien), 0) AS phu_cap " +
                       "FROM nhan_vien nv " +
                       "JOIN luong l ON nv.ma_nv = l.ma_nv " +
                       "LEFT JOIN cham_cong cc ON nv.ma_nv = cc.ma_nv AND l.thang = cc.thang AND l.nam = cc.nam " +
                       "LEFT JOIN bao_hiem bh ON nv.ma_nv = bh.ma_nv " +
                       "LEFT JOIN phu_cap pc ON nv.ma_nv = pc.ma_nv " +
                       "GROUP BY nv.ho_ten, l.thang, l.nam, l.tong_luong, cc.so_ngay_cong";
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();

        // Định dạng số tiền
        NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
%>
<!DOCTYPE html>
<html>
<head>
    <title>Bảng Lương Nhân Viên</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/Nqnalll.css">
</head>
<body>
<jsp:include page="Nqntrang-chu-admin.jsp" />
<div class="container-dashboard">
    <h2>Bảng Lương</h2>
     <div class="action-links">
            
        </div>
    <table border="1">
        <tr>
            <th>Họ tên</th>
            <th>Tháng</th>
            <th>Năm</th>
            <th>Lương</th>
            <th>Số ngày công</th>
            <th>Bảo hiểm</th>
            <th>Phụ cấp</th>
            <th>Hành động</th>
        </tr>
        <%
            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getString("ho_ten") %></td>
            <td><%= rs.getInt("thang") %></td>
            <td><%= rs.getInt("nam") %></td>
            <td><%= currencyFormat.format(rs.getDouble("tong_luong")) %> VND</td>
            <td><%= rs.getInt("so_ngay_cong") %></td>
            <td><%= currencyFormat.format(rs.getDouble("bao_hiem")) %> VND</td>
            <td><%= currencyFormat.format(rs.getDouble("phu_cap")) %> VND</td>
            <td>
               <div class="action-links"> <a href="NqnSuaLuongServlet?ho_ten=<%= rs.getString("ho_ten") %>&thang=<%= rs.getInt("thang") %>&nam=<%= rs.getInt("nam") %>">Cập Nhật</a> </div> 
              
            </td>
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
        // Đóng kết nối an toàn
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>
