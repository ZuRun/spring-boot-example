package me.zuhr.demo.ecp.config;

import me.zuhr.demo.basis.utils.SystemInfo;
import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.rocketmq.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    public AbstractRocketMqConsumer MqConsumer() throws MQClientException {
        AbstractRocketMqConsumer rocketMqConsumer = new AbstractRocketMqConsumer(consumerGroup) {
            @Override
            public Map<String, Set<String>> subscribeTopicTags() {
                Map<String, Set<String>> map = new HashMap<>();
                Set<String> tags = new HashSet<>();
                tags.add("push");
                map.put("PushTopic", tags);
                return map;
            }

            @Override
            public boolean consumeMsg(MessageExt messageExt) {
                String message = new String(messageExt.getBody());
                logger.info(message);
                redisUtils.jackson2RedisTemplate.opsForList().rightPush("PushTopic", message.concat(";Mac:").concat(SystemInfo.getInstance().getMac()));
                return true;
            }
        };
        rocketMqConsumer.setNamesrvAddr(namesrvAddr);
        return rocketMqConsumer;
    }
}
