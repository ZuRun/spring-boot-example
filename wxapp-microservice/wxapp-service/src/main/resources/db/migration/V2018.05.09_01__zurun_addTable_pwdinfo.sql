# 用户的密码管理-用户的密码信息的密文
CREATE TABLE `t_wxapp_password_info` (
  `id`      BIGINT   NOT NULL  AUTO_INCREMENT,
  `create_time`    DATETIME COMMENT '创建时间'      DEFAULT NULL,
  `modified_time`  DATETIME COMMENT '修改时间'      DEFAULT NULL,

  `openid`     VARCHAR(255)   COMMENT '用户身份id'   NOT NULL,
  `name`  VARCHAR(255)   COMMENT '用户昵称'  NOT NULL  DEFAULT '',
  `cipher_text`  TEXT  COMMENT '密文'  NOT NULL ,
  `salt`  VARCHAR(255)   COMMENT '随机盐'  NOT NULL  DEFAULT '',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  COMMENT ='密码管理-之-密码信息';