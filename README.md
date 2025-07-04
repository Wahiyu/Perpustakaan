# PerpustakaanNusantara

Aplikasi manajemen perpustakaan berbasis Java Swing menggunakan arsitektur MVC dan koneksi ke database MySQL.

## Fitur
- Manajemen data buku (Tambah, Tampil, Hapus)
- Peminjaman dan pengembalian buku
- Antarmuka GUI berbasis Java Swing
- Koneksi database via JDBC
- Struktur program mengikuti pola MVC

## Struktur Direktori
```
PerpustakaanNusantara/
├── controller/
├── model/
├── view/
├── util/
├── lib/
├── perpustakaankita.sql
└── README.md
```

## Cara Menjalankan
1. **Clone repository ini:**
   ```bash
   git clone https://github.com/username/PerpustakaanNusantara.git
   ```

2. **Import database:**
   - Buka `phpMyAdmin` atau aplikasi MySQL lainnya.
   - Buat database baru dengan nama: `perpustakaankita`
   - Import file `perpustakaankita.sql`

3. **Konfigurasi koneksi database:**
   - Buka file `util/DatabaseConnection.java`
   - Ganti bagian username/password sesuai dengan database lokal Anda.

4. **Compile dan Jalankan Program:**
   - Pastikan sudah menambahkan library MySQL Connector ke dalam classpath (`lib/mysql-connector-j-xx.jar`)
   - Jalankan file `MainView.java` atau `Main.java` jika ada.

## Kebutuhan
- Java JDK 8+
- MySQL Server
- NetBeans/IDE lain (disarankan: NetBeans karena GUI-nya dibuat dengan GUI Builder)
