package me.zuhr.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author zurun
 * @date 2018/2/23 23:20:29
 */
@SpringCloudApplication
public class EurekaClientProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientProviderApplication.class, args);
    }
}
