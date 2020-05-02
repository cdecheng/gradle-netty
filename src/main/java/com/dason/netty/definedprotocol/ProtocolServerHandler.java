package com.dason.netty.definedprotocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端处理器
 *
 * @author chendecheng
 * @since 2020-05-02 12:32
 */
public class ProtocolServerHandler extends SimpleChannelInboundHandler<MyProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
        System.out.println("获取的协议的长度是：" + msg.getContentLength());
        System.out.println(msg.getBody());
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
