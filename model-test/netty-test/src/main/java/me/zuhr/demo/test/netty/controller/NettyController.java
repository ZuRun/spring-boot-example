package me.zuhr.demo.test.netty.controller;

import io.netty.channel.Channel;
import me.zuhr.demo.basis.model.Result;
import me.zuhr.demo.test.netty.global.NettyChannelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zurun
 * @date 2018/6/4 17:18:29
 */
@RestController
public class NettyController {

    @RequestMapping("nettyReply")
    public Result nettyReply(String param) {
        NettyChannelMap.getAll().forEach((key, val) -> {
            Channel channel = (Channel) val;
            channel.writeAndFlush(param);
        });
        return Result.ok("ok了");
    }
}
