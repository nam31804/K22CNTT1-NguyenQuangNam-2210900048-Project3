<%@ page contentType="text/html; charset=UTF-8" %>

<%
    String hoTen = (String) request.getAttribute("hoTen");
    int thang = (Integer) request.getAttribute("thang");
    int nam = (Integer) request.getAttribute("nam");
    double luongCoBan = (Double) request.getAttribute("luongCoBan");
    int soNgayCong = (Integer) request.getAttribute("soNgayCong");
    double tongLuong = (luongCoBan / 26) * soNgayCong; // Tính lương theo ngày công
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa lương</title>
    <script>
        function tinhLuong() {
            let soNgayCong = document.getElementById("soNgayCong").value;
            let luongCoBan = <%= luongCoBan %>;
            let tongLuongMoi = (luongCoBan / 26) * soNgayCong;

            document.getElementById("tongLuong").value = tongLuongMoi.toFixed(2);
        }
    </script>
    <link rel="stylesheet" href="css/Nqntl.css">
</head>
<body>
<jsp:include page="Nqntrang-chu-admin.jsp" />
<div class="container-dashboard">
    <h2>Chỉnh sửa lương nhân viên</h2>
    <form action="NqnCapNhatLuongServlet" method="post">
        <input type="hidden" name="ho_ten" value="<%= hoTen %>">
        <input type="hidden" name="thang" value="<%= thang %>">
        <input type="hidden" name="nam" value="<%= nam %>">
        <input type="hidden" name="luong_co_ban" value="<%= luongCoBan %>">

        <label>Số ngày công:</label>
        <input type="number" id="soNgayCong" name="so_ngay_cong" value="<%= soNgayCong %>" oninput="tinhLuong()" required>

        <label>Tổng lương:</label>
        <input type="text" id="tongLuong" name="tong_luong" value="<%= tongLuong %>" readonly>

        <button type="submit">Lưu</button>
    </form>
    </div>
</body>
<jsp:include page="Nqnfooter.jsp" />
</html>
