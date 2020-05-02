package com.dason.netty.definedprotocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端处理器
 *
 * @author chendecheng
 * @since 2020-05-02 12:31
 */
public class ProtocolClientHandler extends SimpleChannelInboundHandler<MyProtocol> {

    /**
     * 客户端跟服务端建立连接后触发的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MyProtocol myProtocol = new MyProtocol();
        myProtocol.setBody("zhagnzhen");
        myProtocol.setContentLength(myProtocol.getBody().length());
        ctx.writeAndFlush(myProtocol);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
        System.out.println("获取的协议的长度是：" + msg.getContentLength());
        System.out.println(msg.getBody());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
