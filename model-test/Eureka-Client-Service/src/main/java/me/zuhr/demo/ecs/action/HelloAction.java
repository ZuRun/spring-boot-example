package me.zuhr.demo.ecs.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/2/23 23:50:54
 */
@RestController
public class HelloAction {
    @Value("${server.port}")
    private int serverPort;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello, Spring Cloud! My port is " + String.valueOf(serverPort);
    }

}
