package com.dason.netty.definedprotocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 客户端handler添加初始化对象
 *
 * @author chendecheng
 * @since 2020-05-02 11:43
 */
public class ProtocolClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtocolDecoder());
//        pipeline.addLast(new ProtocolClientHandler());
        pipeline.addLast(new ProtocolEncoder());
        pipeline.addLast(new ProtocolClientHandler()); //这里再次验证了一点就是，将其放在最后，是不可行的，也就是pipeline的
        // 最后一个添加的handler必须是入栈处理器，就像将其pipeline.addLast(new ProtocolEncoder()); 前面的话是无法执行的，整个链路就出问题了
    }

}
