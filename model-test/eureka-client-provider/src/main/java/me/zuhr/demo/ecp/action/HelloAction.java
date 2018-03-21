package me.zuhr.demo.ecp.action;

import me.zuhr.demo.server.exception.RestBusinessException;
import me.zuhr.demo.server.restful.MyResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zurun
 * @date 2018/2/23 23:50:54
 */
@RestController
public class HelloAction {
    @Value("${server.port}")
    private int serverPort;
    @Value("${configServerTest}")
    private String configTest;


    @RequestMapping(value = "/configTest", method = RequestMethod.GET)
    public String configTest() {
        return "configTest:" + configTest;
    }

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hi() {
        return "Hello, Spring Cloud! My port is " + String.valueOf(serverPort);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() throws Exception {
        throw new Exception("手动异常!");
    }

    @RequestMapping(value = "/zdy", method = RequestMethod.GET)
    public String zdy() {
        throw new RestBusinessException("zdy异常");
    }

    @RequestMapping("/handle")
    public ResponseEntity handle() {
        Map map = new HashMap();
        map.put("errcode", 1);
        map.put("errmsg", "测试错误信息");
        return MyResponseEntity.fail(map);
//        return new ResponseEntity(map, HttpStatus.METHOD_NOT_ALLOWED);
    }


}
