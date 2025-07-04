package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Peminjaman;
import model.Buku;
import controller.PeminjamanController;
import controller.BukuController;

public class PeminjamanView extends JPanel {
    private PeminjamanController peminjamanController;
    private BukuController bukuController;
    private JTable peminjamanTable;
    private DefaultTableModel tableModel;
    private JTextField namaPeminjamField, searchField;
    private JComboBox<Buku> bukuComboBox;
    private JButton pinjamButton, kembalikanButton, refreshButton;

    public PeminjamanView() {
        peminjamanController = new PeminjamanController();
        bukuController = new BukuController();
        initializeUI();
        loadPeminjamanData();
        loadBukuComboBox();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel form
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Peminjaman"));

        namaPeminjamField = new JTextField();
        bukuComboBox = new JComboBox<>();
        bukuComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Buku) {
                    Buku buku = (Buku) value;
                    setText(buku.getJudul() + " (" + buku.getPenulis() + ")");
                }
                return this;
            }
        });

        formPanel.add(new JLabel("Nama Peminjam:"));
        formPanel.add(namaPeminjamField);
        formPanel.add(new JLabel("Buku:"));
        formPanel.add(bukuComboBox);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pinjamButton = new JButton("Pinjam");
        pinjamButton.setBackground(new Color(76, 175, 80));
        pinjamButton.setForeground(Color.BLACK);
        
        kembalikanButton = new JButton("Kembalikan");
        kembalikanButton.setBackground(new Color(33, 150, 243));
        kembalikanButton.setForeground(Color.BLACK);
        
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(255, 152, 0));
        refreshButton.setForeground(Color.BLACK);

        buttonPanel.add(pinjamButton);
        buttonPanel.add(kembalikanButton);
        buttonPanel.add(refreshButton);

        // Panel form + tombol
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel pencarian
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        JButton searchButton = new JButton("Cari");
        searchPanel.add(new JLabel("Cari Peminjaman:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Tabel peminjaman
        tableModel = new DefaultTableModel(new Object[]{"ID", "Judul Buku", "Peminjam", "Tgl Pinjam", "Tgl Kembali", "Status"}, 0);
        peminjamanTable = new JTable(tableModel);
        peminjamanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        peminjamanTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(peminjamanTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Peminjaman"));

        // Layout utama
        add(northPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Event listeners
        pinjamButton.addActionListener(e -> pinjamBuku());
        kembalikanButton.addActionListener(e -> kembalikanBuku());
        refreshButton.addActionListener(e -> loadPeminjamanData());
        searchButton.addActionListener(e -> searchPeminjaman());
    }

    private void loadPeminjamanData() {
        try {
            List<Peminjaman> peminjamanList = peminjamanController.getAllPeminjaman();
            tableModel.setRowCount(0); // Clear existing data

            for (Peminjaman p : peminjamanList) {
                Buku buku = bukuController.getBukuById(p.getIdBuku());
                Object[] rowData = {
                    p.getIdPeminjaman(),
                    buku != null ? buku.getJudul() : "Buku tidak ditemukan",
                    p.getNamaPeminjam(),
                    p.getTanggalPinjam(),
                    p.getTanggalKembali(),
                    p.getStatus()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBukuComboBox() {
        try {
            List<Buku> bukuList = bukuController.getAllBuku();
            bukuComboBox.removeAllItems();
            for (Buku buku : bukuList) {
                if (buku.getStok() > 0) {
                    bukuComboBox.addItem(buku);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading buku: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pinjamBuku() {
        if (namaPeminjamField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama peminjam harus diisi", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (bukuComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan dipinjam", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Buku selectedBuku = (Buku) bukuComboBox.getSelectedItem();
            String namaPeminjam = namaPeminjamField.getText().trim();

            peminjamanController.pinjamBuku(selectedBuku.getIdBuku(), namaPeminjam);
            loadPeminjamanData();
            loadBukuComboBox();
            namaPeminjamField.setText("");
            JOptionPane.showMessageDialog(this, "Buku berhasil dipinjam", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void kembalikanBuku() {
        int selectedRow = peminjamanTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih peminjaman yang akan dikembalikan", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idPeminjaman = (int) peminjamanTable.getValueAt(selectedRow, 0);
            peminjamanController.kembalikanBuku(idPeminjaman);
            loadPeminjamanData();
            loadBukuComboBox();
            JOptionPane.showMessageDialog(this, "Buku berhasil dikembalikan", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchPeminjaman() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadPeminjamanData();
            return;
        }

        try {
            List<Peminjaman> hasil = peminjamanController.cariPeminjaman(keyword);
            tableModel.setRowCount(0);

            for (Peminjaman p : hasil) {
                Buku buku = bukuController.getBukuById(p.getIdBuku());
                Object[] rowData = {
                    p.getIdPeminjaman(),
                    buku != null ? buku.getJudul() : "Buku tidak ditemukan",
                    p.getNamaPeminjam(),
                    p.getTanggalPinjam(),
                    p.getTanggalKembali(),
                    p.getStatus()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}