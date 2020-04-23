package com.dason.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * ByteBufdemo1
 *
 * @author chendecheng
 * @since 2020-04-23 18:27
 */
public class ByteBufDemo1 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("张震zhangzhen", Charset.forName("utf-8"));

        if (byteBuf.hasArray()) {//这个判断也说明了这个是堆内缓存，因为堆内缓存的底层是数组，直接内存的字节保存不是以数组的形式
            byte[] array = byteBuf.array();//获取缓存中的字节数组
            System.out.println(new String(array, Charset.forName("utf-8"))); //将字节数组输出，指定编码才能正常输出
            System.out.println(byteBuf);//打印ByteBuf对象


            //一些比较重要的标识打印
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            int length = byteBuf.readableBytes();
            System.out.println(length);

            for (int i = 0; i < length; i++) {
                System.out.println((char) byteBuf.getByte(i));//没有中文，仅仅是英文可以的，但是如果有中文的话，这里就会出现问题
            }

            //上面的循环每次是读取一个字节的，如果是英文就没关系可以直接获取，但是涉及中文3个字节的就需要指定编码才能正常显示，不然会乱码
            System.out.println(byteBuf.getCharSequence(0, 6, Charset.forName("utf-8")));
            System.out.println(byteBuf.getCharSequence(7, length, Charset.forName("utf-8")));

        }
    }
}
