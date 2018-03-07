# SpringBoot + SpringCloud 实践

## 目录

- **common** : 被依赖的模块,非单独启动的服务
    - **[base-server](common/base-server/README.md)**   : 一般的微服务都需要依赖此模块
    - **[basis](common/basis/README.md)**   : 
    - **redis**
    - **rocketmq**
    - **shiro**
    - **web**
- **server**
    - **config-repo** : 配置文件
    - **[config-server](server/config-server/README.md)** : 配置服务,微服务从此服务中获取配置
    - **discovery** : 服务治理(Eureka) 
    - **web-service** : 没用上,测试的
- **model-test** : 用来练习和测试的模块
    - **ECS** : 模拟被请求的服务
    - **ECC** : 模拟发送请求的服务

## 约定
- **模块的默认配置文件为application.yml** 
- hosts文件增加:
    ```
    127.0.0.1 std-eureka-server
    127.0.0.1 std-config-server
    ```

## 打包+Docker部署
### [打包](http://blog.csdn.net/Ser_Bad/article/details/78433340)
### [Docker部署](http://blog.csdn.net/u011699931/article/details/70226504)

## 总结
- [注解](Annotation.md)

## 注意

### 微服务通用配置
- **application启动类中**
    1. 增加EnableDiscoveryClient注解,声明这是一个Eureka Client
    2. @EnableDiscoveryClient 等同于bootstrap.yml中的spring.cloud.config.discoveryenabled: true
- **向Eureka服务器进行服务注册**
    ```yaml
    #向Eureka服务器进行服务注册，这里可以声明多个Eureka服务器
    eureka:
        client:
          service-url:
            defaultZone: http://localhost:18001/eureka
    ```
  
- **ribbon**  微服务默认使用ribbon负载均衡,在base-server模块pom中已经引入
- **[config](common/base-server/README.md)**  在base-server模块pom中已经引入

## springBoot版本升级注意事项:
- common/basis模块下的org.springframework.http.HttpStatus文件检查下是否需要更新