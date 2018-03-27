# 测试

DROP TABLE IF EXISTS `cat4` ;
CREATE TABLE `cat4` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_age` int(11) NOT NULL,
  `cat_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin