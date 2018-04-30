package me.zuhr.demo.wxapp.config;

import lombok.Data;
import me.zuhr.demo.wxapp.utils.WxappUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2018/3/19 22:35:09
 */
@Data
@Configuration
public class WxAppConfiguration {
    @Value("${wxapp.appSecret}")
    private String appSecret;

    @Value("${wxapp.appId}")
    private String appId;


    @Bean
    public WxappUtils wxappUtils(){
        return new WxappUtils();
    }
}
