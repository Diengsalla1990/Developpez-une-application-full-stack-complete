-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Lun 19 Mai 2025 à 09:47
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `mdd`
--

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

CREATE TABLE IF NOT EXISTS `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `title` varchar(60) NOT NULL,
  `theme_id` bigint(20) NOT NULL,
  `auteur_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgpufqukmfnet3mw61ylcn54xx` (`theme_id`),
  KEY `FKwr3gq2ua2mdyit2c4appklfh` (`auteur_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `article`
--

INSERT INTO `article` (`id`, `content`, `created_at`, `title`, `theme_id`, `auteur_id`) VALUES
(1, 'Content of JavaScript Article', '2025-04-30 19:22:30.000000', 'JavaScript article', 1, 1),
(2, 'Content of Java Article', '2025-04-30 19:22:30.000000', 'Java article', 2, 1),
(3, 'Content of Python Article', '2025-04-30 19:22:30.000000', 'Python Article', 3, 1),
(4, 'Content of Go Article', '2025-04-30 19:22:30.000000', 'Go Article', 4, 1),
(5, 'contient Intelligence artificiel', '2025-05-01 16:31:45.105679', 'Intelligence artificiel', 1, 4),
(6, 'contient Intelligence artificiel', '2025-05-01 19:57:17.375514', 'Intelligence artificiel', 1, 1),
(7, 'contenu Python programming', '2025-05-13 17:08:25.143662', 'Python Programming', 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

CREATE TABLE IF NOT EXISTS `commentaire` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `article_id` bigint(20) NOT NULL,
  `auteur_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpl8x2ccino75hr790mhtghbt9` (`article_id`),
  KEY `FKdrcox9f5gj76re4uoj7p5pwbu` (`auteur_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `commentaire`
--

INSERT INTO `commentaire` (`id`, `content`, `created_at`, `article_id`, `auteur_id`) VALUES
(1, 'Commentaire de JavaScript Article', '2025-04-30 20:02:26.000000', 1, 1),
(2, 'Ce ci est une nouveau ', '2025-05-01 20:25:20.534647', 1, 1),
(3, 'trés bon langage de programmation', '2025-05-15 00:00:00.000000', 7, 1),
(4, 'vous avez vu la nouvelle mis à jour', '2025-05-15 16:30:20.013860', 7, 1);

-- --------------------------------------------------------

--
-- Structure de la table `theme`
--

CREATE TABLE IF NOT EXISTS `theme` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkos4rdub1av4d5wt6wocsdb7t` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `theme`
--

INSERT INTO `theme` (`id`, `description`, `name`) VALUES
(1, 'Contenu du theme JavaScript', 'JavaScript'),
(2, 'Contenu du theme Java', 'Java'),
(3, 'Contenu du theme Python', 'Python'),
(4, 'Contenu du Go theme', 'Go');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `username` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `username`) VALUES
(1, 'dieng@gmail.com', '$2a$10$QYLxAI5qnvbYGubr/0SatOJigS62hoDyEskB3c8nk9qcgfq.GZ6cu', 'Diengsalla'),
(2, 'fatou@gmail.com', '$2a$10$RYpJQlK4utGH/QK3dQmubuVZh4uTQMBjMlaL2iNk50iLbxyyc8ANa', 'Fatoumata'),
(3, 'aissatou@gmail.com', '$2a$10$gUhwUFXBLdr4WFi/MFIAkezWmj.INxFFD/1iqJlcY.HC08GQEwyCO', 'Aissatou'),
(4, 'diengdev@gmail.com', '$2a$10$ZFVhix.ugle5G9AiPTWRJOE7B5Bfs5YFRIF8V8ykhyYe7kEkMKSRy', 'Ibrahim'),
(5, 'maradieng@gmail.com', '$2a$10$nbVfBv09690SN7ibV.5JreuX6zhz902rCYiwDBwvoHjKPhvQKvBde', 'Maradieng');

-- --------------------------------------------------------

--
-- Structure de la table `user_theme`
--

CREATE TABLE IF NOT EXISTS `user_theme` (
  `user_id` bigint(20) NOT NULL,
  `theme_id` bigint(20) NOT NULL,
  KEY `FKfl1hg5lrpt9v8pv6h5kwa0q46` (`theme_id`),
  KEY `FK6cte98k5upsi11ku35uc3hbua` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `user_theme`
--

INSERT INTO `user_theme` (`user_id`, `theme_id`) VALUES
(1, 2),
(1, 1),
(4, 2),
(4, 4);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `FKgpufqukmfnet3mw61ylcn54xx` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`),
  ADD CONSTRAINT `FKwr3gq2ua2mdyit2c4appklfh` FOREIGN KEY (`auteur_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `FKdrcox9f5gj76re4uoj7p5pwbu` FOREIGN KEY (`auteur_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKpl8x2ccino75hr790mhtghbt9` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`);

--
-- Contraintes pour la table `user_theme`
--
ALTER TABLE `user_theme`
  ADD CONSTRAINT `FK6cte98k5upsi11ku35uc3hbua` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKfl1hg5lrpt9v8pv6h5kwa0q46` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
