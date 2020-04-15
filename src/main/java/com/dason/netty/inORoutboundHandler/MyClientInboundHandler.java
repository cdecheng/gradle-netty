package com.dason.netty.inORoutboundHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 自定义的入站处理器
 */
public class MyClientInboundHandler extends SimpleChannelInboundHandler<Long> {
    /**
     * 获取服务端传回来的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("进来了自定义的入站处理器，获取的信息："+msg);
    }

    /**
     * 连接成功的时候，触发的事件，这里用来发个信息到服务端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientInboundHandler 在跟服务端连接成功的时候 发送数据");
        ctx.writeAndFlush(123456L); //发送的是一个long
    }
}
