package com.dason.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * long类型解码器，继承ReplayingDecoder类的实现
 *
 * @author chendecheng
 * @since 2020-04-26 17:18
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("long类型的解码器调用,基于：ReplayingDecoder");
        System.out.println("接收到的字节长度" + in.readableBytes());
        out.add(in.readLong());
    }
}
