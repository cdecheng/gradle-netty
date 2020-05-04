package com.dason.netty.definedprotocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.Charset;

/**
 * LengthFieldBasedFrameDecoder解码Handler的使用案例
 *
 * 主要两个用途：
 * 1。就是将获取的数据进行切割后，只剩下我们想要的内容描述长度+内容
 * 2。将我们想要的内容在重写的decode()方法中解码成目标对象
 *
 * @author chendecheng
 * @since 2020-05-05 00:19
 */
public class TestLengthFieldBasedFrameDecoder extends LengthFieldBasedFrameDecoder {

    public TestLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                                            int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        //这里的结果就是根据我们的构造方法，根据入参的构造方法进行相关传递过来的数据进行校验或者进行一些切割或者整理
        //最后根据构造方法的入参输出构造后的结果，然后我们在这里根据LengthFieldBasedFrameDecoder处理后的结果ByteBuf进行
        //我们实际的解码操作， 也就是这里这些操作，所以实际上，LengthFieldBasedFrameDecoder并没做什么很多事情，只是一些常规的处理
        //不理解为什么会说这个解码器很厉害，目前在我这里不建议使用，如果是自定义协议解码的话，建议自己完整写一个解码器，这个不难
        //要注意的是解码器的编写可以参考LengthFieldBasedFrameDecoder的decode()方法进行一系列强化性判断
        ByteBuf decodeByteBuf = (ByteBuf) super.decode(ctx, in);
        int length = decodeByteBuf.readInt();
        //这个9是我们传输的内容实际长度，一般情况下这里解码结果要剩下描述
        CharSequence charSequence = decodeByteBuf.readCharSequence(length, Charset.forName("utf-8"));
        String result = charSequence.toString();
        MyProtocol myProtocol = new MyProtocol();
        myProtocol.setContentLength(length);
        myProtocol.setBody(result);

        return myProtocol;
    }

}
