package com.dason.netty.dnetty.netty.inORoutboundHandler;

import com.dason.netty.inORoutboundHandler.MyClientInboundHandler;
import com.dason.netty.inORoutboundHandler.MyClientOutboundHandler;
import com.dason.netty.inORoutboundHandler.MyDataEncodeHandler;
import com.dason.netty.inORoutboundHandler.MyReplayingDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 客户端的初始化通道handler，用来添加各种自带或者自定义的handler
 */
public class MyClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyReplayingDecoder());
//        pipeline.addLast(new MyDataDecodeHandler());
        pipeline.addLast(new MyDataEncodeHandler());
        pipeline.addLast(new MyClientInboundHandler());
        pipeline.addLast(new MyClientOutboundHandler());
    }
}
