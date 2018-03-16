package me.zuhr.demo.basisserver.rocketmq;

import me.zuhr.demo.rocketmq.common.AbstractRocketMqConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zurun
 * @date 2018/3/17 00:58:50
 */
@Component
public class LoggerRocketMqConsumer extends AbstractRocketMqConsumer{

    @Override
    public Map<String, Set<String>> subscribeTopicTags() {
        Map<String, Set<String>> map = new HashMap<>();
        Set<String> tags = new HashSet<>();
        tags.add("request");
        map.put("log", tags);
        return map;
    }

    @Override
    public boolean consumeMsg(MessageExt messageExt) {
        byte[] bytes = messageExt.getBody();
        System.out.println("--------------message:");
        System.out.println(new String(bytes));
        return true;
    }
}
