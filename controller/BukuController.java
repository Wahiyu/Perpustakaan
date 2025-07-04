package controller;

import java.util.List;
import model.Buku;
import model.BukuDAO;

public class BukuController {
    private BukuDAO bukuDAO;
    
    public BukuController() {
        bukuDAO = new BukuDAO();
    }
    
    public void addBuku(Buku buku) throws Exception {
        bukuDAO.addBuku(buku);
    }
    
    public List<Buku> getAllBuku() throws Exception {
        return bukuDAO.getAllBuku();
    }
    
    public void updateBuku(Buku buku) throws Exception {
        bukuDAO.updateBuku(buku);
    }
    
    public void deleteBuku(int idBuku) throws Exception {
        bukuDAO.deleteBuku(idBuku);
    }
    
    public Buku getBukuById(int idBuku) throws Exception {
        return bukuDAO.getBukuById(idBuku);
    }
}