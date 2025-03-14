package model;

public class NqnChamCong {
    private int id;
    private int maNv;
    private int thang;
    private int nam;
    private int soNgayCong;

    public NqnChamCong(int id, int maNv, int thang, int nam, int soNgayCong) {
        this.id = id;
        this.maNv = maNv;
        this.thang = thang;
        this.nam = nam;
        this.soNgayCong = soNgayCong;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMaNv() { return maNv; }
    public void setMaNv(int maNv) { this.maNv = maNv; }

    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }

    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }

    public int getSoNgayCong() { return soNgayCong; }
    public void setSoNgayCong(int soNgayCong) { this.soNgayCong = soNgayCong; }
}
