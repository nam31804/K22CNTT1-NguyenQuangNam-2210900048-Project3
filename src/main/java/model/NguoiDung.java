package model;

public class NguoiDung {
    private int id;
    private String email;
    private String matKhau;
    private String vaiTro;

    public NguoiDung() {}

    public NguoiDung(int id, String email, String matKhau, String vaiTro) {
        this.id = id;
        this.email = email;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
}
