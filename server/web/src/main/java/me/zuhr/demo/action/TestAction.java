package me.zuhr.demo.action;

import me.zuhr.demo.service.TestService;
import me.zuhr.demo.utils.ByteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/2/11 10:12:14
 */
@RestController
@RequestMapping("/test")
public class TestAction {
    @Autowired
    TestService testService;

    public static void main(String[] args) {
        byte[] bytes = ByteUtils.toByteArray("test");
        System.out.println(bytes);
    }

    @RequestMapping("t")
    public String test() {
        return "------";
    }

    @RequestMapping("redis")
    public String redis() {
        long beginStr = System.currentTimeMillis();
//        for(int i=0;i<10;i++) {
            String k1 = testService.test("k1");
//            String k2 = testService.test("k2");
//            String k3 = testService.test("k3");
//        }
        long timeStr = System.currentTimeMillis() - beginStr;
        System.out.println("StringRedisSerializer time:" + timeStr);

        return "ok";
    }
}
