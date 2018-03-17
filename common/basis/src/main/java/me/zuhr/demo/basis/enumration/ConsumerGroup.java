package me.zuhr.demo.basis.enumration;

/**
 * 消费者组名
 * 消费者的组名,一个消费组的所有消费者实例必须拥有相同的topic主题描述。
 * 建议一个服务一个组名,对于多个服务使用同一个topic的情况,这种要特殊处理,暂时没有遇到这种情况,还没研究过
 *
 * @author zurun
 * @date 2018/3/17 16:43:30
 */
public enum ConsumerGroup {
    BasisServer("基础服务用消费组");

    private String value;


    ConsumerGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
