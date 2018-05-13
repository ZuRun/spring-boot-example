package me.zuhr.demo.ecp.config;

import me.zuhr.demo.basis.enumration.ConsumerTag;
import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.rocketmq.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author zurun
 * @date 2018/3/11 21:04:00
 */
@Configuration
public class RocketMqConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 消费者的组名
     */
    @Value("${apache.rocketmq.consumer.PushConsumer}")
    private String consumerGroup;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 消费者
     */
    @Bean
    public AbstractRocketMqConsumer MqConsumer() {
        AbstractRocketMqConsumer rocketMqConsumer = new AbstractRocketMqConsumer(consumerGroup,namesrvAddr) {

            @Override
            public void subscribeTopicTags(Set<ConsumerTag> set) {
                set.add(ConsumerTag.PUSH);
            }

            @Override
            public boolean consumeMsg(MessageExt messageExt) {
                String message = new String(messageExt.getBody());
                logger.info(message);
                redisUtils.jackson2RedisTemplate.opsForList().rightPush("PushTopic", message);
                return true;
            }
        };
        return rocketMqConsumer;
    }
}
