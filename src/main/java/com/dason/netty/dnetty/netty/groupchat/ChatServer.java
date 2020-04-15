package com.dason.netty.dnetty.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 使用netty实现一个群聊系统，主要的代码量在handler上面，而且netty封装了很多事件操作
 * 使用起来实现非常快速方便
 */
public class ChatServer {

    private int port; //监听端口

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);//指定一个线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();//默认cpu核心数*2 ，这里是值为16
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)//是我们的服务端，也就是Selector一个会处理多个，处理不过来的队列大小
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
    //                .channel() 这个是给bossGroup田间handler的
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //给pipeline添加一个netty自带的handler，处理字符串的解码，意义是（我们会对传输的数据进行编码，然后获取到需要解码）
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ChatServerHandler());//添加数据处理handler
                        }
                    });
            System.out.println("netty的群聊系统启动");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        ChatServer chatServer = new ChatServer(9090);
        chatServer.run();
    }

}
