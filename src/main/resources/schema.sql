CREATE TABLE IF NOT EXISTS `plots` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `attempts_to_recall` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `period` bigint NOT NULL,
  `recalled_attempts` int NOT NULL,
  `sensor_status` bit(1) NOT NULL,
  `slot` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
