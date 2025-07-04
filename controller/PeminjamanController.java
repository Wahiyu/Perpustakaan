package controller;

import java.sql.SQLException;
import java.util.List;
import model.Peminjaman;
import model.PeminjamanDAO;
import model.BukuDAO;

public class PeminjamanController {
    private PeminjamanDAO peminjamanDAO;
    private BukuDAO bukuDAO;

    public PeminjamanController() {
        peminjamanDAO = new PeminjamanDAO();
        bukuDAO = new BukuDAO();
    }

    public void pinjamBuku(int idBuku, String namaPeminjam) throws SQLException {
        // Cek stok buku
        if (bukuDAO.getBukuById(idBuku).getStok() <= 0) {
            throw new SQLException("Stok buku habis");
        }

        // Buat peminjaman baru
        peminjamanDAO.pinjamBuku(idBuku, namaPeminjam);
        
        // Kurangi stok buku
        bukuDAO.kurangiStok(idBuku);
    }

    public void kembalikanBuku(int idPeminjaman) throws SQLException {
        // Dapatkan data peminjaman
        Peminjaman peminjaman = peminjamanDAO.getPeminjamanById(idPeminjaman);
        
        if (peminjaman == null) {
            throw new SQLException("Data peminjaman tidak ditemukan");
        }

        if ("Dikembalikan".equals(peminjaman.getStatus())) {
            throw new SQLException("Buku sudah dikembalikan sebelumnya");
        }

        // Update status peminjaman
        peminjamanDAO.kembalikanBuku(idPeminjaman);
        
        // Tambah stok buku
        bukuDAO.tambahStok(peminjaman.getIdBuku());
    }

    public List<Peminjaman> getAllPeminjaman() throws SQLException {
        return peminjamanDAO.getAllPeminjaman();
    }

    public List<Peminjaman> cariPeminjaman(String keyword) throws SQLException {
        return peminjamanDAO.cariPeminjaman(keyword);
    }

    public Peminjaman getPeminjamanById(int idPeminjaman) throws SQLException {
        return peminjamanDAO.getPeminjamanById(idPeminjaman);
    }
}