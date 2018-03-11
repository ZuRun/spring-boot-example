# 构建Eureka Client 服务提供者

### 步骤
- pom
    - 依赖或者parent中依赖 SpringBoot和SpringCloud
    - 依赖spring-cloud-starter-eureka

- 启动类
    - 注解 @EnableDiscoveryClient，声明这是一个Eureka Client。
    - @EnableDiscoveryClient 等同于bootstrap.yml中的spring.cloud.config.discoveryenabled: true
    
- 配置文件
    