package me.zuhr.demo.basis.enumration;

/**
 * 消费者订阅主题
 *
 * @author zurun
 * @date 2018/3/17 17:09:20
 */
public enum ConsumerTopic {
    // 日志相关
    LOG("log"),
    // 练习用
    PushTopic("PushTopic");

    private String topic;

    ConsumerTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
