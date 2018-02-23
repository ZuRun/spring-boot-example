package me.zuhr.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zurun
 * @date 2018/2/23 18:45:39
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryApplication {
    public static void main(String[] args) {
        // 貌似没有区别
        new SpringApplicationBuilder(DiscoveryApplication.class).web(true).run(args);
    }
}
