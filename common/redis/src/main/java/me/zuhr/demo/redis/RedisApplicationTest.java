package me.zuhr.demo.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 这个启动项没啥用,单纯用来做单元测试的
 *
 * @author zurun
 * @date 2018/2/12 16:48:25
 */
@SpringBootApplication
public class RedisApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplicationTest.class, args);
    }
}
