package com.dason.protobuf3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端的pipeline 管道的添加的自定义的Handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyMessage.Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage.Message msg) throws Exception {
        MyMessage.Message.DataType dataType = msg.getDataType();

        if (dataType == MyMessage.Message.DataType.PersonType) {
            MyMessage.Person person = msg.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.getAddress());
        } else if (dataType == MyMessage.Message.DataType.DogType) {
            MyMessage.Dog dog = msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        } else {
            MyMessage.Cat cat = msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getAge());
        }
    }

    /**
     * 处理异常，也就是关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
