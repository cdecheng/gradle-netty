package com.dason.netty.definedprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * 协议编码器
 *
 * @author chendecheng
 * @since 2020-05-02 11:41
 */
public class ProtocolEncoder extends MessageToByteEncoder<MyProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {
//        out.writeLong(8l); //配合TestLengthFieldBasedFrameDecoder使用
        out.writeInt(msg.getContentLength());
        out.writeCharSequence(msg.getBody(), Charset.forName("utf-8"));
    }
}
