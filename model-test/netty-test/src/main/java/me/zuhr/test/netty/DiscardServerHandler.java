package me.zuhr.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zurun
 * @date 2018/5/16 15:33:40
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in= (ByteBuf) msg;
        System.out.println(ctx.channel().remoteAddress()+"->Server :"+ in.toString(io.netty.util.CharsetUtil.US_ASCII));

        ctx.writeAndFlush(msg);



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
}
