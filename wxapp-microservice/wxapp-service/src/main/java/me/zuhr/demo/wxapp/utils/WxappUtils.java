package me.zuhr.demo.wxapp.utils;

import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.wxapp.vo.SessionVo;
import me.zuhr.demo.wxapp.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

/**
 * @author zurun
 * @date 2018/4/30 00:34:18
 */
public class WxappUtils {

    @Autowired
    RedisUtils redisUtils;

    /**
     * redis缓存key的前缀
     */
    @Value("${wxapp.config.redis.prefix}")
    private String WXAPP_PREFIX;
    @Value("${wxapp.config.redis.timeout}")
    private Long TIMEOUT;

    public void add(String key, UserInfo userInfo) {

    }

    /**
     * 登录
     *
     * @param vo
     * @return
     */
    public String login(SessionVo vo) {
        String key = UUID.randomUUID().toString();
        redisUtils.hashSet(key, vo, TIMEOUT);
        return key;
    }
}
