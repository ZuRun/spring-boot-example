package me.zuhr.demo.wxapp.utils;

import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.wxapp.vo.SessionVo;
import me.zuhr.demo.wxapp.vo.UserInfoVo;
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

    public void add(String key, UserInfoVo userInfo) {

    }

    /**
     * 登录记录登录信息并返回token
     *
     * @param vo
     * @return
     */
    public String login(SessionVo vo) {
        String token = UUID.randomUUID().toString();
        redisUtils.hashSet(WXAPP_PREFIX + token, vo, TIMEOUT);
        return token;
    }


    /**
     * @param token
     * @return
     * @author ZuRun
     */
    public SessionVo getSession(String token) {
        return redisUtils.hashGet(WXAPP_PREFIX + token, SessionVo.class);
    }
}
