package com.dason.netty.codec;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyByteToLongDecoder());//解码器
        pipeline.addLast(new MyLongToByteEncoder());//编码器
        pipeline.addLast(new MyClientHandler());//入栈处理器

    }
}
