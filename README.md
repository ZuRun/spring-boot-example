[![MIT License](https://img.shields.io/github/license/mashape/apistatus.svg)](LICENSE)

# SpringBoot + SpringCloud 实践

## 目录

- **api-gateway** API网关
- **[basis-service](basis-service/README.md)** : 记录日志,发送邮件等
- **common** : 被依赖的模块,非单独启动的服务
    - **[base-server](common/base-server/README.md)**   : 一般的微服务都需要依赖此模块
    - **[basis](common/basis/README.md)**   : 
    - **email** : 发送邮件服务,建议需要发送邮件的都通过消息队列,由指定的服务统一发送邮件
    - **flyway** : 数据库版本控制
    - **[jpa](common/jpa/README.md)** : hibernate,使用了druid连接池
    - **mybatis**
    - **mybatis-plus**  Mybatis 的增强工具,挺好用的
    - **redis**
    - **[redisson](common/redisson/README.md)** 利用redis实现分布式锁
    - **[rocketmq](common/rocketmq/README.md)** : 阿里的消息队列
    - **shiro**
    - **web**
- **server**
    - **config-repo** : 配置文件
    - **[config-server](server/config-server/README.md)** : 配置服务,微服务从此服务中获取配置
    - **discovery** : 服务治理(eureka-server) 
    - **spring-boot-admin** : 监控
- **model-test** : 用来练习和测试的模块
    - **eureka-client-provider** : 模拟 服务提供方
    - **eureka-client-consumer** : 模拟 服务消费者
    - **web-service** : 没用上,测试的
- **wxapp-microservice** : 微信小程序服务端
    - **wxapp** : 基础模块
    - **wxapp-service** : 小程序微服务

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
- 编码规范: 见阿里java开发手册
    - idea中安装alibaba java coding guidelines 插件
    - 文件注释(File Header)
    ```
    /**
     * @author ${USER}
     * @date ${DATE} ${HOUR}:${MINUTE}:${SECOND}
     */
    ```
    
- hosts文件增加:
    ```
    127.0.0.1 std-eureka-server-1
    ```
  因为注册比较慢,开发环境可以将所有serviceName都加到hosts中
- Assert
    项目中的断言使用org.springframework.util.Assert,原生的在生产环境会默认忽略

- 字符集
    项目为utf-8,数据库为utf8mb4
    因为Mysql的utf8编码最多3个字节,4个字节会报错(Emoji)

- **微服务通信**
   约定微服务之间通信,成功则返回需要的数据,异常则直接抛业务异常,框架会返回[Result](common/basis/src/main/java/me/zuhr/demo/basis/model/Result.java)实体

- **[异常处理](docs/Exception.md)**
  - [项目异常继承关系](https://www.processon.com/view/link/5ab60ee8e4b027675e3854b9),密码boot
  - 项目默认业务异常为BusinessException,自定义业务异常需要继承此异常
  - 通过control的请求,抛的异常都将在GlobalExceptionHandler中被处理,所以业务中尽量不要加try.catch 
  - 被调用的微服务抛的异常,框架调用方在[MyResponseErrorHandler](common/base-server/src/main/java/me/zuhr/demo/server/restful/MyResponseErrorHandler.java)中根据相应策略进行处理
  - rest请求返回400和500可能会抛AbstractRestServerException和AbstractRestHttpException异常,调用方可根据实际情况捕获
- **jpa**
    配置禁止自动修改表，因为使用了[flyway](docs/flyway.md)来管理数据库版本
     `spring.jpa.hibernate.ddl-auto=none`

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
