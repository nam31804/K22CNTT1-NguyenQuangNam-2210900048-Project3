package model;

public class NqnBaoHiem {
    private int id;
    private int maNv;
    private String loaiBaoHiem;
    private double mucDong;

    public NqnBaoHiem(int id, int maNv, String loaiBaoHiem, double mucDong) {
        this.id = id;
        this.maNv = maNv;
        this.loaiBaoHiem = loaiBaoHiem;
        this.mucDong = mucDong;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMaNv() { return maNv; }
    public void setMaNv(int maNv) { this.maNv = maNv; }

    public String getLoaiBaoHiem() { return loaiBaoHiem; }
    public void setLoaiBaoHiem(String loaiBaoHiem) { this.loaiBaoHiem = loaiBaoHiem; }

    public double getMucDong() { return mucDong; }
    public void setMucDong(double mucDong) { this.mucDong = mucDong; }
}
