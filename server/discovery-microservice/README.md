## [服务治理(Eureka)](https://www.jianshu.com/p/d32ae141f680)

### 步骤

- pom文件
    - 依赖或者parent中依赖 SpringBoot和SpringCloud
    - spring-cloud-starter-eureka-server依赖
        
        ```xml
        <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-eureka-server</artifactId>
                </dependency>
            </dependencies>
        ```

- 启动类
    - 启动类上需要额外添加@EnableEurekaServer，声明这是一个Eureka服务器。
    
- 配置文件
    