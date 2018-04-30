## [配置参考](server/config-repo/jpa.yml)

## 字符集
数据库字符集建议用utf8mb4,不然最常见的问题就是保存表情文字😊会报错
代码配置文件中的characterEncoding可以直接写utf8

## 连接池
### druid
访问地址: `/druid`

## [自动生成时间和修改者](https://www.jianshu.com/p/14cb69646195)
### 使用
- 首先申明实体类，需要在类上加上注解@EntityListeners(AuditingEntityListener.class)，
- 其次在application启动类中加上注解EnableJpaAuditing
- 同时在需要的字段上加上@CreatedDate、@CreatedBy、@LastModifiedDate、@LastModifiedBy等注解。
- CreatedBy和LastModifiedBy另外处理,见blog