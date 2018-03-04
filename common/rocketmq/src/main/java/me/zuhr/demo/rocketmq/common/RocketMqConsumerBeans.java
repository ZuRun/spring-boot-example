package me.zuhr.demo.rocketmq.common;

import java.util.List;

/**
 * @author zurun
 * @date 2018/3/4 20:41:31
 */
public class RocketMqConsumerBeans {
    private List<AbstractRocketMqConsumer> consumers;

    public List<AbstractRocketMqConsumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<AbstractRocketMqConsumer> consumers) {
        this.consumers = consumers;
    }
}

