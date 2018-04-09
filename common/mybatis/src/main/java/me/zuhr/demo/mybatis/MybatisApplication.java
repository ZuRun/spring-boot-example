package me.zuhr.demo.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用于单元测试
 *
 * @author zurun
 * @date 2018/4/9 17:40:35
 */
@SpringBootApplication
@MapperScan("me.zuhr.demo.mybatis.mapper")
public class MybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

}
