package com.dason.netty.dnetty.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * netty的客户端
 */
public class ChatClient {

    private String host = "127.0.0.1";
    private int port = 9090;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());//跟服务端对应，数据交互前先解码编码
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ChatClientHandler());//因为输入数据在这个方法中，然后handler是简单输出服务端返回的数据
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();//连接服务器端
//            channelFuture.channel().closeFuture();//因为客户端在不断等待输入数据，所以不需要关闭
            Channel channel = channelFuture.channel();//然后通过该ChannelFuture来获取通讯的channel
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();//获取控制台输入的信息
                channel.writeAndFlush(msg);//将数据写入通到，也就是传输给服务器端了
            }

        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        ChatClient chatClient = new ChatClient("127.0.0.1", 9090);
        chatClient.run();
    }
}
