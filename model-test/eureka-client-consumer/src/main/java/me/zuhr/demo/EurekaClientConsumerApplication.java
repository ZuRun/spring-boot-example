package me.zuhr.demo;

import me.zuhr.demo.std.BaseApplication;
import org.springframework.boot.SpringApplication;

/**
 * @author zurun
 * @date 2018/2/24 00:05:09
 */
public class EurekaClientConsumerApplication extends BaseApplication{
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
