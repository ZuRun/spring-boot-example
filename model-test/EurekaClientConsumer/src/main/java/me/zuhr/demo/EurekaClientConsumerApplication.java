package me.zuhr.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author zurun
 * @date 2018/2/24 00:05:09
 */
@SpringCloudApplication
public class EurekaClientConsumerApplication {
//    放在base-server模块中实例化
//    @Bean
//    @LoadBalanced
//    MyRestTemplate restTemplate() {
//        return new MyRestTemplate();
//    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientConsumerApplication.class, args);
    }
}
