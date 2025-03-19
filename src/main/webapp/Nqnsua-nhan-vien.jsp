<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.NqnNhanVien" %>

<%
    NqnNhanVien nhanVien = (NqnNhanVien) request.getAttribute("nhanVien");
    if (nhanVien == null) {
%>
    <h3 style="color: red;">Lỗi: Không tìm thấy nhân viên!</h3>
    <a href="Nqndashboard.jsp">Quay lại danh sách nhân viên</a>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Sửa nhân viên</title>
    <link rel="stylesheet" type="text/css" href="css/Nqntnv.css">
</head>
<body>
<jsp:include page="Nqntrang-chu-admin.jsp" />
    <h2>Sửa nhân viên</h2>
    <form action="SuaNhanVienServlet" method="post">
        <input type="hidden" name="ma_nv" value="<%= nhanVien.getMaNV() %>">

        <label>Họ tên:</label>
        <input type="text" name="ho_ten" value="<%= nhanVien.getHoTen() %>" required><br>

        <label>Ngày sinh:</label>
        <input type="date" name="ngay_sinh" value="<%= nhanVien.getNgaySinh() %>" required><br>

        <label>Giới tính:</label>
        <select name="gioi_tinh">
            <option value="Nam" <%= "Nam".equals(nhanVien.getGioiTinh()) ? "selected" : "" %>>Nam</option>
            <option value="Nữ" <%= "Nữ".equals(nhanVien.getGioiTinh()) ? "selected" : "" %>>Nữ</option>
        </select><br>

        <label>Phòng ban:</label>
        <input type="text" name="phong_ban" value="<%= nhanVien.getPhongBan() %>" required><br>

        <label>Chức vụ:</label>
        <input type="text" name="chuc_vu" value="<%= nhanVien.getChucVu() %>" required><br>

        <label>Hệ số lương:</label>
        <input type="number" name="he_so_luong" step="0.1" value="<%= nhanVien.getHeSoLuong() %>" required><br>

        <label>Lương cơ bản:</label>
        <input type="number" name="luong_co_ban" value="<%= nhanVien.getLuongCoBan() %>" required><br>

        <label>Email:</label>
        <input type="email" name="email" value="<%= nhanVien.getEmail() %>" required><br>

        <label>Mật khẩu:</label>
        <input type="password" name="mat_khau" value="<%= nhanVien.getMatKhau() %>" required><br>

        <button type="submit">Lưu thay đổi</button>
    </form>
</body>
<jsp:include page="Nqnfooter.jsp" />
</html>
