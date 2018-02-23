package me.zuhr.demo.ecc.action;

import me.zuhr.demo.basis.enumration.ServiceNameEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zurun
 * @date 2018/2/24 00:09:39
 */
@RestController
public class HelloAction {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hello() {
        return restTemplate.getForEntity("http://"+ ServiceNameEnum.ECS.getValue()+"/hello", String.class).getBody();
    }

}
