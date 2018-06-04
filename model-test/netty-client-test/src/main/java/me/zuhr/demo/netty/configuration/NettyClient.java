package me.zuhr.demo.netty.configuration;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zurun
 * @date 2018/6/4 17:50:19
 */
@Component
public class NettyClient {
    @Value("${test.netty.port}")
    private int tcpPort;

    private String host = "127.0.0.1";

    @Autowired
    @Qualifier("clientBootStrap")
    private Bootstrap clientBootstrap;

    private ChannelFuture serverChannelFuture;


    @PostConstruct
    public void start() throws Exception {
        System.out.println("Starting server at " + tcpPort);
        serverChannelFuture = clientBootstrap.connect(host, tcpPort).sync();
    }

    @PreDestroy
    public void stop() throws Exception {
        serverChannelFuture.channel().closeFuture().sync();
        System.out.println("---stop client");

    }

}
