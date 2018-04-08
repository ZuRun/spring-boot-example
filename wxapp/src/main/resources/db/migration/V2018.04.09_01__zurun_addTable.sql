# 微信用户表
CREATE TABLE `t_user` (
  `id`      BIGINT(20)   NOT NULL  AUTO_INCREMENT,
  user_name VARCHAR(255) NOT NULL,
  password  VARCHAR(255) NOT NULL,
  phone     VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  COMMENT ='微信用户表';