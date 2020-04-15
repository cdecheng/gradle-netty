package com.dason.netty.dnetty.nio;

import java.nio.ByteBuffer;

/**
 * 使用byteBuffer操作数据的时候，put的类型要跟get的一致，不然可能会抛异常（指的是获取的顺序要对应，但是不一定抛异常）
 */
public class ByteBufferPutGet {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.putInt(333);
        byteBuffer.putLong(444l);
        byteBuffer.putChar('z');

        byteBuffer.flip();

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getChar());

    }

}
