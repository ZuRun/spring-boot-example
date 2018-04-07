# 记录异常日志

CREATE TABLE `t_logger_exception` (
  `id`             BIGINT(20) NOT NULL                              AUTO_INCREMENT,
  `create_time`    DATETIME COMMENT '写入数据库的时间'                      DEFAULT NULL,
  `time`           DATETIME COMMENT '抛异常的时间'                        DEFAULT NULL,
  `cause_by`       VARCHAR(11255) COLLATE utf8mb4_bin               DEFAULT NULL,
  `error_code`     VARCHAR(255) COLLATE utf8mb4_bin COMMENT '异常码'   DEFAULT NULL,
  `error_message`  VARCHAR(255) COLLATE utf8mb4_bin COMMENT '错误信息'  DEFAULT NULL,
  `exception_name` VARCHAR(255) COLLATE utf8mb4_bin COMMENT '异常名'   DEFAULT NULL,
  `stack_trace`    MEDIUMTEXT COLLATE utf8mb4_bin COMMENT '异常堆栈信息',
  `server_name`    VARCHAR(255) COLLATE utf8mb4_bin COMMENT '服务名'   DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 514
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  COMMENT ='异常信息表，记录异常日志';

# 记录请求日志
CREATE TABLE `t_logger_request` (
  `id`                   BIGINT(20) NOT NULL                                                             AUTO_INCREMENT,
  `create_time`          DATETIME COMMENT '写入数据库的时间'                                                     DEFAULT NULL,
  `client_ip`            VARCHAR(255) COLLATE utf8mb4_bin COMMENT '客户端请求ip'                              DEFAULT NULL,
  `http_status_code`     VARCHAR(255) COLLATE utf8mb4_bin COMMENT '请求返回的httpStatusCode代码，如：200,400,404等' DEFAULT NULL,
  `local_addr`           VARCHAR(255) COLLATE utf8mb4_bin COMMENT '服务端地址,用来区分机器'                         DEFAULT NULL,
  `local_port`           INT(11) COMMENT '服务方端口号LocalPort,用来区分机器'                                        DEFAULT NULL,
  `method`               VARCHAR(255) COLLATE utf8mb4_bin COMMENT '请求方式method,post,get等'                 DEFAULT NULL,
  `param_data`           VARCHAR(255) COLLATE utf8mb4_bin COMMENT '请求参数内容,json'                          DEFAULT NULL,
  `request_create_time`  DATETIME COMMENT '请求时间'                                                         DEFAULT NULL,
  `response_return_time` DATETIME COMMENT '接口返回时间'                                                       DEFAULT NULL,
  `server_name`          VARCHAR(255) COLLATE utf8mb4_bin COMMENT '微服务名'                                 DEFAULT NULL,
  `session_id`           VARCHAR(255) COLLATE utf8mb4_bin COMMENT 'SessionId'                            DEFAULT NULL,
  `time_consuming`       INT(11) COMMENT '请求耗时'                                                          DEFAULT NULL,
  `type`                 VARCHAR(255) COLLATE utf8mb4_bin COMMENT '终端请求方式,普通请求,ajax请求'                   DEFAULT NULL,
  `uri`                  VARCHAR(255) COLLATE utf8mb4_bin COMMENT '客户端请求路径'                              DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 186
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  COMMENT ='记录请求日志';