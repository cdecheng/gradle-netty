package com.dason.netty.dnetty.netty.tcp.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 某种程度来说，Server的启动对象不是核心，因为都是一样的，核心在我们处理handler中
 * ps：有个问题在这里记录一下，我们普通数据的传输都是ByteBuf对象，这个尽管说是netty封装的字节对象
 * 效率还是比较低的，因为是基于java的序列化的，想要高效还是得要使用第三方的框架例如 google的protocolbuf
 * 但是至今我还没搞明白，netty在通道传输之前不是先在buffer也就是缓冲区吗，为什么突然就是基于新的传输协议
 * 初步猜测：新的协议是在缓冲区前面的的，就是一个文件存在的存在，然后我们在传输的时候还会将这个协议的文件转换成字节流
 * 才传到缓存中，然后再通道传输
 */
public class MyServer {
    public static void main(String[] args) throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler(new MyServerInitializer()); //自定义一个初始化类


            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
