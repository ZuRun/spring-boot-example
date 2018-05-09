package me.zuhr.demo.zuul.util;

import me.zuhr.demo.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author zurun
 * @date 2018/5/1 00:36:34
 */
public class RedisHelper {
    @Autowired
    RedisUtils redisUtils;
    /**
     * redis缓存key的前缀
     */
    @Value("${wxapp.config.redis.prefix}")
    private String WXAPP_PREFIX;
    @Value("${wxapp.config.redis.timeout}")
    private Long WXAPP_TIMEOUT;


    /**
     * 检查微信小程序登录状态,并刷新超时时间
     *
     * @param token
     * @return
     */
    public boolean wxappCheckToken(String token) {
        return redisUtils.expire(WXAPP_PREFIX + token, WXAPP_TIMEOUT);
    }
}
