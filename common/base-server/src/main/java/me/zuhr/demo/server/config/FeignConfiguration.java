package me.zuhr.demo.server.config;

import feign.codec.ErrorDecoder;
import me.zuhr.demo.server.feign.RestErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign对http错误状态码的处理方法
 *
 * @author ZuRun
 * @date 2018/04/13 01:25:17
 */
@Configuration
public class FeignConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new RestErrorDecoder();
    }
}
