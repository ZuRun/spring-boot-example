package me.zuhr.demo.rocketmq.common;

import me.zuhr.demo.basis.enumration.ConsumerTag;
import me.zuhr.demo.basis.mq.AbstractMqConsumer;
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
import java.util.*;
import java.util.List;

/**
 * @author zurun
 * @date 2018/3/2 15:42:40
 */
public abstract class AbstractRocketMqConsumer implements AbstractMqConsumer {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private DefaultMQPushConsumer mqPushConsumer;

    /**
     * 消费者的组名
     */
    protected String consumerGroup;
    /**
     * NameServer 地址
     */
    protected String namesrvAddr;
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


    public AbstractRocketMqConsumer() {
    }

    public AbstractRocketMqConsumer(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public AbstractRocketMqConsumer(String consumerGroup, String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
        this.consumerGroup = consumerGroup;
    }


    /**
     * 需要订阅的 标签
     * <p>
     *
     * @param set Set<Tags>  PushTopic下指定Tag的消息
     * @return
     */
    @Override
    public abstract void subscribeTopicTags(Set<ConsumerTag> set);

    /**
     * 收到的信息进行处理(消费)
     *
     * @param messageExt
     * @return true表示成功, 否则失败
     */
    public abstract boolean consumeMsg(MessageExt messageExt);

    @PostConstruct
    public void init() throws MQClientException {
        Assert.notNull(consumerGroup, "未指定ConsumerGroup!");
        mqPushConsumer = new DefaultMQPushConsumer(this.consumerGroup);

        Assert.isTrue(!isStarted, "container already started.");
        this.isStarted = true;

        // 并行方式处理消息
        mqPushConsumer.setMessageListener(new DefaultMessageListenerConcurrently());
        /**
         * 解析需要订阅的主题标签
         *  直接用set是为了实现方法中写起来简单,虽然解析起来比较麻烦
         */
        Set<ConsumerTag> set = new HashSet();
        subscribeTopicTags(set);

        /**
         * mq订阅需要的格式为 tag1 || tag2 || tag3
         * 先遍历set 将有相同的rocketMqTopic的rocketMqTag放在同一个set中
         * Map<rocketMqTopic,Set<rocketMqTag>>
         */
        Map<String, Set<String>> map = new HashMap<>();
        set.forEach(e -> {

            String rocketMqTopic = e.getConsumerTopic().getTopic();
            String rocketMqTag = e.getTag();
            Assert.notNull(rocketMqTag, "标签不为空,如需全部订阅,请使用*");
            if (map.containsKey(rocketMqTopic)) {
                map.get(rocketMqTopic).add(rocketMqTag);
            } else {
                HashSet<String> tags = new HashSet<String>();
                tags.add(rocketMqTag);
                map.put(rocketMqTopic, tags);
            }
//

        });

        /**
         * 格式化为需要的subscription expression
         */
        map.entrySet().forEach(e -> {
            try {
                String rocketMqTopic = e.getKey();
                Set tagsSet = e.getValue();
                String rocketMqTags = StringUtils.join(tagsSet, " || ");
                mqPushConsumer.subscribe(rocketMqTopic, rocketMqTags);
                logger.info("subscribe, topic:{}, tag:{}", rocketMqTopic, rocketMqTags);
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

        mqPushConsumer.start();


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
