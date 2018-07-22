package me.zuhr.demo.test.netty.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.zuhr.demo.test.netty.global.NettyChannelMap;

import java.util.UUID;

/**
 * @author zurun
 * @date 2018/5/16 15:33:40
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf in = (ByteBuf) msg;
//        in.toString(io.netty.util.CharsetUtil.US_ASCII)
        System.out.println(ctx.channel().remoteAddress() + "->Server :" +msg);

        ctx.writeAndFlush(msg);

//        ctx.writeAndFlush(NettyController.result.getBytes());


//        ctx.flush();
//        try {
////            while (in.isReadable()) {
////                System.out.print((char) in.readByte());
////                System.out.flush();
////            }
//            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
//            ctx.write("sss");
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }


    /**
     * 建立通道,全局变量中记录通道信息
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyChannelMap.add(UUID.randomUUID().toString(), ctx.channel());
    }

    /**
     * 通道断开
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.remove(ctx.channel());
        super.channelInactive(ctx);
    }
}
