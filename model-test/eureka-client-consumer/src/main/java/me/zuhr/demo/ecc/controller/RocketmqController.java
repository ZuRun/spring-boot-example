package me.zuhr.demo.ecc.controller;

import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.rocketmq.common.RocketMqProducer;
import me.zuhr.demo.std.BaseService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author zurun
 * @date 2018/3/1 15:24:05
 */
@RestController
@RequestMapping("mq")
public class RocketmqController extends BaseService {
private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RocketMqProducer producer;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/getNextMessage")
    public Result getNextMessage() {
        String message = (String) redisUtils.jackson2RedisTemplate.opsForList().leftPop("PushTopic");
        return Result.ok("ok").addResult(message);
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String mq() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String messageBody = "发了消息:" + UUID.randomUUID().toString();
        String message = new String(messageBody.getBytes(), "utf-8");

        //构建消息
        Message msg = new Message("PushTopic", "push", "key_01993", message.getBytes());

        //发送消息
        SendResult result = producer.send(msg);

        String resultStr = "发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus();
        System.out.println(resultStr);
        System.out.println("-----------------------");

        // 发送延迟消息
//        producer.sendDelayMsg(msg, 3);

        return resultStr;
    }
}
