-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2025 at 04:57 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpustakaan`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_buku`
--

CREATE TABLE `tb_buku` (
  `id_buku` int(11) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `penulis` varchar(100) NOT NULL,
  `tahun_terbit` int(11) NOT NULL,
  `kategori` varchar(50) NOT NULL,
  `stok` int(11) NOT NULL DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `cover_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tb_buku`
--

INSERT INTO `tb_buku` (`id_buku`, `judul`, `penulis`, `tahun_terbit`, `kategori`, `stok`, `created_at`, `cover_path`) VALUES
(1, 'Pemrograman Java', 'Budi Raharjo', 2019, 'Teknologi', 5, '2025-07-03 16:23:21', NULL),
(2, 'Desain Database', 'Joko Santoso', 2020, 'Teknologi', 6, '2025-07-03 16:23:21', NULL),
(4, 'Kalkulus Dasar', 'Ahmad Fauzi', 2021, 'Matematika', 4, '2025-07-03 16:23:21', NULL),
(6, 'Laskar Pelangi', 'Andrea Hirata', 2005, 'Fiksi', 12, '2025-07-04 03:29:06', NULL),
(7, 'Bumi Manusia', 'Pramoedya Ananta Toer', 2005, 'Fiksi', 6, '2025-07-04 03:30:00', NULL),
(8, 'Laut Bercerita', 'Leila S. Chudor', 2017, 'Fiksi', 9, '2025-07-04 03:30:53', NULL),
(9, 'Negeri 5 Menara', 'Ahmad Fuadi', 2009, 'Fiksi', 7, '2025-07-04 03:31:30', NULL),
(10, 'Cantik Itu Luka', 'Eka Kurniawan', 2002, 'Fiksi', 4, '2025-07-04 03:32:13', NULL),
(11, 'Gadis Kretek', 'Ratih Kumala', 2012, 'Fiksi', 8, '2025-07-04 03:33:05', NULL),
(12, 'Pulang', 'Leila S. Chudori', 2015, 'Fiksi', 9, '2025-07-04 03:33:52', NULL),
(13, 'Saman', 'Ayu Utami', 1998, 'Fiksi', 6, '2025-07-04 03:34:48', NULL),
(15, 'The Power of Habit', 'Charles Duhigg', 2012, 'Non-Fiksi', 2, '2025-07-04 03:36:31', NULL),
(16, 'Atomic Habits', 'James Clear', 2018, 'Non-Fiksi', 5, '2025-07-04 03:37:17', NULL),
(17, 'Kisah, Perjuangan, & Inspirasi B.J. Habibie', 'M. Farid Rahman', 2019, 'Non-Fiksi', 6, '2025-07-04 03:38:15', NULL),
(18, 'Educated', 'Tara Westover', 2018, 'Non-Fiksi', 5, '2025-07-04 03:39:26', NULL),
(19, 'Mindset', 'Carol S. Dweck', 2006, 'Non-Fiksi', 2, '2025-07-04 03:40:30', NULL),
(21, 'Koala Kumal', 'Raditya Dika', 2015, 'Non-Fiksi', 4, '2025-07-04 05:31:43', NULL),
(22, 'Kacil', 'Reyhan', 2015, 'Cerita Anak', 6, '2025-07-04 11:03:54', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tb_peminjaman`
--

CREATE TABLE `tb_peminjaman` (
  `id_peminjaman` int(11) NOT NULL,
  `id_buku` int(11) NOT NULL,
  `nama_peminjam` varchar(100) NOT NULL,
  `tanggal_pinjam` date NOT NULL,
  `tanggal_kembali` date DEFAULT NULL,
  `status` enum('Dipinjam','Dikembalikan') DEFAULT 'Dipinjam'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tb_peminjaman`
--

INSERT INTO `tb_peminjaman` (`id_peminjaman`, `id_buku`, `nama_peminjam`, `tanggal_pinjam`, `tanggal_kembali`, `status`) VALUES
(1, 2, 'joni', '2025-07-04', '2025-07-04', 'Dikembalikan'),
(2, 4, 'seno', '2025-07-04', '2025-07-04', 'Dikembalikan'),
(4, 15, 'Agus', '2025-07-04', '2025-07-04', 'Dikembalikan'),
(5, 19, 'Ilham', '2025-07-04', NULL, 'Dipinjam');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_buku`
--
ALTER TABLE `tb_buku`
  ADD PRIMARY KEY (`id_buku`);

--
-- Indexes for table `tb_peminjaman`
--
ALTER TABLE `tb_peminjaman`
  ADD PRIMARY KEY (`id_peminjaman`),
  ADD KEY `id_buku` (`id_buku`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_buku`
--
ALTER TABLE `tb_buku`
  MODIFY `id_buku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `tb_peminjaman`
--
ALTER TABLE `tb_peminjaman`
  MODIFY `id_peminjaman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_peminjaman`
--
ALTER TABLE `tb_peminjaman`
  ADD CONSTRAINT `tb_peminjaman_ibfk_1` FOREIGN KEY (`id_buku`) REFERENCES `tb_buku` (`id_buku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
