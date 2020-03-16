package com.dason.protobuf3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * netty的Handler
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MyMessage.Message> {

    /**
     * 通道就绪的时候会触发的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        MyMessage.Message message = null;
        if (randomInt == 0) {
             message = MyMessage.Message.newBuilder().setDataType(MyMessage.Message.DataType.PersonType).
                    setPerson(MyMessage.Person.newBuilder().setAddress("广州天河").setName("张震").setAge(18).build()).build();
            ctx.writeAndFlush(message);
        } else if (randomInt == 1) {
            message = MyMessage.Message.newBuilder().setDataType(MyMessage.Message.DataType.DogType).
                    setDog(MyMessage.Dog.newBuilder().setName("张震的狗").setAge(1).build()).build();
            ctx.writeAndFlush(message);
        } else {
            message = MyMessage.Message.newBuilder().setDataType(MyMessage.Message.DataType.CatType).
                    setCat(MyMessage.Cat.newBuilder().setName("张震的猫").setAge(1).build()).build();
            ctx.writeAndFlush(message);
        }
        ctx.channel().writeAndFlush(message);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage.Message msg) throws Exception {
        System.out.println("服务端回写了");
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
