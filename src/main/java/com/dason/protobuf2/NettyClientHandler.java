package com.dason.protobuf2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * netty的Handler
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {

    /**
     * 通道就绪的时候会触发的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder().
                setName("张震").setAge(28).setAddress("广州").build();
        ctx.writeAndFlush(student);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {
        System.out.println(msg.getName()+"666");
        System.out.println(msg.getAge()+"666");
        System.out.println(msg.getAddress()+"666");
    }

    /**
     * 出现异常的时候处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
