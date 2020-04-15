package com.dason.netty.dnetty.netty.tcp.ordinary;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 这个包的demo，就是常规的netty的基于tcp协议的Socket数据传输，但是默认的tcp协议的数据传输会出现
 * 粘包拆包的问题，因为客户端传输的数据会自动分断，存在将一个完整的数据一次传输或者一次传输多个又或者多次加起来传输一个
 * 这个主要需要做变更处理的是：我们的服务器端获取客户端传输过去的长度每次都是不固定的，所以我们一次获取多少都没有固定，就会出现不稳定
 * 例如这个demo，客户端发10个数据过来，服务器端每次接收的长度大小都不固定
 */
public class MyServer {
    public static void main(String[] args) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new MyServerInitializer()); //自定义一个初始化类


            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
