package me.zuhr.demo.test.netty.configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zurun
 * @date 2018/6/4 17:05:05
 */
@Component
@Configuration
public class NettyServer {

    @Value("${test.netty.port}")
    private int tcpPort;

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap serverBootstrap;

    private ChannelFuture serverChannelFuture;

    @PostConstruct
    public void start() throws Exception {
        System.out.println("Starting server at " + tcpPort);
        serverChannelFuture = serverBootstrap.bind(tcpPort).sync();
    }

    @PreDestroy
    public void stop() throws Exception {
        serverChannelFuture.channel().closeFuture().sync();
        System.out.println("---stop server");

    }

}
