package me.zuhr.demo.ecs.action;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author zurun
 * @date 2018/3/1 15:24:05
 */
@RestController
public class RocketmqAction {

    @Autowired
    DefaultMQProducer producer;

    @RequestMapping(value = "/mq", method = RequestMethod.GET)
    public String mq() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String messageBody = "发了消息";
        String message = new String(messageBody.getBytes(), "utf-8");

        //构建消息
        Message msg = new Message("PushTopic", "push", "key_01993", message.getBytes());

        //发送消息
        SendResult result = producer.send(msg);

        String resultStr = "发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus();
        System.out.println(resultStr);

        return resultStr;
    }
}
