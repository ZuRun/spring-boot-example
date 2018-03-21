package me.zuhr.demo.ecp.controller;

import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.rocketmq.common.RocketMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/3/11 20:57:10
 */
@RestController
public class RocketmqController {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    RocketMqProducer producer;


}
