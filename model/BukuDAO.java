package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class BukuDAO {
    private Connection connection;
    
    public BukuDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // CRUD Operations
    public void addBuku(Buku buku) throws SQLException {
        String sql = "INSERT INTO tb_buku (judul, penulis, tahun_terbit, kategori, stok) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, buku.getJudul());
            statement.setString(2, buku.getPenulis());
            statement.setInt(3, buku.getTahunTerbit());
            statement.setString(4, buku.getKategori());
            statement.setInt(5, buku.getStok());
            statement.executeUpdate();
        }
    }
    
    public List<Buku> getAllBuku() throws SQLException {
        List<Buku> bukuList = new ArrayList<>();
        String sql = "SELECT * FROM tb_buku";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(resultSet.getInt("id_buku"));
                buku.setJudul(resultSet.getString("judul"));
                buku.setPenulis(resultSet.getString("penulis"));
                buku.setTahunTerbit(resultSet.getInt("tahun_terbit"));
                buku.setKategori(resultSet.getString("kategori"));
                buku.setStok(resultSet.getInt("stok"));
                bukuList.add(buku);
            }
        }
        return bukuList;
    }
    
    public void updateBuku(Buku buku) throws SQLException {
        String sql = "UPDATE tb_buku SET judul = ?, penulis = ?, tahun_terbit = ?, kategori = ?, stok = ? WHERE id_buku = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, buku.getJudul());
            statement.setString(2, buku.getPenulis());
            statement.setInt(3, buku.getTahunTerbit());
            statement.setString(4, buku.getKategori());
            statement.setInt(5, buku.getStok());
            statement.setInt(6, buku.getIdBuku());
            statement.executeUpdate();
        }
    }
    
    public void deleteBuku(int idBuku) throws SQLException {
        String sql = "DELETE FROM tb_buku WHERE id_buku = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idBuku);
            statement.executeUpdate();
        }
    }
    
    public Buku getBukuById(int idBuku) throws SQLException {
        String sql = "SELECT * FROM tb_buku WHERE id_buku = ?";
        Buku buku = null;
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idBuku);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    buku = new Buku();
                    buku.setIdBuku(resultSet.getInt("id_buku"));
                    buku.setJudul(resultSet.getString("judul"));
                    buku.setPenulis(resultSet.getString("penulis"));
                    buku.setTahunTerbit(resultSet.getInt("tahun_terbit"));
                    buku.setKategori(resultSet.getString("kategori"));
                    buku.setStok(resultSet.getInt("stok"));
                }
            }
        }
        return buku;
    }
    public void kurangiStok(int idBuku) throws SQLException {
    String sql = "UPDATE tb_buku SET stok = stok - 1 WHERE id_buku = ? AND stok > 0";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, idBuku);
        stmt.executeUpdate();
    }
}

public void tambahStok(int idBuku) throws SQLException {
    String sql = "UPDATE tb_buku SET stok = stok + 1 WHERE id_buku = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, idBuku);
        stmt.executeUpdate();
    }
}
}
