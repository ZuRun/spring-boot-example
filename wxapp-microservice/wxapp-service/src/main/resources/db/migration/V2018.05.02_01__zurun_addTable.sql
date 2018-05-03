# 用户表
CREATE TABLE `t_wxapp_user` (
  `id`      BIGINT   NOT NULL  AUTO_INCREMENT,
  `create_time`    DATETIME COMMENT '创建时间'      DEFAULT NULL,
  `modified_time`  DATETIME COMMENT '修改时间'      DEFAULT NULL,

  `openid`     VARCHAR(255)   COMMENT '用户身份id'   NOT NULL  DEFAULT '',
  `unionid`  VARCHAR(255)   COMMENT 'unionid'  NOT NULL  DEFAULT '',
  `nickname`  VARCHAR(255)   COMMENT '用户昵称'  NOT NULL  DEFAULT '',
  `gender`  TINYINT   COMMENT '性别,1男性，2女性，0未知'  NOT NULL  DEFAULT 0,
  `language`  VARCHAR(255)   COMMENT '用户的语言'  NOT NULL  DEFAULT '',
  `province`  VARCHAR(255)   COMMENT '省份'  NOT NULL  DEFAULT '',
  `city`  VARCHAR(255)   COMMENT '城市'  NOT NULL  DEFAULT '',
  `country`  VARCHAR(255)   COMMENT '国家'  NOT NULL  DEFAULT '',
  `avatar_url`    VARCHAR(500)   COMMENT '用户头像url',
  PRIMARY KEY (`id`)
)
  ENGINE = INNODB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  COMMENT ='微信小程序用户表';