package me.zuhr.demo.rocketmq.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author zurun
 * @date 2018/3/2 15:42:40
 */
public abstract class AbstractRocketMqConsumer {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private DefaultMQPushConsumer mqPushConsumer;

    public AbstractRocketMqConsumer(String consumerGroup) {
        this.consumerGroup = consumerGroup;
        mqPushConsumer = new DefaultMQPushConsumer(consumerGroup);
    }


    /**
     * 需要订阅的 主题+标签
     * <p>
     * Map<topic,Set<Tags>>  PushTopic下指定Tag的消息
     *
     * @return
     */
    public abstract Map<String, Set<String>> subscribeTopicTags();

    /**
     * 收到的信息进行处理(消费)
     *
     * @param messageExt
     * @return true表示成功, 否则失败
     */
    public abstract boolean consumeMsg(MessageExt messageExt);

    /**
     * 消费者的组名
     */
    private String consumerGroup;
    /**
     * NameServer 地址
     */
    private String namesrvAddr;
    /**
     * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
     * 如果非第一次启动，那么按照上次消费的位置继续消费
     */
    private ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET;
    /**
     * 是否启动,只能启动一次
     */
    private boolean isStarted;
    /**
     * 消息重试策略
     * Message consume retry strategy<br>
     * -1, no retry,put into DLQ directly<br>
     * 0, broker control retry frequency<br>
     * >0, client control retry frequency
     */
    private int delayLevelWhenNextConsume = 0;


    @PostConstruct
    public void init() throws MQClientException {
        Assert.isTrue(!isStarted, "container already started.");

        // 并行方式处理消息
        mqPushConsumer.setMessageListener(new DefaultMessageListenerConcurrently());
        /**
         * 解析需要订阅的主题标签
         *
         */
        subscribeTopicTags().entrySet().forEach(e -> {
            try {
                String rocketMqTopic = e.getKey();
                Set<String> rocketMqTags = e.getValue();
                Assert.notNull(rocketMqTags, "标签不为空,如需全部订阅,请使用*");
                String tags = StringUtils.join(rocketMqTags, " || ");
                mqPushConsumer.subscribe(rocketMqTopic, tags);
                logger.info("subscribe, topic:{}, tags:{}", rocketMqTopic, tags);
            } catch (MQClientException ex) {
                logger.error("consumer subscribe error", ex);
                throw new IllegalComponentStateException("consumer subscribe error," + ex.getMessage());
            }
        });


        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        mqPushConsumer.setConsumeFromWhere(consumeFromWhere);
        //指定NameServer地址，多个地址以 ; 隔开
        mqPushConsumer.setNamesrvAddr(namesrvAddr);


        // 检查
        Assert.notNull(namesrvAddr, "未指定NameServer地址!");
        Assert.notNull(consumerGroup, "未指定ConsumerGroup!");

        mqPushConsumer.start();
        this.isStarted = true;


    }


    /**
     * 销毁
     */
    @PreDestroy
    private void destroy() {
        if (Objects.nonNull(mqPushConsumer)) {
            logger.info("producer shutdown");
            mqPushConsumer.shutdown();
            logger.warn("--- producer has shutdown,namesrvAddr:" + namesrvAddr);
        }
    }

    /**
     * 默认的并行的方式处理消息,不追求时间顺序
     */
    public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

        @Override
        @SuppressWarnings("unchecked")
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt messageExt : msgs) {
                try {
                    long now = System.currentTimeMillis();
                    // 消费失败,则重试!
                    if (!consumeMsg(messageExt)) {
                        return retry(context);
                    }
                    long costTime = System.currentTimeMillis() - now;
                    logger.info("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                } catch (Exception e) {
                    logger.warn("consume message failed. messageExt:{}", messageExt, e);
                    return retry(context);
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        /**
         * 重试
         *
         * @param context
         * @return
         */
        private ConsumeConcurrentlyStatus retry(ConsumeConcurrentlyContext context) {
            context.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

    //getAndSet

    public DefaultMQPushConsumer getMqPushConsumer() {
        return mqPushConsumer;
    }

    public void setMqPushConsumer(DefaultMQPushConsumer mqPushConsumer) {
        this.mqPushConsumer = mqPushConsumer;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public ConsumeFromWhere getConsumeFromWhere() {
        return consumeFromWhere;
    }

    public void setConsumeFromWhere(ConsumeFromWhere consumeFromWhere) {
        this.consumeFromWhere = consumeFromWhere;
    }

    // get
    public String getConsumerGroup() {
        return consumerGroup;
    }

    public boolean isStarted() {
        return isStarted;
    }

}
