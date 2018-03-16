package me.zuhr.demo.rocketmq;

import me.zuhr.demo.rocketmq.common.MyMQProducer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zurun
 * @date 2018/2/28 00:49:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RocketmqApplication.class)
public class RocketmqApplicationTest {

    /**
     * 消费者的组名
     */
    @Value("${apache.rocketmq.consumer.PushConsumer}")
    private String consumerGroup;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Autowired
    MyMQProducer producer;

    /**
     * 消费者
     */
    @Test
    public void demoMqConsumer() throws MQClientException {
        DemoMqConsumer demoMqConsumer = new DemoMqConsumer();
        demoMqConsumer.setNamesrvAddr(namesrvAddr);
        // 单元测试执行此方法,正常情况不要执行,会重复执行
        demoMqConsumer.init();
//        demoMqConsumer.start();
    }


    /**
     * 生产者
     */
    @Test
//    @PostConstruct
    public void defaultMQProducer() {
        try {

            for (int i = 0; i < 100; i++) {

                String messageBody = "我是消息内容:" + i;

                String message = new String(messageBody.getBytes(), "utf-8");

                //构建消息
                Message msg = new Message("PushTopic" /* PushTopic */, "push"/* Tag  */, "key_" + i /* Keys */, message.getBytes());

                //发送消息
                SendResult result = producer.send(msg);

                System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            producer.shutdown();
        }

    }

}