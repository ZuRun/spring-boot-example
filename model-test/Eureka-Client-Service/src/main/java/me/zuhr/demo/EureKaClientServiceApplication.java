package me.zuhr.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zurun
 * @date 2018/2/23 23:20:29
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EureKaClientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EureKaClientServiceApplication.class, args);
    }
}
