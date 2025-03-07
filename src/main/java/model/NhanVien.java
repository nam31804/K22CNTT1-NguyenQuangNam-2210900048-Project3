package model;

import java.util.Date;

public class NhanVien {
    private int maNV;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String phongBan;
    private String chucVu;
    private double heSoLuong;
    private double luongCoBan;
    private int nguoiDungId;
    private String email;
    private String matKhau;

    public NhanVien(int maNV, String hoTen, Date ngaySinh, String gioiTinh, String phongBan,
                     String chucVu, double heSoLuong, double luongCoBan, int nguoiDungId,
                     String email, String matKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.phongBan = phongBan;
        this.chucVu = chucVu;
        this.heSoLuong = heSoLuong;
        this.luongCoBan = luongCoBan;
        this.nguoiDungId = nguoiDungId;
        this.email = email;
        this.matKhau = matKhau;
    }

    // Getters and Setters
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getPhongBan() { return phongBan; }
    public void setPhongBan(String phongBan) { this.phongBan = phongBan; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public double getHeSoLuong() { return heSoLuong; }
    public void setHeSoLuong(double heSoLuong) { this.heSoLuong = heSoLuong; }

    public double getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(double luongCoBan) { this.luongCoBan = luongCoBan; }

    public int getNguoiDungId() { return nguoiDungId; }
    public void setNguoiDungId(int nguoiDungId) { this.nguoiDungId = nguoiDungId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}