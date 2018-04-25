package me.zuhr.demo.zuul.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2018/4/24 23:05:42
 */
@Configuration
@Data
public class JwtConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    /**
     * 主体
     */
    @Value("${jwt.audience}")
    private String audience;

    /**
     * 发行人
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * 超时时间(毫秒)
     */
    @Value("${jwt.expiresMillisecond}")
    private int expiresMillisecond;

    @Value("access-token")
    private String header;

}
