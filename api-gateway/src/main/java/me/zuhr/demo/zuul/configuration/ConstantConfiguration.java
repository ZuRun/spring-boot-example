package me.zuhr.demo.zuul.configuration;

import me.zuhr.demo.zuul.util.RedisHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2018/4/30 23:55:25
 */
@Configuration
public class ConstantConfiguration {

    @Bean
    public RedisHelper redisHelper() {
        return new RedisHelper();
    }

}
