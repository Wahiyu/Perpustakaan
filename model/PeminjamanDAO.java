package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class PeminjamanDAO {
    private Connection connection;

    public PeminjamanDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void pinjamBuku(int idBuku, String namaPeminjam) throws SQLException {
        String sql = "INSERT INTO tb_peminjaman (id_buku, nama_peminjam, tanggal_pinjam, status) VALUES (?, ?, CURDATE(), 'Dipinjam')";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idBuku);
            stmt.setString(2, namaPeminjam);
            stmt.executeUpdate();
        }
    }

    public void kembalikanBuku(int idPeminjaman) throws SQLException {
        String sql = "UPDATE tb_peminjaman SET tanggal_kembali = CURDATE(), status = 'Dikembalikan' WHERE id_peminjaman = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPeminjaman);
            stmt.executeUpdate();
        }
    }

    public List<Peminjaman> getAllPeminjaman() throws SQLException {
        List<Peminjaman> peminjamanList = new ArrayList<>();
        String sql = "SELECT * FROM tb_peminjaman ORDER BY tanggal_pinjam DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Peminjaman p = new Peminjaman();
                p.setIdPeminjaman(rs.getInt("id_peminjaman"));
                p.setIdBuku(rs.getInt("id_buku"));
                p.setNamaPeminjam(rs.getString("nama_peminjam"));
                p.setTanggalPinjam(rs.getDate("tanggal_pinjam"));
                p.setTanggalKembali(rs.getDate("tanggal_kembali"));
                p.setStatus(rs.getString("status"));
                peminjamanList.add(p);
            }
        }
        return peminjamanList;
    }

    public Peminjaman getPeminjamanById(int idPeminjaman) throws SQLException {
        String sql = "SELECT * FROM tb_peminjaman WHERE id_peminjaman = ?";
        Peminjaman p = null;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPeminjaman);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = new Peminjaman();
                    p.setIdPeminjaman(rs.getInt("id_peminjaman"));
                    p.setIdBuku(rs.getInt("id_buku"));
                    p.setNamaPeminjam(rs.getString("nama_peminjam"));
                    p.setTanggalPinjam(rs.getDate("tanggal_pinjam"));
                    p.setTanggalKembali(rs.getDate("tanggal_kembali"));
                    p.setStatus(rs.getString("status"));
                }
            }
        }
        return p;
    }

    public List<Peminjaman> cariPeminjaman(String keyword) throws SQLException {
        List<Peminjaman> hasil = new ArrayList<>();
        String sql = "SELECT * FROM tb_peminjaman WHERE nama_peminjam LIKE ? OR id_peminjaman LIKE ? ORDER BY tanggal_pinjam DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Peminjaman p = new Peminjaman();
                    p.setIdPeminjaman(rs.getInt("id_peminjaman"));
                    p.setIdBuku(rs.getInt("id_buku"));
                    p.setNamaPeminjam(rs.getString("nama_peminjam"));
                    p.setTanggalPinjam(rs.getDate("tanggal_pinjam"));
                    p.setTanggalKembali(rs.getDate("tanggal_kembali"));
                    p.setStatus(rs.getString("status"));
                    hasil.add(p);
                }
            }
        }
        return hasil;
    }
} 