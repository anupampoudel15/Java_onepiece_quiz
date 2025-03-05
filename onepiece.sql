-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 12, 2025 at 11:35 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `onepiece`
--

-- --------------------------------------------------------

--
-- Table structure for table `game_history`
--

CREATE TABLE `game_history` (
  `id` int(11) NOT NULL,
  `player_id` int(11) DEFAULT NULL,
  `level` enum('Beginner','Intermediate','Advanced') NOT NULL,
  `score` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `game_history`
--

INSERT INTO `game_history` (`id`, `player_id`, `level`, `score`, `timestamp`) VALUES
(1, 5, 'Beginner', 10, '2025-02-11 19:45:47'),
(2, 5, 'Intermediate', 10, '2025-02-11 19:46:16'),
(3, 5, 'Intermediate', 7, '2025-02-11 19:49:41'),
(4, 5, 'Advanced', 10, '2025-02-11 19:50:21'),
(5, 1, 'Advanced', 9, '2025-02-11 20:02:07'),
(6, 1, 'Advanced', 8, '2025-02-11 20:14:43'),
(7, 5, 'Advanced', 9, '2025-02-11 20:15:33'),
(8, 9, 'Beginner', 10, '2025-02-11 20:16:36'),
(9, 9, 'Intermediate', 7, '2025-02-11 20:17:07'),
(10, 11, 'Beginner', 10, '2025-02-11 20:33:51'),
(11, 11, 'Intermediate', 9, '2025-02-11 20:34:30'),
(12, 5, 'Advanced', 10, '2025-02-11 20:42:02'),
(13, 5, 'Beginner', 10, '2025-02-11 20:42:20'),
(14, 5, 'Advanced', 10, '2025-02-11 20:42:41'),
(15, 5, 'Intermediate', 10, '2025-02-11 20:43:05'),
(16, 12, 'Advanced', 7, '2025-02-11 20:56:04'),
(17, 12, 'Beginner', 10, '2025-02-11 20:56:24'),
(18, 5, 'Advanced', 10, '2025-02-11 21:08:12'),
(19, 5, 'Advanced', 10, '2025-02-11 21:08:34'),
(20, 5, 'Beginner', 10, '2025-02-11 21:38:44'),
(21, 1, 'Beginner', 8, '2025-02-12 06:35:50'),
(22, 1, 'Advanced', 10, '2025-02-12 06:36:16'),
(23, 1, 'Advanced', 9, '2025-02-12 06:36:34');

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE `players` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `level` enum('Beginner','Intermediate','Advanced') NOT NULL,
  `score` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `name`, `level`, `score`) VALUES
(1, 'anupam', 'Advanced', 10),
(2, 'handsome', 'Beginner', 4),
(3, 'coffeemug', 'Beginner', 3),
(4, 'bhawani', 'Beginner', 4),
(5, 'poudel', 'Beginner', 10),
(6, 'maldai', 'Intermediate', 5),
(7, 'bhshwo', 'Advanced', 5),
(8, 'manish', 'Beginner', 4),
(9, 'sachin pariyar', 'Intermediate', 10),
(10, 'kendrak lama', 'Beginner', 0),
(11, 'uthyo', 'Intermediate', 10),
(12, 'helo', 'Beginner', 10),
(13, 'f', 'Beginner', 0);

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `image_path` varchar(255) NOT NULL,
  `correct_answer` varchar(100) NOT NULL,
  `option_1` varchar(100) NOT NULL,
  `option_2` varchar(100) NOT NULL,
  `difficulty` enum('Beginner','Intermediate','Advanced') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `image_path`, `correct_answer`, `option_1`, `option_2`, `difficulty`) VALUES
(35, 'images/luffy.jpg', 'Monkey D. Luffy', 'Monkey D. Luffy', 'Roronoa Zoro', 'Beginner'),
(36, 'images/zoro.jpg', 'Roronoa Zoro', 'Nami', 'Roronoa Zoro', 'Beginner'),
(40, 'images/ace.jpg', 'Portgas D. Ace', 'Nico Robin', 'Portgas D. Ace', 'Advanced'),
(41, 'images/yamato.jpg', 'Yamato', 'Yamato', 'Momonosuke', 'Advanced'),
(43, 'images/kinnemon.jpg', 'Foxfire Kin\'emon', 'Foxfire Kin\'emon', 'Yamato', 'Advanced'),
(44, 'images/denjiro.jpg', 'Denjiro', 'Denjiro', 'Kozuki Oden', 'Advanced'),
(45, 'images/oden.jpg', 'Kozuki Oden', 'Kozuki Oden', 'Kaidou', 'Advanced'),
(46, 'images/robin.jpg', 'Niko Robin', 'Niko Robin', 'Neftari Vivi', 'Intermediate'),
(47, 'images/franky.jpg', 'Franky', 'Shanks', 'Franky', 'Intermediate'),
(48, 'images/Brook.jpg', 'Brook', 'Kuma', 'Brook', 'Intermediate'),
(49, 'images/buggy.jpg', 'Buggy', 'Doflamingo', 'Buggy', 'Beginner'),
(50, 'images/shanks.jpg', 'Shanks', 'Ace', 'Shanks', 'Beginner'),
(51, 'images/mihawk.jpg', 'Dracule Mihawk', 'Dracule Mihawk', 'Smoker', 'Beginner'),
(52, 'images/Smoker.jpg', 'Smoker', 'Alvida', 'Smoker', 'Beginner'),
(53, 'images/Alvida.jpg', 'Alvida', 'Koby', 'Alvida', 'Beginner'),
(54, 'images/Koby.jpg', 'Koby', 'Helmeppo', 'Koby', 'Beginner'),
(55, 'images/Helmeppo.jpg', 'Helmeppo', 'Arlong', 'Helmeppo', 'Beginner'),
(56, 'images/Arlong.jpg', 'Arlong', 'Captain Kuro', 'Arlong', 'Beginner'),
(58, 'images/makino.jpeg', 'Makino', 'Dadan', 'Makino', 'Beginner'),
(59, 'images/dadan.jpg', 'Dadan', 'roger', 'Dadan', 'Beginner'),
(60, 'images/roger.jpg', 'roger', 'Garp', 'roger', 'Beginner'),
(61, 'images/Garp.jpg', 'Garp', 'Dracule Mihawk', 'Garp', 'Beginner'),
(62, 'images/doflamingo.jpg', 'Donquixote Doflamingo', 'Boa Hancock', 'Donquixote Doflamingo', 'Intermediate'),
(63, 'images/hancock.jpg', 'Boa Hancock', 'Trafalgar D. Water Law', 'Boa Hancock', 'Intermediate'),
(64, 'images/law.jpg', 'Trafalgar D. Water Law', 'Eustass Kid', 'Trafalgar D. Water Law', 'Intermediate'),
(65, 'images/kid.jpg', 'Eustass Kid', 'Marshall D. Teach (Blackbeard)', 'Eustass Kid', 'Intermediate'),
(66, 'images/blackbeard.jpg', 'Marshall D. Teach (Blackbeard)', 'Jinbe', 'Marshall D. Teach (Blackbeard)', 'Intermediate'),
(67, 'images/Jinbe.jpg', 'Jinbe', 'Silvers Rayleigh', 'Jinbe', 'Intermediate'),
(68, 'images/rayleigh.jpg', 'Silvers Rayleigh', 'Perona', 'Silvers Rayleigh', 'Intermediate'),
(69, 'images/Perona.jpg', 'Perona', 'Rob Lucci', 'Perona', 'Intermediate'),
(70, 'images/lucci.jpg', 'Rob Lucci', 'Sabo', 'Rob Lucci', 'Intermediate'),
(71, 'images/sabo.jpg', 'Sabo', 'Ivankov', 'Sabo', 'Intermediate'),
(72, 'images/ivankov.jpg', 'Ivankov', 'Crocodile', 'Ivankov', 'Intermediate'),
(73, 'images/Crocodile.jpg', 'Crocodile', 'Enel', 'Crocodile', 'Intermediate'),
(74, 'images/Enel.jpg', 'Enel', 'Kuzan (Aokiji)', 'Enel', 'Intermediate'),
(75, 'images/kujan.jpg', 'Kuzan (Aokiji)', 'Sakazuki (Akainu)', 'Kuzan (Aokiji)', 'Intermediate'),
(76, 'images/akainu.jpg', 'Sakazuki (Akainu)', 'Donquixote Doflamingo', 'Sakazuki (Akainu)', 'Intermediate'),
(77, 'images/kawamatsu.jpg', 'Kawamatsu', 'vegapunk', 'Kawamatsu', 'Advanced'),
(78, 'images/vegapunk.jpg', 'vegapunk', 'Kanjuro', 'vegapunk', 'Advanced'),
(79, 'images/Kanjuro.jpg', 'Kanjuro', 'Raizo', 'Kanjuro', 'Advanced'),
(80, 'images/Raizo.jpg', 'Raizo', 'katakuri', 'Raizo', 'Advanced'),
(81, 'images/katakuri.jpg', 'katakuri', 'otama', 'katakuri', 'Advanced'),
(82, 'images/otama.jpg', 'otama', 'Orochi', 'otama', 'Advanced'),
(83, 'images/Orochi.jpg', 'Orochi', 'Kaido', 'Orochi', 'Advanced'),
(84, 'images/kaidou.jpg', 'Kaido', 'King', 'Kaido', 'Advanced'),
(85, 'images/King.jpg', 'King', 'Queen', 'King', 'Advanced'),
(86, 'images/Queen.jpg', 'Queen', 'Jack', 'Queen', 'Advanced'),
(88, 'images/bigmom.jpg', 'Big Mom', 'Kaido', 'Big Mom', 'Advanced'),
(89, 'images/loki.jpg', 'Loki', 'Loki', 'Broggy', 'Advanced');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `id` int(11) NOT NULL,
  `player_id` int(11) DEFAULT NULL,
  `score` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`id`, `player_id`, `score`, `timestamp`) VALUES
(1, 1, 7, '2025-02-10 06:16:03'),
(2, 2, 4, '2025-02-10 06:41:04'),
(3, 3, 3, '2025-02-10 06:41:53'),
(4, 4, 4, '2025-02-10 06:45:13'),
(5, 5, 4, '2025-02-10 06:51:24'),
(6, 5, 3, '2025-02-10 06:54:46'),
(7, 5, 3, '2025-02-10 07:05:56'),
(8, 5, 6, '2025-02-10 07:32:00'),
(9, 5, 5, '2025-02-10 07:39:04'),
(10, 1, 5, '2025-02-10 07:40:32'),
(11, 1, 3, '2025-02-10 07:40:47'),
(12, 6, 3, '2025-02-10 07:41:26'),
(13, 6, 5, '2025-02-10 07:41:48'),
(14, 6, 2, '2025-02-10 07:42:07'),
(15, 7, 4, '2025-02-10 08:34:36'),
(16, 7, 4, '2025-02-10 08:35:06'),
(17, 7, 3, '2025-02-10 08:35:17'),
(18, 7, 5, '2025-02-10 08:35:42'),
(19, 8, 4, '2025-02-10 09:11:01'),
(20, 1, 3, '2025-02-10 09:27:00'),
(21, 5, 4, '2025-02-10 16:05:48'),
(22, 5, 4, '2025-02-11 15:05:05'),
(23, 5, 4, '2025-02-11 15:29:44'),
(24, 5, 4, '2025-02-11 15:37:43'),
(25, 5, 3, '2025-02-11 16:03:25'),
(26, 5, 10, '2025-02-11 17:54:54'),
(27, 5, 8, '2025-02-11 17:55:22'),
(28, 5, 8, '2025-02-11 18:52:24'),
(29, 5, 8, '2025-02-11 19:20:33'),
(30, 5, 7, '2025-02-11 19:26:29');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game_history`
--
ALTER TABLE `game_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `player_id` (`player_id`);

--
-- Indexes for table `players`
--
ALTER TABLE `players`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`id`),
  ADD KEY `player_id` (`player_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `game_history`
--
ALTER TABLE `game_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `players`
--
ALTER TABLE `players`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `game_history`
--
ALTER TABLE `game_history`
  ADD CONSTRAINT `game_history_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
