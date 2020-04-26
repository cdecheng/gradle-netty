package com.dason.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * long类型解码器
 *
 * @author chendecheng
 * @since 2020-04-26 17:18
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("long类型的解码器调用");
        System.out.println("接收到的字节长度" + in.readableBytes());
        if (in.readableBytes() >= 8) {//解码成Long类型一定要先判断长度，这个判断是必须的，不然长度不够解码会出错
            out.add(in.readLong());
        }
    }
}
