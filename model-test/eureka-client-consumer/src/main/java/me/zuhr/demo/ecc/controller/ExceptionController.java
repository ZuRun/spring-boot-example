package me.zuhr.demo.ecc.controller;

import me.zuhr.demo.basis.enumration.ServiceNameEnum;
import me.zuhr.demo.ecp.feign.ExceptionFeignClient;
import me.zuhr.demo.server.restful.MyRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/3/21 01:51:34
 */
@RestController
public class ExceptionController {
    @Autowired
    MyRestTemplate restTemplate;

    @Autowired
    ExceptionFeignClient exceptionFeignClient;


    @RequestMapping("/exception")
    public String exception() {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/exception", String.class);
    }

    @RequestMapping("/runtimeException")
    public String runtimeException() {
        return exceptionFeignClient.runtimeException();
//        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/runtimeException", String.class);
    }

    @RequestMapping("/businessException")
    public String businessException() {
        return exceptionFeignClient.businessException();
//        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/businessException", String.class);
    }

    @RequestMapping("/restBusinessException")
    public String restBusinessException() {
        return exceptionFeignClient.restBusinessException();
//        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/restBusinessException", String.class);
    }

    @RequestMapping(value = "/notfound0", method = RequestMethod.GET)
    public String notfound0(String param) {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/notfoundxx", String.class);
    }

    @RequestMapping(value = "/notfound1", method = RequestMethod.GET)
    public String notfound1(String param) {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/notfound", String.class);
    }

    @RequestMapping(value = "/notfound2", method = RequestMethod.GET)
    public String notfound2(String param) {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/notfound?param=" + param, String.class);
    }

    @RequestMapping(value = "/notfound3", method = RequestMethod.GET)
    public String notfound3(String param) {
        return restTemplate.postForObject("http://" + ServiceNameEnum.ECP.getValue() + "/notfound", null, String.class);
    }
}
