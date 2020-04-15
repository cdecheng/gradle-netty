package com.dason.netty.dnetty.netty.inORoutboundHandler;

import com.dason.netty.inORoutboundHandler.MyDataDecodeHandler;
import com.dason.netty.inORoutboundHandler.MyDataEncodeHandler;
import com.dason.netty.inORoutboundHandler.MyServerInboundHandler;
import com.dason.netty.inORoutboundHandler.MyServerOutboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 作为一个初始化通道的handler，ChannelInitializer也是一个入站Handler，只是一个比较特殊的用于初始化的
 */
public class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //在通道添加各种处理的handler
        pipeline.addLast(new MyDataEncodeHandler());
        pipeline.addLast(new MyDataDecodeHandler());
        pipeline.addLast(new MyServerInboundHandler());
        pipeline.addLast(new MyServerOutboundHandler());
    }
}
