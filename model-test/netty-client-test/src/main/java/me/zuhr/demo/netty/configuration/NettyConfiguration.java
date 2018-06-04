package me.zuhr.demo.netty.configuration;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import me.zuhr.demo.netty.handle.NettyClientHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2018/6/4 10:20:14
 */
@Configuration
public class NettyConfiguration {
    @Value("1")
    private int workerCount;

    /**
     * 客户端bootstrap
     *
     * @return
     */
    @Bean
    public Bootstrap clientBootStrap() {
        Bootstrap b = new Bootstrap();
        b.group(clientGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast("decoder", new StringDecoder());
                        p.addLast("encoder", new StringEncoder());
                        p.addLast(new NettyClientHandler());
                    }
                });
        return b;
    }

    /**
     * 客户端EventLoopGroup,客户端bossGroup与workerGroup共用一个
     *
     * @return
     */
    @Bean(name = "clientGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup clientGroup() {
        return new NioEventLoopGroup(workerCount);
    }
}
