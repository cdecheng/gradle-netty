package com.dason.netty.dnetty.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端的handler，泛型使用的String，所以客户端跟服务端的交互的数据直接就是String，服务端也是一致的，
 * 也就是为什么bootStrap中的编码解码器用的String的
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("客户的handler接收到的msg ："+msg.trim());
    }
}
