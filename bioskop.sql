-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for bioskop
CREATE DATABASE IF NOT EXISTS `bioskop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `bioskop`;

-- Dumping structure for table bioskop.film
CREATE TABLE IF NOT EXISTS `film` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judul` varchar(100) NOT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `durasi` int(11) DEFAULT NULL,
  `harga` double DEFAULT 0,
  `deskripsi` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table bioskop.film: ~4 rows (approximately)
REPLACE INTO `film` (`id`, `judul`, `genre`, `durasi`, `harga`, `deskripsi`) VALUES
	(1, 'Avatar', 'Sci-Fi', 162, 100, 'Kisah tentang sebuah planet alien dan penduduk aslinya yang berjuang melawan eksploitasi manusia.'),
	(2, 'Inception', 'Action', 148, 120, 'Sebuah thriller yang membingungkan pikiran dan mengeksplorasi konsep mimpi di dalam mimpi.'),
	(3, 'Frozen', 'Animation', 102, 80, 'Kisah menyentuh tentang dua saudara perempuan yang menjalani kehidupan, cinta, dan kekuatan magis di sebuah kerajaan bersalju.'),
	(4, 'The Godfather', 'Drama', 175, 150, 'Sebuah saga tentang keluarga kriminal dan perjuangan mereka untuk kekuasaan, loyalitas, dan bertahan hidup.');

-- Dumping structure for table bioskop.tiket
CREATE TABLE IF NOT EXISTS `tiket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nama_pembeli` varchar(100) DEFAULT NULL,
  `film_id` int(11) DEFAULT NULL,
  `jumlah_tiket` int(11) DEFAULT NULL,
  `harga_total` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `film_id` (`film_id`),
  CONSTRAINT `tiket_ibfk_1` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table bioskop.tiket: ~6 rows (approximately)
REPLACE INTO `tiket` (`id`, `nama_pembeli`, `film_id`, `jumlah_tiket`, `harga_total`) VALUES
	(1, 'Anonymous', 3, 2, 100),
	(2, 'Anonymous', 4, 22, 3300),
	(3, 'TEST', 1, 222, 22200),
	(4, 'TEST', 3, 2, 160),
	(5, 'TEST', 1, 2222, 222200),
	(6, 'TEST', 1, 22, 2200),
	(7, 'TEST', 1, 1, 100);

-- Dumping structure for table bioskop.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table bioskop.user: ~8 rows (approximately)
REPLACE INTO `user` (`user_id`, `username`, `password`, `email`) VALUES
	(1, 'TEST', '123', '123'),
	(2, 'Username', 'jPasswordField1', 'Email'),
	(3, 'Name', 'Password', 'Email'),
	(4, 'Name', 'Password', 'Email'),
	(5, 'Name', 'Password', 'Email'),
	(6, 'Name', 'Password', 'Email'),
	(7, 'awd', 'awdawd', 'awdad'),
	(8, 'Username', 'jPasswordField1', 'Email');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
