package model;

public class NqnNguoiDung {
    private int id;
    private String email;
    private String matKhau;
    private String vaiTro;
    private String hoTen; // 🆕 Thêm họ tên vào model

    public NqnNguoiDung() {}

    public NqnNguoiDung(int id, String email, String matKhau, String vaiTro, String hoTen) {
        this.id = id;
        this.email = email;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.hoTen = hoTen;
    }

    // 🛠 Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; } // 🆕 Getter và Setter cho họ tên
}
