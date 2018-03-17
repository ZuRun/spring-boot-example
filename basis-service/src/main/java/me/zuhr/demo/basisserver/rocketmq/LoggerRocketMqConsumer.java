package me.zuhr.demo.basisserver.rocketmq;

import com.alibaba.fastjson.JSONObject;
import me.zuhr.demo.basis.enumration.ConsumerGroup;
import me.zuhr.demo.basis.enumration.ConsumerTag;
import me.zuhr.demo.basisserver.entity.RequestLogger;
import me.zuhr.demo.basisserver.service.LoggerService;
import me.zuhr.demo.rocketmq.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 记录日志
 *
 * @author zurun
 * @date 2018/3/17 00:58:50
 */
@Component
public class LoggerRocketMqConsumer extends AbstractRocketMqConsumer {
    @Autowired
    LoggerService loggerService;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;


    @Override
    @PostConstruct
    public void init() throws MQClientException {
        super.namesrvAddr = this.namesrvAddr;
        super.consumerGroup = ConsumerGroup.BasisServer.name();
        super.init();
    }

    @Override
    public void subscribeTopicTags(Set<ConsumerTag> set) {
        set.add(ConsumerTag.REQUEST);
    }

    @Override
    public boolean consumeMsg(MessageExt messageExt) {
        byte[] bytes = messageExt.getBody();
        System.out.println("--------------message:");
        String message = new String(bytes);
        System.out.println(message);
        RequestLogger requestLogger = JSONObject.parseObject(message, RequestLogger.class);
        loggerService.saveRequestLogger(requestLogger);
        return true;
    }
}
