package model;

public class NqnPhuCap {
    private int id;
    private int maNv;
    private String loaiPhuCap;
    private double soTien;

    public NqnPhuCap(int id, int maNv, String loaiPhuCap, double soTien) {
        this.id = id;
        this.maNv = maNv;
        this.loaiPhuCap = loaiPhuCap;
        this.soTien = soTien;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMaNv() { return maNv; }
    public void setMaNv(int maNv) { this.maNv = maNv; }

    public String getLoaiPhuCap() { return loaiPhuCap; }
    public void setLoaiPhuCap(String loaiPhuCap) { this.loaiPhuCap = loaiPhuCap; }

    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = soTien; }
}
