<%@page import="java.sql.*" %>
<%@page import="database.DBConnection" %>
<%@page import="java.text.NumberFormat" %>
<%@page import="java.util.Locale" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%
    // 🛠 Kiểm tra session
    HttpSession sess = request.getSession(false);
    if (sess == null || sess.getAttribute("nguoiDung") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // 🛠 Lấy thông tin người dùng từ session
    model.NguoiDung nguoiDung = (model.NguoiDung) sess.getAttribute("nguoiDung");
    String role = nguoiDung.getVaiTro();

    if (role == null || (!role.equals("Admin") && !role.equals("NhanSu"))) {
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

        // 🛠 Truy vấn tất cả thông tin nhân viên
        String query = "SELECT nv.ma_nv, nv.ho_ten, nv.ngay_sinh, nv.gioi_tinh, nv.phong_ban, nv.chuc_vu, " +
                       "nv.he_so_luong, nv.luong_co_ban, nv.nguoi_dung_id, nd.email, nd.mat_khau " +
                       "FROM nhan_vien nv JOIN nguoi_dung nd ON nv.nguoi_dung_id = nd.id";
        
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();

        // 🛠 Định dạng số tiền
        NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Danh sách nhân viên</title>
    <meta charset="UTF-8">
</head>
<body>
    <h2>Danh sách nhân viên</h2>
    <a href="LogoutServlet" style="color: red; font-weight: bold;">Đăng xuất</a>
    
    <a href="them-nhan-vien.jsp">Thêm nhân viên</a>
    <table border="1">
        <tr>
            <th>Mã NV</th>
            <th>Họ tên</th>
            <th>Ngày sinh</th>
            <th>Giới tính</th>
            <th>Phòng ban</th>
            <th>Chức vụ</th>
            <th>Hệ số lương</th>
            <th>Lương cơ bản</th>
            <th>Email</th>
            <th>Mật khẩu</th>
            <th>Hành động</th>
        </tr>
        <%
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
        %>
        <tr>
            <td><%= rs.getInt("ma_nv") %></td>
            <td><%= rs.getString("ho_ten") %></td>
            <td><%= rs.getDate("ngay_sinh") %></td>
            <td><%= rs.getString("gioi_tinh") %></td>
            <td><%= rs.getString("phong_ban") %></td>
            <td><%= rs.getString("chuc_vu") %></td>
            <td><%= rs.getDouble("he_so_luong") %></td>
            <td><%= currencyFormat.format(rs.getDouble("luong_co_ban")) %> VND</td>
            <td><%= rs.getString("email") %></td>
            <td><%= rs.getString("mat_khau") %></td>
            <td>
    <a href="SuaNhanVienServlet?edit=<%= rs.getInt("ma_nv") %>">Sửa</a> |
    <a href="XoaNhanVienServlet?ma_nv=<%= rs.getInt("ma_nv") %>" 
   onclick="return confirm('Bạn có chắc muốn xóa nhân viên này không?');">
   Xóa
</a>

</td>

        </tr>
        <% }
        if (!hasData) { %>
            <tr>
                <td colspan="11" style="text-align: center; color: red;">Không có dữ liệu nhân viên!</td>
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