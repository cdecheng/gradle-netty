package com.dason.netty.dnetty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * 简单展示了netty封装的byteBuf的常用操作
 * 整个ByteBuf的操作，都跟一个封装对象有关就是Unpooled（非池化）
 */
public class NettyByteBufDemo {

    public static void main(String[] args) {
        //分配十个字节大小的字节缓冲区
        ByteBuf buffer = Unpooled.buffer(10);
        //直接创建一个字节缓冲区并且，将我们要字节化的数据处理（实际转换成字节对象的长度一般情况下是大于实际的字符串的）
        ByteBuf byteBuf = Unpooled.copiedBuffer("张震", CharsetUtil.UTF_8);

        //ByteBuf的读写，是通过标识readerindex 和 writerIndex来实现的，所以不需要flap这个读写之间需要变更position操作
        for(int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        for(int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());//这个方法会自动变更下标，每次加1
        }

    }

}
