package me.zuhr.demo.basis.enumration;

/**
 * 消费者订阅主题
 *
 * @author zurun
 * @date 2018/3/17 17:09:20
 */
public enum ConsumerTag {
    // 请求日志相关
    REQUEST("request", ConsumerTopic.LOG),
    // 练习用
    PUSH("push", ConsumerTopic.PushTopic);

    private String tag;
    private ConsumerTopic consumerTopic;

    ConsumerTag(String tag, ConsumerTopic consumerTopic) {
        this.tag = tag;
        this.consumerTopic = consumerTopic;
    }

    public ConsumerTopic getConsumerTopic() {
        return consumerTopic;
    }

    public String getTag() {
        return tag;
    }
}
