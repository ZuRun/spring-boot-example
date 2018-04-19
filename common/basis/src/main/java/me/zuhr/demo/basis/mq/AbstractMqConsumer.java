package me.zuhr.demo.basis.mq;

import me.zuhr.demo.basis.enumration.ConsumerTag;

import java.util.Set;

/**
 * @author zurun
 * @date 2018/3/17 18:09:53
 */
public interface AbstractMqConsumer {
    /**
     * 需要订阅的 标签
     * <p>
     *
     * @param set Set<Tags>  PushTopic下指定Tag的消息
     * @return
     */
    void subscribeTopicTags(Set<ConsumerTag> set);

}
