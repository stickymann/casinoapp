-- MySQL dump 10.13  Distrib 5.5.28, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: casino
-- ------------------------------------------------------
-- Server version	5.5.28-0ubuntu0.12.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pokegames`
--

DROP TABLE IF EXISTS `pokegames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pokegames` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` varchar(50) NOT NULL,
  `table_id` varchar(30) NOT NULL,
  `player_total` int(3) NOT NULL,
  `playtime` int(11) NOT NULL,
  `result` varchar(256) NOT NULL,
  `winner` varchar(50) NOT NULL,
  `winning_hand` varchar(50) NOT NULL,
  `input_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokegames`
--

LOCK TABLES `pokegames` WRITE;
/*!40000 ALTER TABLE `pokegames` DISABLE KEYS */;
/*!40000 ALTER TABLE `pokegames` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pokertables`
--

DROP TABLE IF EXISTS `pokertables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pokertables` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `maxplayers` int(3) NOT NULL DEFAULT '10',
  `maxplaytime` int(11) DEFAULT '10000',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_table_id` (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokertables`
--

LOCK TABLES `pokertables` WRITE;
/*!40000 ALTER TABLE `pokertables` DISABLE KEYS */;
INSERT INTO `pokertables` VALUES (1,'ANGELWING','Angel Wing - Texas Hold\' Em',10,10000),(2,'APPLEROSE','Apple Rose - Texas Hold\' Em',10,10000),(3,'BLUEDAWN','Blue Dawn - Texas Hold\' Em',10,10000),(4,'CALADIUM','Caladium - Texas Hold\' Em',10,10000),(5,'CONSTANIA','Constania - Texas Hold\' Em',10,10000),(6,'DAHLIA','Dahlia - Texas Hold\' Em',10,10000),(7,'HELICONIA','Heliconia - Texas Hold\' Em',10,10000),(8,'IRONWOOD','Ironwood - Texas Hold\' Em',10,10000),(9,'JUNIPER','Juniper - Texas Hold\' Em',10,10000),(10,'KUMAKANI','Kumkani - Texas Hold\' Em',10,10000);
/*!40000 ALTER TABLE `pokertables` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-09  3:05:10
