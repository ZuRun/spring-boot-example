# 构建Eureka Client 服务消费者

### 步骤
- pom
    - 依赖或者parent中依赖 SpringBoot和SpringCloud
    - 依赖spring-cloud-starter-eureka
    - 还要依赖spring-cloud-starter-ribbon,提供客户端负载均衡功能

- 启动类
    - 注解 @EnableDiscoveryClient，声明这是一个Eureka Client。
    
- 配置文件
    