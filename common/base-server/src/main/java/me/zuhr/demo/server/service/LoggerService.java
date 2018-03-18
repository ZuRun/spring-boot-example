package me.zuhr.demo.server.service;

import com.alibaba.fastjson.JSONObject;
import me.zuhr.demo.basis.enumration.ConsumerTag;
import me.zuhr.demo.basis.enumration.ConsumerTopic;
import me.zuhr.demo.rocketmq.common.RocketMqProducer;
import me.zuhr.demo.server.entity.LoggerEntity;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zurun
 * @date 2018/3/18 23:53:26
 */
@Service
public class LoggerService {

    @Autowired
    RocketMqProducer producer;

    /**
     * 发送异常消息 消息队列
     *
     * @param body
     */
    @Async
    public void sendExceptionLog(byte[] body) {
        // 此处keys不设置,因为请求日志没有唯一值, 设置的话会导致哈希冲突
        producer.sendOneWayMsg(new Message(ConsumerTopic.LOG.getTopic(), ConsumerTag.EXCEPTION.getTag(), body));

    }

    /**
     * 发送请求日志消息队列
     *
     * @param loggerEntity
     */
    @Async
    public void sendRequestLog(LoggerEntity loggerEntity) {
        // 此处keys不设置,因为请求日志没有唯一值, 设置的话会导致哈希冲突
        producer.sendOneWayMsg(new Message(ConsumerTopic.LOG.getTopic(), ConsumerTag.REQUEST.getTag(), JSONObject.toJSONBytes(loggerEntity)));

    }
}
