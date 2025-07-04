package model;

public class Buku {
    private int idBuku;
    private String judul;
    private String penulis;
    private int tahunTerbit;
    private String kategori;
    private int stok;
    
    public Buku() {}
    
    public Buku(String judul, String penulis, int tahunTerbit, String kategori, int stok) {
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.kategori = kategori;
        this.stok = stok;
    }
    
    // Getter dan Setter
    public int getIdBuku() {
        return idBuku;
    }
    
    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }
    
    public String getJudul() {
        return judul;
    }
    
    public void setJudul(String judul) {
        this.judul = judul;
    }
    
    public String getPenulis() {
        return penulis;
    }
    
    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }
    
    public int getTahunTerbit() {
        return tahunTerbit;
    }
    
    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    public int getStok() {
        return stok;
    }
    
    public void setStok(int stok) {
        this.stok = stok;
    }
}