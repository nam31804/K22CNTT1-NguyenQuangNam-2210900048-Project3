package model;

public class NqnLuong {
    private int maNV;
    private String hoTen;
    private int thang;
    private int nam;
    private double tongLuong;

    public NqnLuong(int maNV, String hoTen, int thang, int nam, double tongLuong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.thang = thang;
        this.nam = nam;
        this.tongLuong = tongLuong;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public double getTongLuong() {
        return tongLuong;
    }

    public void setTongLuong(double tongLuong) {
        this.tongLuong = tongLuong;
    }
}
