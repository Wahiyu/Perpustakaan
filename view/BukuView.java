package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Buku;
import controller.BukuController;

public class BukuView extends JPanel {
    private BukuController controller;
    private JTable bukuTable;
    private DefaultTableModel tableModel;
    private JTextField judulField, penulisField, tahunField, kategoriField, stokField;

    public BukuView() {
        controller = new BukuController();
        setLayout(new BorderLayout());
        initializeUI();
        loadBukuData();
    }

    private void initializeUI() {
        // Panel utama dengan border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel form input
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Tambah/Edit Buku"));

        judulField = new JTextField();
        penulisField = new JTextField();
        tahunField = new JTextField();
        kategoriField = new JTextField();
        stokField = new JTextField();

        formPanel.add(new JLabel("Judul:"));
        formPanel.add(judulField);
        formPanel.add(new JLabel("Penulis:"));
        formPanel.add(penulisField);
        formPanel.add(new JLabel("Tahun Terbit:"));
        formPanel.add(tahunField);
        formPanel.add(new JLabel("Kategori:"));
        formPanel.add(kategoriField);
        formPanel.add(new JLabel("Stok:"));
        formPanel.add(stokField);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Hapus");
        JButton clearButton = new JButton("Clear");

        // Styling tombol
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.BLACK);
        updateButton.setBackground(new Color(33, 150, 243));
        updateButton.setForeground(Color.BLACK);
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.BLACK);
        clearButton.setBackground(new Color(255, 152, 0));
        clearButton.setForeground(Color.BLACK);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        // Panel form + tombol
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tabel buku
        tableModel = new DefaultTableModel(new Object[]{"ID", "Judul", "Penulis", "Tahun", "Kategori", "Stok"}, 0);
        bukuTable = new JTable(tableModel);
        bukuTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bukuTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(bukuTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Buku"));

        // Menambahkan komponen ke panel utama
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Tambahkan main panel ke panel ini (JPanel)
        this.add(mainPanel, BorderLayout.CENTER);

        // Event listeners
        addButton.addActionListener(e -> addBuku());
        updateButton.addActionListener(e -> updateBuku());
        deleteButton.addActionListener(e -> deleteBuku());
        clearButton.addActionListener(e -> clearForm());

        bukuTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = bukuTable.getSelectedRow();
                if (selectedRow >= 0) {
                    fillFormFromTable(selectedRow);
                }
            }
        });
    }

    private void loadBukuData() {
        try {
            List<Buku> bukuList = controller.getAllBuku();
            tableModel.setRowCount(0); // Clear existing data

            for (Buku buku : bukuList) {
                Object[] rowData = {
                    buku.getIdBuku(),
                    buku.getJudul(),
                    buku.getPenulis(),
                    buku.getTahunTerbit(),
                    buku.getKategori(),
                    buku.getStok()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addBuku() {
        try {
            Buku buku = new Buku(
                    judulField.getText(),
                    penulisField.getText(),
                    Integer.parseInt(tahunField.getText()),
                    kategoriField.getText(),
                    Integer.parseInt(stokField.getText())
            );

            controller.addBuku(buku);
            loadBukuData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBuku() {
        int selectedRow = bukuTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan diupdate",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idBuku = (int) bukuTable.getValueAt(selectedRow, 0);
            Buku buku = new Buku(
                    judulField.getText(),
                    penulisField.getText(),
                    Integer.parseInt(tahunField.getText()),
                    kategoriField.getText(),
                    Integer.parseInt(stokField.getText())
            );
            buku.setIdBuku(idBuku);

            controller.updateBuku(buku);
            loadBukuData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Buku berhasil diupdate",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBuku() {
        int selectedRow = bukuTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang akan dihapus",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus buku ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int idBuku = (int) bukuTable.getValueAt(selectedRow, 0);
                controller.deleteBuku(idBuku);
                loadBukuData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Buku berhasil dihapus",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        judulField.setText("");
        penulisField.setText("");
        tahunField.setText("");
        kategoriField.setText("");
        stokField.setText("");
        bukuTable.clearSelection();
    }

    private void fillFormFromTable(int selectedRow) {
        judulField.setText(bukuTable.getValueAt(selectedRow, 1).toString());
        penulisField.setText(bukuTable.getValueAt(selectedRow, 2).toString());
        tahunField.setText(bukuTable.getValueAt(selectedRow, 3).toString());
        kategoriField.setText(bukuTable.getValueAt(selectedRow, 4).toString());
        stokField.setText(bukuTable.getValueAt(selectedRow, 5).toString());
    }
}
