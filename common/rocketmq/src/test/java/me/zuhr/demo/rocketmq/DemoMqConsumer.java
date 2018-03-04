package me.zuhr.demo.rocketmq;

import me.zuhr.demo.rocketmq.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zurun
 * @date 2018/3/4 21:16:05
 */
public class DemoMqConsumer extends AbstractRocketMqConsumer {


    public DemoMqConsumer(String consumerGroup) {
        super(consumerGroup);
    }

    /**
     * Map<topic,Set<Tags>>
     *
     * @return
     */
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
        byte[] bytes = messageExt.getBody();
        System.out.println("--------------consumeMsg:");
        System.out.println(new String(bytes));
        return true;
    }
}
