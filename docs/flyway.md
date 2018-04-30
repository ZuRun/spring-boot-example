# flyway，数据库版本控制

## 约定
- 默认路径

默认放在resource下的db.migration文件夹中
- 命名规则

V{version_number}__{description}的格式命名
> eg. V2018.03.26_01__zurun_addTable.sql

- version_number必须按增序添加
> flyway每次执行会检查所有的migration脚本
> 如果发现有未执行但是version_number早于已执行过的脚本存在，就会抛出异常。

## 如果是已经开发一段时间的项目需要开启 baselineOnMigrate 否则抛出异常
 ```
 Caused by: org.flywaydb.core.api.FlywayException: Found non-empty schema `dev-basis` without metadata table! Use baseline() or set baselineOnMigrate to true to initialize the metadata table.
 ```

 需要在 application.properties 添加配置
`flyway.baselineOnMigrate=true`
