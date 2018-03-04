package me.zuhr.demo.redis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 这个启动项没啥用,单纯用来做单元测试的
 *
 * @author zurun
 * @date 2018/2/12 16:48:25
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RedisApplicationTest {

    public static void main(String[] args) {
        //变更配置文件读取位置启动
        new SpringApplicationBuilder(RedisApplicationTest.class)
                .properties("spring.cloud.bootstrap.name=SpringBootTest").run(args);
    }
}
