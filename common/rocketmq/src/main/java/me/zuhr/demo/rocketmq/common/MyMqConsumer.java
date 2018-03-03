package me.zuhr.demo.rocketmq.common;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.Objects;

/**
 * @author zurun
 * @date 2018/3/2 15:42:40
 */
public class MyMqConsumer {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private DefaultMQPushConsumer consumer;

    /**
     * 销毁
     */
    @PreDestroy
    private void destroy() {
        if (Objects.nonNull(consumer)) {
            logger.info("producer shutdown");
            consumer.shutdown();
            logger.info("producer has shutdown");
        }
    }

}
