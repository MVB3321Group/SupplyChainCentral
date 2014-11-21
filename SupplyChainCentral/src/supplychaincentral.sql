-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 15, 2014 at 05:34 PM
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
-- Table structure for table `inventory`
--

CREATE TABLE IF NOT EXISTS `inventory` (
  `locationCode` varchar(5) NOT NULL COMMENT 'inventory location',
  `productID` int(11) NOT NULL COMMENT 'product',
  `amount` int(11) NOT NULL COMMENT 'amount of product',
  PRIMARY KEY (`locationCode`,`productID`),
  KEY `fk_inventoryProducts` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Contains inventory levels by location';

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
('DET', 1, '4 Back Road', 33833, 'Detroit', 'Michigan', '\0\0\0\0\0\0\0\0\0\0\0\0�T@\0\0\0\0\0\0E@'),
('LA', 1, '803 Sunshine Place', 88888, 'Los Angeles', 'California', '\0\0\0\0\0\0\0\0\0\0\0\0�]@\0\0\0\0\0\0A@'),
('NY', 1, '199 Production Pkwy.', 12345, 'New York', 'New York', '\0\0\0\0\0\0\0\0\0\0\0\0�R@\0\0\0\0\0\0D@'),
('PHX', 1, '796 Cactus Way', 77771, 'Phoenix', 'Arizona', '\0\0\0\0\0\0\0\0\0\0\0\0\0\\@\0\0\0\0\0�@@'),
('PORT', 1, '26 Rainy St.', 67849, 'Portland', 'Oregon', '\0\0\0\0\0\0\0\0\0\0\0\0�^@\0\0\0\0\0�F@'),
('SAV', 1, '420 Commerce Blvd.', 31419, 'Savannah', 'Georgia', '\0\0\0\0\0\0\0\0\0\0\0\0@T@\0\0\0\0\0\0@@');

-- --------------------------------------------------------

--
-- Stand-in structure for view `locationsview`
--
CREATE TABLE IF NOT EXISTS `locationsview` (
`locationCode` varchar(5)
,`X(GPScoords)` double
,`Y(GPScoords)` double
);
-- --------------------------------------------------------

--
-- Table structure for table `locationtypes`
--

CREATE TABLE IF NOT EXISTS `locationtypes` (
  `locTypeID` int(11) NOT NULL COMMENT 'location type id',
  `description` varchar(50) NOT NULL COMMENT 'description'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `priorityrequests`
--

CREATE TABLE IF NOT EXISTS `priorityrequests` (
  `employeeID` int(11) NOT NULL COMMENT 'user id',
  `shipID` int(11) NOT NULL COMMENT 'shipment id',
  `priority` int(11) NOT NULL COMMENT 'requested priority',
  PRIMARY KEY (`employeeID`,`shipID`),
  KEY `fk_PriorityShipments` (`shipID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='stores requests for priority for viewing and approval by manager';

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `pName` varchar(30) NOT NULL COMMENT 'description of product',
  `productID` int(11) NOT NULL COMMENT 'primary key',
  `height` double NOT NULL,
  `length` double NOT NULL,
  `width` double NOT NULL,
  `weight` double NOT NULL,
  PRIMARY KEY (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='contains products shipped by the corporation';

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`pName`, `productID`, `height`, `length`, `width`, `weight`) VALUES
('foobar', 1, 10, 10, 10, 20),
('widget', 2, 5, 5, 20, 16),
('gizmo', 3, 14, 7, 15, 26),
('jetpack', 4, 2, 0.5, 0.25, 44);

-- --------------------------------------------------------

--
-- Table structure for table `productsshipped`
--

CREATE TABLE IF NOT EXISTS `productsshipped` (
  `detailID` int(11) NOT NULL AUTO_INCREMENT,
  `shipID` int(11) NOT NULL COMMENT 'foreign key to shipments (pending and current)',
  `productID` int(11) NOT NULL COMMENT 'foreign key to products',
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`detailID`),
  KEY `shipmentID` (`shipID`,`productID`),
  KEY `fk_ProductshipProducts` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='associates products with shipments' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `roleID` int(11) NOT NULL COMMENT 'uniquely identifies role',
  `roleName` varchar(30) NOT NULL COMMENT 'role name',
  PRIMARY KEY (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Contains role names for supply chain app';

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`roleID`, `roleName`) VALUES
(1, 'Supply Chain Manager'),
(2, 'Material Planner'),
(3, 'Warehouse Manager'),
(4, 'Dispatcher');

-- --------------------------------------------------------

--
-- Stand-in structure for view `roles_and_names`
--
CREATE TABLE IF NOT EXISTS `roles_and_names` (
`lName` varchar(20)
,`roleName` varchar(30)
);
-- --------------------------------------------------------

--
-- Table structure for table `schedules`
--

CREATE TABLE IF NOT EXISTS `schedules` (
  `scheduleID` int(11) NOT NULL COMMENT 'to associate schedules with shipments',
  `creationDate` datetime NOT NULL COMMENT 'schedule''s creation time',
  PRIMARY KEY (`scheduleID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `shipments`
--

CREATE TABLE IF NOT EXISTS `shipments` (
  `shipID` int(11) NOT NULL AUTO_INCREMENT,
  `originatorID` int(11) NOT NULL COMMENT 'User who originated the shipment.',
  `origin` varchar(5) NOT NULL COMMENT 'foreign key to locations',
  `destination` varchar(5) NOT NULL COMMENT 'foreign key to locations',
  `priority` int(11) NOT NULL COMMENT 'range from 1 to 5',
  `scheduleID` int(11) DEFAULT NULL COMMENT 'If null, the shipment is pending, else it is scheduled',
  `startTime` datetime DEFAULT NULL COMMENT 'null if pending',
  `endTime` datetime DEFAULT NULL COMMENT 'null if pending or in progress',
  `currentLocation` varchar(100) DEFAULT NULL COMMENT 'null if pending, may be gps coords or lat/long',
  `ETA` datetime DEFAULT NULL COMMENT 'can be null, but preferred to have a value',
  PRIMARY KEY (`shipID`),
  KEY `origin` (`origin`,`destination`),
  KEY `location` (`currentLocation`),
  KEY `fk_ShipmentsSchedules` (`scheduleID`),
  KEY `fk_ShipmentsDestinations` (`destination`),
  KEY `fk_ShipmentsUsers` (`originatorID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `shipments`
--

INSERT INTO `shipments` (`shipID`, `originatorID`, `origin`, `destination`, `priority`, `scheduleID`, `startTime`, `endTime`, `currentLocation`, `ETA`) VALUES
(1, 2222, 'LA', 'DET', 4, NULL, NULL, NULL, NULL, NULL),
(2, 2222, 'SAV', 'PORT', 5, NULL, NULL, NULL, NULL, NULL),
(3, 2222, 'NY', 'LA', 5, NULL, NULL, NULL, NULL, NULL),
(4, 2222, 'PHX', 'LA', 3, NULL, NULL, NULL, NULL, NULL),
(5, 2222, 'LA', 'DET', 5, NULL, NULL, NULL, NULL, NULL),
(6, 2222, 'NY', 'LA', 5, NULL, NULL, NULL, NULL, NULL),
(7, 2222, 'LA', 'NY', 1, NULL, NULL, NULL, NULL, NULL),
(8, 2222, 'NY', 'LA', 5, NULL, NULL, NULL, NULL, NULL),
(9, 2222, 'SAV', 'PHX', 3, NULL, NULL, NULL, NULL, NULL),
(10, 2222, 'PHX', 'DET', 1, NULL, NULL, NULL, NULL, NULL),
(11, 2223, 'PORT', 'SAV', 5, NULL, NULL, NULL, NULL, NULL),
(12, 2223, 'SAV', 'LA', 1, NULL, NULL, NULL, NULL, NULL),
(13, 2223, 'SAV', 'PHX', 5, NULL, NULL, NULL, NULL, NULL),
(14, 2223, 'PHX', 'LA', 5, NULL, NULL, NULL, NULL, NULL),
(15, 2223, 'PHX', 'NY', 4, NULL, NULL, NULL, NULL, NULL),
(16, 2223, 'LA', 'PHX', 3, NULL, NULL, NULL, NULL, NULL),
(17, 2223, 'PHX', 'SAV', 3, NULL, NULL, NULL, NULL, NULL),
(18, 2223, 'SAV', 'DET', 3, NULL, NULL, NULL, NULL, NULL),
(19, 2223, 'LA', 'SAV', 3, NULL, NULL, NULL, NULL, NULL),
(20, 2223, 'SAV', 'LA', 1, NULL, NULL, NULL, NULL, NULL),
(21, 2222, 'LA', 'PHX', 3, NULL, NULL, NULL, NULL, NULL),
(22, 2222, 'PHX', 'SAV', 2, NULL, NULL, NULL, NULL, NULL),
(23, 2222, 'LA', 'PHX', 3, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `shippingunits`
--

CREATE TABLE IF NOT EXISTS `shippingunits` (
  `unitID` int(7) NOT NULL COMMENT 'Unique id for shipping unit',
  `homeLocation` varchar(5) NOT NULL COMMENT 'Shipping unit''s base location',
  `capacity` int(11) NOT NULL COMMENT 'capacity of storage unit',
  PRIMARY KEY (`unitID`),
  KEY `fk_ShippingunitsLocations` (`homeLocation`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `fName` varchar(20) NOT NULL COMMENT 'user first name',
  `lName` varchar(20) NOT NULL COMMENT 'user last name',
  `employeeID` int(11) NOT NULL COMMENT 'user employee id number',
  `managerID` int(11) DEFAULT NULL COMMENT 'user manager employee id',
  `roleID` int(11) NOT NULL COMMENT 'foreign key to roles relation',
  `locationCode` varchar(6) NOT NULL COMMENT 'employee location',
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`employeeID`),
  KEY `roleID` (`roleID`) COMMENT 'allows foreign key to roleID',
  KEY `fk_UsersLocations` (`locationCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Employees for the supply chain optimization application';

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`fName`, `lName`, `employeeID`, `managerID`, `roleID`, `locationCode`, `password`) VALUES
('Bob', 'Wilderness', 1111, NULL, 1, 'SAV', 'bwilder'),
('James', 'Jones', 2222, 1111, 2, 'NY', 'password'),
('Jerry', 'Williamson', 2223, 1111, 2, 'SAV', 'abc123'),
('George', 'Parker', 3333, 1111, 3, 'LA', 'gpark'),
('Fred', 'Smith', 4444, 3333, 4, 'LA', 'fsmith');

-- --------------------------------------------------------

--
-- Structure for view `locationsview`
--
DROP TABLE IF EXISTS `locationsview`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `locationsview` AS select `locations`.`locationCode` AS `locationCode`,st_x(`locations`.`GPScoords`) AS `X(GPScoords)`,st_y(`locations`.`GPScoords`) AS `Y(GPScoords)` from `locations`;

-- --------------------------------------------------------

--
-- Structure for view `roles_and_names`
--
DROP TABLE IF EXISTS `roles_and_names`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `roles_and_names` AS select `users`.`lName` AS `lName`,`roles`.`roleName` AS `roleName` from (`users` join `roles`) where (`users`.`roleID` = `roles`.`roleID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `inventory`
--
ALTER TABLE `inventory`
  ADD CONSTRAINT `fk_inventoryLocations` FOREIGN KEY (`locationCode`) REFERENCES `locations` (`locationCode`),
  ADD CONSTRAINT `fk_inventoryProducts` FOREIGN KEY (`productID`) REFERENCES `products` (`productID`);

--
-- Constraints for table `priorityrequests`
--
ALTER TABLE `priorityrequests`
  ADD CONSTRAINT `fk_PriorityUsers` FOREIGN KEY (`employeeID`) REFERENCES `users` (`employeeID`);

--
-- Constraints for table `productsshipped`
--
ALTER TABLE `productsshipped`
  ADD CONSTRAINT `fk_ProductshipProducts` FOREIGN KEY (`productID`) REFERENCES `products` (`productID`);

--
-- Constraints for table `shipments`
--
ALTER TABLE `shipments`
  ADD CONSTRAINT `fk_ShipmentsDestinations` FOREIGN KEY (`destination`) REFERENCES `locations` (`locationCode`),
  ADD CONSTRAINT `fk_ShipmentsOrigins` FOREIGN KEY (`origin`) REFERENCES `locations` (`locationCode`),
  ADD CONSTRAINT `fk_ShipmentsSchedules` FOREIGN KEY (`scheduleID`) REFERENCES `schedules` (`scheduleID`),
  ADD CONSTRAINT `fk_ShipmentsUsers` FOREIGN KEY (`originatorID`) REFERENCES `users` (`employeeID`);

--
-- Constraints for table `shippingunits`
--
ALTER TABLE `shippingunits`
  ADD CONSTRAINT `fk_ShippingunitsLocations` FOREIGN KEY (`homeLocation`) REFERENCES `locations` (`locationCode`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_UsersLocations` FOREIGN KEY (`locationCode`) REFERENCES `locations` (`locationCode`),
  ADD CONSTRAINT `fk_UsersRoles` FOREIGN KEY (`roleID`) REFERENCES `roles` (`roleID`),
  ADD CONSTRAINT `roleConstraint` FOREIGN KEY (`roleID`) REFERENCES `roles` (`roleID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
