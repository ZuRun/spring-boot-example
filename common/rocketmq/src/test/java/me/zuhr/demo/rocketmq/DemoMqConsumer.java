package me.zuhr.demo.rocketmq;

import me.zuhr.demo.basis.enumration.ConsumerTag;
import me.zuhr.demo.rocketmq.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Set;

/**
 * @author zurun
 * @date 2018/3/4 21:16:05
 */
public class DemoMqConsumer extends AbstractRocketMqConsumer {


    public DemoMqConsumer(String consumerGroup) {
        super(consumerGroup);
    }

    @Override
    public void subscribeTopicTags(Set<ConsumerTag> set) {
        set.add(ConsumerTag.PUSH);
    }


    @Override
    public boolean consumeMsg(MessageExt messageExt) {
        byte[] bytes = messageExt.getBody();
        System.out.println("--------------consumeMsg:");
        System.out.println(new String(bytes));
        return true;
    }
}
