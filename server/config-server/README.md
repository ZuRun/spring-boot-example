# Spring Cloud Config Server,配置管理中心

## [启用加密](https://segmentfault.com/a/1190000011680775)
- [Oracle官网下载JCE](http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html)
- 将两个jar文件放到%JRE_HOME%\lib\security文件目录下
> 如果安装了jdk，也将两个jar文件放到%JDK_HOME%\jre\lib\security文件目录下。(jdk8环境)
> mac路径:`/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre/lib/security
### 对称加密(目前使用这个)
- 密钥需要配置在bootstrap中,application中无效
`encrypt.key=密钥`
- 访问 ip:port/encrypt/status,查看是否搭建成功
- 配置文件的值如果是以{cipher}开头，表示该值是一个加密字符.
- 注意,加密字符在yml中需要加单引号,properties没有单引号
- 获取加密字符或者解密

访问/encrypt 和/decrypt接口,使用post方法

## 优先级
- 以redis说明

redis.properties中配置为默认值,如果redis-dev.properties中也存在,则以redis-dev中为准
## 模块中使用
- 添加依赖 base-server(依赖添加在此模块中了)

## 客户端中bootstrap.yml模板
```yaml
# 更改server.port,application.name,cloud.config.name 即可

server:
  port: 19011
spring:
  application:
    # spring.application.name必须要设置，服务消费者将通过该名称调用所提供的服务。
    name: eureka-client-consumer
  cloud:
    # 使用配置中心
    config:
      # 配置了name,则配置文件名为:name-profile.yml/properties
      name: ecc,redis
      # 开发环境
      profile: ${spring.profiles.active:dev}
      # 分支
      label: master
      discovery:
        enabled: true
        serviceId: std-config-server-1
      failFast: true
      retry:
        initialInterval: 10000
        multiplier: 2
        maxInterval: 60000
        maxAttempts: 10


# 所有微服务通用配置
# eureka.client.service-url也必须设置，表示我们要向那些Eureka服务器进行服务注册，这里可以声明多个Eureka服务器
eureka:
  client:
    service-url:
      defaultZone: http://localhost:18001/eureka
```