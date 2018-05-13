package me.zuhr.demo.basisserver.mq;

import com.alibaba.fastjson.JSONObject;
import me.zuhr.demo.basis.enumration.ConsumerGroup;
import me.zuhr.demo.basis.enumration.ConsumerTag;
import me.zuhr.demo.basisserver.entity.ExceptionLogger;
import me.zuhr.demo.basisserver.entity.RequestLogger;
import me.zuhr.demo.basisserver.service.BasisLoggerService;
import me.zuhr.demo.rocketmq.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

import static me.zuhr.demo.basis.enumration.ConsumerTag.*;

/**
 * 记录日志
 *
 * @author zurun
 * @date 2018/3/17 00:58:50
 */
@Component
public class LoggerRocketMqConsumer extends AbstractRocketMqConsumer {
    @Autowired
    BasisLoggerService loggerService;

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
        set.add(REQUEST);
        set.add(EXCEPTION);
    }

    @Override
    public boolean consumeMsg(MessageExt messageExt) {
        byte[] bytes = messageExt.getBody();
        String message = new String(bytes);
        logger.info("--------------message----- topic : {} , tags : {}",messageExt.getTopic(),messageExt.getTags());
        System.out.println(message);

        // 异常信息记录
        if (messageExt.getTags().equals(EXCEPTION.getTag())) {
            ExceptionLogger exceptionLogger = JSONObject.parseObject(message, ExceptionLogger.class);
            loggerService.saveExceptionLogger(exceptionLogger);
        }
        // 请求日志记录
        else if (messageExt.getTags().equals(REQUEST.getTag())) {
            RequestLogger requestLogger = JSONObject.parseObject(message, RequestLogger.class);
            loggerService.saveRequestLogger(requestLogger);
        }


        return true;
    }
}
