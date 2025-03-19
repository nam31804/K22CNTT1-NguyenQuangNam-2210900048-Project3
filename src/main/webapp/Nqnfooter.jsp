<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Footer</title>
    <link rel="stylesheet" href="css/footer.css">
    <style>
       .footer {
    background: linear-gradient(to right, #4facfe, #00f2fe); /* Màu giống menu */
    color: black;
    text-align: center;
    padding: 20px;
    margin-top: 30px;
    border-radius: 10px;
}
.footer a {
    color: black;
    text-decoration: none;
    font-weight: bold;
}
.footer a:hover {
    text-decoration: underline;
}
.footer .social-icons {
    margin-top: 10px;
}
.footer .social-icons a {
    margin: 0 10px;
}

    </style>
</head>
<body>

<footer class="footer">
    <p>&copy; 2025 Doanh Nghiệp Nam31804 - Vịt Quay Lạng Sơn</p>
    <p>Địa chỉ: Đông Kinh, Thành phố Lạng Sơn, Việt Nam</p>
    <p>Số điện thoại: <a href="tel:0707431804">0707431804</a></p>
    <p>Email: <a href="nam31804@gmail.com">nam31804@gmail.com</a></p>
    <p>Chính sách: <a href="https://www.facebook.com/profile.php?id=61573484324656">Bảo mật</a> | <a href="https://www.facebook.com/profile.php?id=61573484324656">Cookie</a></p>
    <div class="social-icons">
        <a href="https://www.facebook.com/profile.php?id=61573484324656" target="_blank">Facebook</a>
        | <a href="https://zalo.me/nam31804" target="_blank">Zalo</a>
    </div>
    <div class="map">
        <iframe
            src="https://maps.google.com/maps?q=21.845247, 106.761375&hl=vi&z=15&output=embed"
            width="100%" height="200" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
    </div>
</footer>

</body>
</html>