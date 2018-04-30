package me.zuhr.demo.server.config;

import me.zuhr.demo.server.restful.MyRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author zurun
 * @date 2018/2/25 00:07:28
 */
@Configuration
public class RestConfig {

    /**
     * 项目中微服务间rest请求
     * 使用了负载均衡
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public MyRestTemplate myRestTemplate() {
        return new MyRestTemplate();
    }

    /**
     * 原生RestTemplate
     * 注入时需要加 @Qualifier("restTemplate")注解
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new MyRestTemplate();
    }
}
