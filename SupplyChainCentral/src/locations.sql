-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 15, 2014 at 05:05 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `supplychaincentral`
--

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE IF NOT EXISTS `locations` (
  `locationCode` varchar(5) NOT NULL COMMENT 'primary key for locations',
  `locationType` int(11) NOT NULL COMMENT 'foreign key for locationtypes',
  `address` varchar(100) NOT NULL COMMENT 'address',
  `zip` int(5) NOT NULL COMMENT 'zip',
  `city` varchar(30) NOT NULL COMMENT 'city',
  `state` varchar(20) NOT NULL COMMENT 'state',
  `GPScoords` point DEFAULT NULL COMMENT 'GPS coords of a given location',
  PRIMARY KEY (`locationCode`),
  KEY `locationType` (`locationType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`locationCode`, `locationType`, `address`, `zip`, `city`, `state`, `GPScoords`) VALUES
('DET', 1, '4 Back Road', 33833, 'Detroit', 'Michigan', '\0\0\0\0\0\0\0\0\0\0\0\0ÀT@\0\0\0\0\0\0E@'),
('LA', 1, '803 Sunshine Place', 88888, 'Los Angeles', 'California', '\0\0\0\0\0\0\0\0\0\0\0\0€]@\0\0\0\0\0\0A@'),
('NY', 1, '199 Production Pkwy.', 12345, 'New York', 'New York', '\0\0\0\0\0\0\0\0\0\0\0\0€R@\0\0\0\0\0\0D@'),
('PHX', 1, '796 Cactus Way', 77771, 'Phoenix', 'Arizona', '\0\0\0\0\0\0\0\0\0\0\0\0\0\\@\0\0\0\0\0€@@'),
('PORT', 1, '26 Rainy St.', 67849, 'Portland', 'Oregon', '\0\0\0\0\0\0\0\0\0\0\0\0€^@\0\0\0\0\0€F@'),
('SAV', 1, '420 Commerce Blvd.', 31419, 'Savannah', 'Georgia', '\0\0\0\0\0\0\0\0\0\0\0\0@T@\0\0\0\0\0\0@@');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
