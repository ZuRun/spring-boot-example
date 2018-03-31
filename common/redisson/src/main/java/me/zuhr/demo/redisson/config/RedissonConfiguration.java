package me.zuhr.demo.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZuRun
 * @date 2018/03/31 12:03:59
 */
@Configuration
public class RedissonConfiguration {
//    @Value("classpath:/redisson-conf.yml")
//    Resource configFile;


    @Bean
    public RedissonClient redissonSingle() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }
}
