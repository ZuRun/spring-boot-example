package me.zuhr.demo.ecs.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hi()  {
        return "Hello, Spring Cloud! My port is " + String.valueOf(serverPort);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() throws Exception {
        throw new Exception("手动异常!");
    }

    @RequestMapping("/handle")
    public ResponseEntity handle() {
        Map map=new HashMap();
        map.put("errcode",1);
        map.put("errmsg","测试错误信息");
        return new ResponseEntity(map, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
