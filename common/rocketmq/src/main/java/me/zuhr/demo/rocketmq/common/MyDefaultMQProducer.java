package me.zuhr.demo.rocketmq.common;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.Objects;

/**
 * @author zurun
 * @date 2018/3/2 00:20:52
 */
public class MyDefaultMQProducer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private DefaultMQProducer producer;

    public MyDefaultMQProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }

    //TODO-zurun 这个地方的异常需要处理下,可以考虑将message存到redis中,或者抛异常,分情况考虑吧
    public SendResult send(Message message) {
        try {
            return producer.send(message);
        } catch (Exception e) {
            logger.error("send msg error : ", e.getMessage());
            return null;
        }
    }

    public Boolean sendMsg(Message message) {
        SendResult sendResult = send(message);
        return sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK;
    }

    /**
     * send one way msg.
     *
     * @param msg msg
     */
    public void sendOneWayMsg(Message msg) {
        try {
            producer.sendOneway(msg);
        } catch (Exception e) {
            logger.error("send msg error", e);
        }
    }

    /**
     * send delay msg.
     *
     * @param msg        content
     * @param delayLevel 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @return send result
     */
    public boolean sendDelayMsg(String topic, String tag, Message msg, int delayLevel) {
        msg.setDelayTimeLevel(delayLevel);
        SendResult sendResult = null;
        try {
            sendResult = producer.send(msg);
        } catch (Exception e) {
            logger.error("send msg error", e);
        }
        return sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK;
    }


    /**
     * 销毁
     */
    @PreDestroy
    private void destroy() {
        if (Objects.nonNull(producer)) {
            logger.info("producer shutdown");
            producer.shutdown();
            logger.info("producer has shutdown");
        }
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }

}
