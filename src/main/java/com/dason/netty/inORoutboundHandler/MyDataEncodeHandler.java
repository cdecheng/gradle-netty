package com.dason.netty.inORoutboundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 继承netty提供的数据编码的handler：MessageToByteEncoder这个是一个出站hanler，在出站的时候对数据进行自定义的编码
 */
public class MyDataEncodeHandler extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("数据编码方法被调用，因为是long就直接输出不进行复杂的处理，编码的数据：" + msg);
        //进行数据写入
        out.writeLong(msg);
    }
}
