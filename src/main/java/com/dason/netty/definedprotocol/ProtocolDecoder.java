package com.dason.netty.definedprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 协议解码器
 *
 * @author chendecheng
 * @since 2020-05-02 11:40
 */
public class ProtocolDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //解码数据，但是这里其实是最简单的情况，只有两个数据前面是数据长度，后面内容是数据，实际上的解码，请求头等会有一系列的标识的
        //不会这么简单的，而且还有一点就是，这里获取到ByteBuf直接读取数据了，实际上不能这样的，这样太不严谨了，很容易出错的
        //应该有一系列的判断，判断长度够不够，标识对不对，进行数据读取的时候有很多业务强壮型的判断才合理，这里都没有
        int contentLength = in.readInt();
        CharSequence body = in.readCharSequence(contentLength, Charset.forName("utf-8"));

        MyProtocol myProtocol = new MyProtocol();
        myProtocol.setBody(body.toString());
        myProtocol.setContentLength(contentLength);
        out.add(myProtocol);
    }
}
