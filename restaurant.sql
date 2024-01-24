-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 24 jan. 2024 à 09:16
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `restaurant`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `idCli` int(11) NOT NULL,
  `telCli` varchar(20) DEFAULT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `nomSociete` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`idCli`, `telCli`, `nom`, `prenom`, `nomSociete`) VALUES
(1, '06 12 34 56 00', 'ECHANIZ', 'Valentin', NULL),
(2, '07 23 45 67 01', 'BUFFIER', 'Alexis', NULL),
(16, '06 56 54 56 46', NULL, NULL, 'Hydro'),
(19, '06 25 87 46 95', 'Smith', 'Jane', NULL),
(20, '06 85 63 25 74', 'Mustermann', 'Max', NULL),
(21, '06 58 31 25 46', NULL, NULL, 'eRegroupe'),
(27, '06 25 14 15 47', NULL, NULL, 'ESN81'),
(28, '05 14 59 25 14', 'Dupont', 'Marc', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `reserver`
--

CREATE TABLE `reserver` (
  `id` int(11) NOT NULL,
  `idreservation` int(11) NOT NULL,
  `idTable` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reserver`
--

INSERT INTO `reserver` (`id`, `idreservation`, `idTable`) VALUES
(1, 2, 14),
(3, 6, 15),
(4, 6, 18),
(5, 6, 20),
(22, 14, 14),
(23, 14, 15),
(24, 14, 18),
(31, 17, 14),
(32, 17, 15),
(33, 17, 18),
(34, 18, 20),
(35, 18, 21),
(36, 18, 22),
(53, 27, 20),
(54, 27, 21),
(55, 28, 14),
(56, 28, 15),
(57, 28, 18),
(58, 29, 20),
(59, 29, 21),
(68, 34, 22),
(69, 34, 23);

-- --------------------------------------------------------

--
-- Structure de la table `réservation`
--

CREATE TABLE `réservation` (
  `idreservation` int(11) NOT NULL,
  `idCli` int(11) NOT NULL,
  `dateReservation` date NOT NULL,
  `nbpersonne` int(1) NOT NULL DEFAULT 1,
  `heureReservation` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `réservation`
--

INSERT INTO `réservation` (`idreservation`, `idCli`, `dateReservation`, `nbpersonne`, `heureReservation`) VALUES
(2, 1, '2024-01-22', 2, '12h10'),
(6, 2, '2024-01-22', 5, '12h00'),
(14, 16, '2024-01-23', 5, '12h00'),
(17, 16, '2024-01-24', 6, '12h00'),
(18, 16, '2024-01-24', 6, '12h00'),
(27, 27, '2024-01-23', 3, '12h00'),
(28, 28, '2024-01-26', 6, '12h30'),
(29, 2, '2024-01-26', 3, '12h30'),
(34, 1, '2024-01-26', 4, '12h50');

-- --------------------------------------------------------

--
-- Structure de la table `tabler`
--

CREATE TABLE `tabler` (
  `idTable` int(11) NOT NULL,
  `nbchaise` int(2) NOT NULL,
  `numeroTable` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `tabler`
--

INSERT INTO `tabler` (`idTable`, `nbchaise`, `numeroTable`) VALUES
(14, 2, 1),
(15, 2, 2),
(18, 2, 3),
(20, 2, 4),
(21, 2, 5),
(22, 2, 6),
(23, 2, 7);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`idCli`);

--
-- Index pour la table `reserver`
--
ALTER TABLE `reserver`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reserver_ibfk_2` (`idTable`),
  ADD KEY `idreservation` (`idreservation`) USING BTREE;

--
-- Index pour la table `réservation`
--
ALTER TABLE `réservation`
  ADD PRIMARY KEY (`idreservation`),
  ADD KEY `idCli` (`idCli`);

--
-- Index pour la table `tabler`
--
ALTER TABLE `tabler`
  ADD PRIMARY KEY (`idTable`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `idCli` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT pour la table `reserver`
--
ALTER TABLE `reserver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT pour la table `réservation`
--
ALTER TABLE `réservation`
  MODIFY `idreservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT pour la table `tabler`
--
ALTER TABLE `tabler`
  MODIFY `idTable` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `reserver`
--
ALTER TABLE `reserver`
  ADD CONSTRAINT `reserver_ibfk_1` FOREIGN KEY (`idreservation`) REFERENCES `réservation` (`idreservation`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reserver_ibfk_2` FOREIGN KEY (`idTable`) REFERENCES `tabler` (`idTable`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `réservation`
--
ALTER TABLE `réservation`
  ADD CONSTRAINT `réservation_ibfk_1` FOREIGN KEY (`idCli`) REFERENCES `client` (`idCli`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
