package me.zuhr.demo.ecc.action;

import me.zuhr.demo.basis.enumration.ServiceNameEnum;
import me.zuhr.demo.server.restful.MyRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/3/21 01:51:34
 */
@RestController
public class ExceptionAction {
    @Autowired
    MyRestTemplate restTemplate;


    @RequestMapping("/exception")
    public String exception() {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/exception", String.class);
    }

    @RequestMapping("/runtimeException")
    public String runtimeException() {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/runtimeException", String.class);
    }

    @RequestMapping("/businessException")
    public String businessException() {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/businessException", String.class);
    }

    @RequestMapping("/restBusinessException")
    public String restBusinessException() {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/restBusinessException", String.class);
    }
}
