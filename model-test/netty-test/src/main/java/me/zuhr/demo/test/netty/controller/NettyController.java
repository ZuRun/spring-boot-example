package me.zuhr.demo.test.netty.controller;

import me.zuhr.demo.basis.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/6/4 17:18:29
 */
@RestController
public class NettyController {
    public static String result = "r";

    @RequestMapping("nettyReply")
    public Result nettyReply(String param) {
        result = param;
        return Result.ok("ok了");
    }
}
