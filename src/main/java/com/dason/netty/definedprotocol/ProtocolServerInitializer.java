package com.dason.netty.definedprotocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 服务端handler添加初始化对象
 *
 * @author chendecheng
 * @since 2020-05-02 11:44
 */
public class ProtocolServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtocolDecoder());
        pipeline.addLast(new ProtocolEncoder());
        pipeline.addLast(new ProtocolServerHandler());
    }

}


