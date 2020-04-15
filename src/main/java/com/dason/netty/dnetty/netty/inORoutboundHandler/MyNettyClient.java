package com.dason.netty.dnetty.netty.inORoutboundHandler;

import com.dason.netty.inORoutboundHandler.MyClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 这个作为一个连接的客户端，配合服务器端验证入站跟出站的执行顺序
 */
public class MyNettyClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientChannelInitializer());
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8090).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
