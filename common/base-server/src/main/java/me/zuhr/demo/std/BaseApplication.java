package me.zuhr.demo.std;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * EnableAsync: 微服务抛的异常等日志需要通过走队列发送到basis-service服务,此处用异步
 *
 * @author zurun
 * @date 2018/3/18 23:20:58
 */
@EnableAsync
@SpringCloudApplication
//@EnableFeignClients //(此注解不支持继承)
public class BaseApplication {
}
