package com.dason.netty.inORoutboundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 这是一个封装的解码方法，一些基础的判断不需要我们自己做，就像这里的Long类型，会先判断字节长度是否达到8个才去解码
 * 但是这里的，这些帮我们封装了，netty之所以强大跟它自带很多这种handler脱离不开,还可以去看看netty的源码包的其他handler的解码类
 * 有不少其他的相关解码器编码器
 * 但是：这样的封装的handler效率会比较低
 */
public class MyReplayingDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyReplayingDecoder 被调用，替代了MyDataDecoderHandler,少了一些常规的判断操作");
        //在 ReplayingDecoder 不需要判断数据是否足够读取，内部会进行处理判断
        out.add(in.readLong());
    }
}
