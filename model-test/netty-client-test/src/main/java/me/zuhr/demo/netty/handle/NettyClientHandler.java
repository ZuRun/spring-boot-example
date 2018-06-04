package me.zuhr.demo.netty.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zurun
 * @date 2018/6/4 10:18:50
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        System.out.println("HelloWorldClientHandler Active");
        System.out.println("-------------------------------");
        ctx.writeAndFlush("Hello Netty Server ,I am a common client");

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("HelloWorldClientHandler read Message:" + msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
