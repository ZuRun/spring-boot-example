package me.zuhr.demo.ecp.action;

import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.rocketmq.common.MyMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/3/11 20:57:10
 */
@RestController
public class RocketmqAction {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    MyMQProducer producer;


}
