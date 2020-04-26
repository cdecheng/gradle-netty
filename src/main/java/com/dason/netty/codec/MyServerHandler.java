package com.dason.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("服务器接收到数据 " + msg);

        //服务器回送数据给客户端, 回送客户端发过来的Long  没有编码器需要手动构造ByteBuf
//        ByteBuf responseByteBuf = Unpooled.copyLong(msg+1);
//        ctx.writeAndFlush(responseByteBuf);


        //服务器回送数据给客户端, 回送客户端发过来的Long  有编码器可以直接发送
        ctx.writeAndFlush(msg);

    }
}
