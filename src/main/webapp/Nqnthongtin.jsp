<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.sql.*" %>
<%@page import="database.NqnDBConnection" %>
<%@page import="java.text.NumberFormat" %>
<%@page import="java.util.Locale" %>

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
    int nguoiDungId = nguoiDung.getId();

    // Kết nối cơ sở dữ liệu
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        // Kết nối đến database
        conn = NqnDBConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Không thể kết nối đến database!");
        }

        // Truy vấn thông tin nhân viên theo nguoiDungId
        String query = "SELECT nv.ma_nv, nv.ho_ten, nv.ngay_sinh, nv.gioi_tinh, nv.phong_ban, nv.chuc_vu, " +
                       "nv.he_so_luong, nv.luong_co_ban, nv.nguoi_dung_id, nd.email " +
                       "FROM nhan_vien nv JOIN nguoi_dung nd ON nv.nguoi_dung_id = nd.id WHERE nd.id = ?";
        
        ps = conn.prepareStatement(query);
        ps.setInt(1, nguoiDungId); // Truyền ID người dùng vào câu truy vấn
        rs = ps.executeQuery();

        if (rs.next()) {
            // Lấy thông tin nhân viên
            String hoTen = rs.getString("ho_ten");
            Date ngaySinh = rs.getDate("ngay_sinh");
            String gioiTinh = rs.getString("gioi_tinh");
            String phongBan = rs.getString("phong_ban");
            String chucVu = rs.getString("chuc_vu");
            double heSoLuong = rs.getDouble("he_so_luong");
            double luongCoBan = rs.getDouble("luong_co_ban");
            String email = rs.getString("email");

            // Định dạng số tiền
            NumberFormat currencyFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
%>

<!DOCTYPE html>
<html>
<head>
    <title>NqnDashboard - Thông tin nhân viên</title>
    <meta charset="UTF-8">
    <!-- Liên kết file CSS -->
    <link rel="stylesheet" type="text/css" href="css/Nqndsnv.css">
</head>
<body>
<jsp:include page="Nqntrang-chu-nhan-vien.jsp" />

    <div class="container-dashboard">
        <h2>Thông tin cá nhân</h2>

        <table>
            <tr>
                <th>Mã NV</th>
                <td><%= rs.getInt("ma_nv") %></td>
            </tr>
            <tr>
                <th>Họ tên</th>
                <td><%= hoTen %></td>
            </tr>
            <tr>
                <th>Ngày sinh</th>
                <td><%= ngaySinh %></td>
            </tr>
            <tr>
                <th>Giới tính</th>
                <td><%= gioiTinh %></td>
            </tr>
            <tr>
                <th>Phòng ban</th>
                <td><%= phongBan %></td>
            </tr>
            <tr>
                <th>Chức vụ</th>
                <td><%= chucVu %></td>
            </tr>
            <tr>
                <th>Hệ số lương</th>
                <td><%= heSoLuong %></td>
            </tr>
            <tr>
                <th>Lương cơ bản</th>
                <td><%= currencyFormat.format(luongCoBan) %> VND</td>
            </tr>
            <tr>
                <th>Email</th>
                <td><%= email %></td>
            </tr>
        </table>

        
        <div class="action-links">
    <a href="NqnDoiMatKhau.jsp">Đổi mật khẩu</a>
</div>
        
    </div>
</body>
<jsp:include page="Nqnfooter.jsp" />
</html>

<%
        } else {
            out.println("<p style='color: red;'>Không tìm thấy thông tin nhân viên!</p>");
        }
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
