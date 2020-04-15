package com.dason.netty.dnetty.netty.inORoutboundHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 自定义的服务器端入站handler
 */
public class MyServerInboundHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("这里是服务端的入站自定义handler，获取接收的数据：" + msg);
        ctx.writeAndFlush(msg);
    }
}
