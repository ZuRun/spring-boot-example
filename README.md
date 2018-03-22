# SpringBoot + SpringCloud 实践

## 目录

- **api-gateway** API网关
- **[basis-service](basis-service/README.md)** : 打杂的服务
- **common** : 被依赖的模块,非单独启动的服务
    - **[base-server](common/base-server/README.md)**   : 一般的微服务都需要依赖此模块
    - **[basis](common/basis/README.md)**   : 
    - **email** : 发送邮件服务,建议需要发送邮件的都通过消息队列,由指定的服务统一发送邮件
    - **[jpa](common/jpa/README.md)** : hibernate,使用了druid连接池
    - **redis**
    - **[rocketmq](common/rocketmq/README.md)** : 阿里的消息队列
    - **shiro**
    - **web**
- **server**
    - **config-repo** : 配置文件
    - **[config-server](server/config-server/README.md)** : 配置服务,微服务从此服务中获取配置
    - **discovery** : 服务治理(eureka-server) 
    - **web-service** : 没用上,测试的
- **model-test** : 用来练习和测试的模块
    - **eureka-client-provider** : 模拟 服务提供方
    - **eureka-client-consumer** : 模拟 服务消费者

## 开始使用
### 本项目集成了[lombok](https://segmentfault.com/a/1190000011433462),让代码更简洁
- ide需自行安装lombok插件
- 常见使用场景:实体中使用@Data注解在类上,不需要getter、setter、toString方法了

### 配置文件
- 项目使用了配置中心,路径为:server/config-repo文件夹

>  一般开发环境使用本地配置 , 生产或测试环境使用git/svn仓库,配置见application.yml(application-test.yml)

- [配置加密](server/config-server/README.md)

> 处于安全考虑,配置文件中密码等重要信息需要加密,配置中心的密钥也不建议直接放在配置文件中
> 本项目密钥留空,项目启动的时候以启动参数的形式将密钥赋值
>    生产环境可以使用脚本
>    开发环境可以在dashboard中指定参数

## 约定
- **模块的默认配置文件为application.yml** 
- hosts文件增加:
    ```
    127.0.0.1 std-eureka-server
    ```
  因为注册比较慢,开发环境可以将所有serviceName都加到hosts中
- Assert
    项目中的断言使用org.springframework.util.Assert,原生的在生产环境会默认忽略

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
    #向Eureka服务器进行服务注册，这里可以声明多个Eureka服务器,用逗号隔开
    eureka:
        client:
          service-url:
            defaultZone: http://localhost:18001/eureka
    ```
  
- **ribbon**  微服务默认使用ribbon负载均衡,在base-server模块pom中已经引入依赖,并在RestConfig中加入@LoadBalanced注解
- **[config](common/base-server/README.md)**  在base-server模块pom中已经引入

## springBoot版本升级注意事项:
