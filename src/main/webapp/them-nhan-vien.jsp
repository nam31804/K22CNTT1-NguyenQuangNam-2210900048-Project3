<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm nhân viên</title>
</head>
<body>
    <h2>Thêm nhân viên</h2>
    <form action="NhanVienServlet" method="post">
    <label>Họ tên:</label> <input type="text" name="ho_ten" required><br>
    <label>Ngày sinh:</label> <input type="date" name="ngay_sinh" required><br>
    <label>Giới tính:</label> 
    <select name="gioi_tinh">
        <option value="Nam">Nam</option>
        <option value="Nữ">Nữ</option>
    </select><br>
    <label>Phòng ban:</label> <input type="text" name="phong_ban" required><br>
    <label>Chức vụ:</label> <input type="text" name="chuc_vu" required><br>
    <label>Hệ số lương:</label> <input type="number" name="he_so_luong" step="0.1" required><br>
    <label>Lương cơ bản:</label> <input type="number" name="luong_co_ban" required><br>
    <label>Email:</label> <input type="email" name="email" required><br>
    <label>Mật khẩu:</label> <input type="password" name="mat_khau" required><br>
    <label>Vai trò:</label> 
<select name="vai_tro" required>
    <option value="NhanVien">Nhân viên</option>
    <option value="NhanSu">Nhân sự</option>
    <option value="Admin">Admin</option>
</select><br>
    
    <button type="submit">Thêm</button>
</form>

</body>
</html>
