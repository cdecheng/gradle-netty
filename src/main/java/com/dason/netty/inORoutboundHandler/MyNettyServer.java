package com.dason.netty.inORoutboundHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 这个包的demo只是为了看看在管道pipeline的的handler的执行顺序，主要是在管道中的各个我们自定义或者netty自带的handler
 * 都是继承了ChannelInboundHandlerAdapter 或者ChannelOutboundHandlerAdapter，这个表明了管道的method的执行顺序
 * 其中ChannelInboundHandlerAdapter是入站的，ChannelOutboundHandlerAdapter是出站的，入站出站是相对而言的，对于服务端
 * 读取获取客户端传过来的数据就是入站，例如进行解码就是入站，编码是出站，客户端刚好相反
 * 下面的案例主要是 以编码解码为例进行演示
 * ps:需要说明一点就是，在pipeline中添加的管道都是按照代码的添加顺序的，不管是入站还是出站，没有分开的，不过就是他们都有一个标示
 * 是出站还是入站，进行入站的链路处理的时候会只按照顺序执行对应的handler
 */
public class MyNettyServer {

    public static void main(String[] args) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new MyServerChannelInitializer());
            ChannelFuture channelFuture = bootstrap.bind(8090).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
