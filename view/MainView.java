package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public MainView() {
        setTitle("Sistem Manajemen Perpustakaan");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Gunakan Look and Feel sistem
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Buat tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab Manajemen Buku
        BukuView bukuPanel = new BukuView(); // HARUS extends JPanel
        JScrollPane bukuScroll = new JScrollPane(bukuPanel);
        tabbedPane.addTab("Manajemen Buku", bukuScroll);

        // Tab Peminjaman Buku
        PeminjamanView peminjamanPanel = new PeminjamanView(); // HARUS extends JPanel
        JScrollPane peminjamanScroll = new JScrollPane(peminjamanPanel);
        tabbedPane.addTab("Peminjaman Buku", peminjamanScroll);

        // Tambahkan tabbedPane ke bagian tengah
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Footer (opsional)
        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        footerPanel.add(new JLabel("STI202303353 - Tri Wahyu Hidayat"));
        getContentPane().add(footerPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
