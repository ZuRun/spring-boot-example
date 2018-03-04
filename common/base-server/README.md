# 服务的基础模块

## 介绍

### RestTemplate

- RestConfig 实例化MyRestTemplate
- 自定义MyRestTemplate
- 自定义状态码相应策略

## pom
```xml
<dependencies>
    <!--服务发现与注册-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    <!--配合eureka 实现负载均衡的-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-ribbon</artifactId>
    </dependency>
    <!--从配置中心获取配置文件-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
</dependencies>
```