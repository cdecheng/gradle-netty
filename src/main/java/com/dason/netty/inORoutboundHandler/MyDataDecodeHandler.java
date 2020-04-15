package com.dason.netty.inORoutboundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义的继承ByteToMessageDecoder的数据解码器
 */
public class MyDataDecodeHandler extends ByteToMessageDecoder {
    /**
     * 解码方法，这个的话因为是知道什么类型，直接读取传过来的字节流，根据long的长度进行数据读取转换成对应的long
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("解码方法被调用--，将数据转换成long类型");
        //因为 long 8个字节, 需要判断有8个字节，才能读取一个long
        if(in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
