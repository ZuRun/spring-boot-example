package me.zuhr.demo.wxapp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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


}
