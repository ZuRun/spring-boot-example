package me.zuhr.demo.basis.config;

import me.zuhr.demo.basis.restful.MyRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2018/2/25 00:07:28
 */
@Configuration
public class RestConfig {

    @Bean
    @LoadBalanced
    public MyRestTemplate myRestTemplate() {
        return new MyRestTemplate();
    }
}
