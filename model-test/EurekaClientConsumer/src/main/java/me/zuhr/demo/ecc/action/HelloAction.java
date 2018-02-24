package me.zuhr.demo.ecc.action;

import me.zuhr.demo.ecc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/2/24 00:09:39
 */
@RestController
public class HelloAction {
    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hello() {
        return helloService.test();
    }

}
