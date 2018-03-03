# SpringBoot + SpringCloud 实践

## 目录

- **common** : 被依赖的模块,非单独启动的服务
    - **[basis](common/basis/README.md)**   : 默认所有模块都会依赖,所以通用的配置直接放在这
    - **redis**
    - **rocketmq**
    - **shiro**
    - **web**
- **server**
    - **discovery** : 服务治理(Eureka) 
    - **web**
- **model-test** : 用来练习和测试的模块
    - **ECS** : 模拟被请求的服务
    - **ECC** : 模拟发送请求的服务

## 约定
- **模块的默认配置文件为application.yml** 

## 总结
- [注解](Annotation.md)

## 注意

### 微服务通用配置
- **application启动类中** 增加EnableDiscoveryClient注解,声明这是一个Eureka Client
- **向Eureka服务器进行服务注册**
    ```yaml
    #向Eureka服务器进行服务注册，这里可以声明多个Eureka服务器
    eureka:
        client:
          service-url:
            defaultZone: http://localhost:18001/eureka
    ```
  
- **ribbon**  微服务默认使用ribbon负载均衡,在basis模块pom中已经引入
- **config**  在basis模块pom中已经引入

## springBoot版本升级注意事项:
- common/basis模块下的org.springframework.http.HttpStatus文件检查下是否需要更新