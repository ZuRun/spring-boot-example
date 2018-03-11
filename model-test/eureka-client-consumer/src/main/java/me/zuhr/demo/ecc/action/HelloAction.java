package me.zuhr.demo.ecc.action;

import me.zuhr.demo.basis.enumration.ServiceNameEnum;
import me.zuhr.demo.server.restful.MyRestTemplate;
import me.zuhr.demo.ecc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Map;

/**
 * @author zurun
 * @date 2018/2/24 00:09:39
 */
@RestController
public class HelloAction {
    @Autowired
    HelloService helloService;

    @Autowired
    MyRestTemplate restTemplate;

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hi() {
        return restTemplate.getForEntity("http://" + ServiceNameEnum.ECP.getValue() + "/hi", String.class).getBody();
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return helloService.test();
    }

    @RequestMapping(value = "/zdy", method = RequestMethod.GET)
    public String zdy() {
        return restTemplate.getForObject("http://" + ServiceNameEnum.ECP.getValue() + "/zdy", String.class);
    }


    @RequestMapping(value = "/handle", method = RequestMethod.GET)
    public Map handle() {
        try {
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://" + ServiceNameEnum.ECP.getValue() + "/handle", Map.class);

            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            System.out.println(e);
            System.out.println(e.getResponseBodyAsString());
        }
        return null;
    }

    @RequestMapping(value = "/notfound", method = RequestMethod.GET)
    public Map notfound() {
        try {
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://" + ServiceNameEnum.ECP.getValue() + "/notfound", Map.class);

            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            System.out.println(e);
            System.out.println(e.getResponseBodyAsString());
        }
        return null;
    }



}
