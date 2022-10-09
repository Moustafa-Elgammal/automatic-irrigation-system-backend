TRUNCATE `plots`;
--
-- Dumping data for table `plots`
--
LOCK TABLES `plots` WRITE;
/*!40000 ALTER TABLE `plots` DISABLE KEYS */;
INSERT INTO `plots` VALUES
(1,10,5,'plot broken sensor',23,3,0,'2022-10-05 15:59:00.000000'),
(2,1,5,'plot need to be irrigate soon',45, 0, 0,'2022-10-09 19:55:00.000000'),
(3,15,10,'plot good for next irrigate',10, 2, 1,'2022-10-04 19:56:00.000000'),
(4,15,10,'plot irrigated',200, 0, 1,'2023-12-04 19:56:00.000000');
/*!40000 ALTER TABLE `plots` ENABLE KEYS */;
UNLOCK TABLES;