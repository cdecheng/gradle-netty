package com.dason.netty.dnetty.nio;

import java.nio.IntBuffer;

/**
 * buffer 父类的Buffer几个常用方法，以及最常用的ByteBuffer的几个方法可以了解一下
 * 还有buffer的几个常用变量也需要了解一下
 * 对于nio而言，主要有三个比较中重要的组件，主要是缓冲区Buffer，通道Channel，选择器Selector
 */
public class BasicBuffer {

    public static void main(String[] args) {

        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 3);
            System.out.println("position is ---" + intBuffer.position());
            System.out.println("limit is ---" + intBuffer.limit());
            System.out.println("capacity is ---" + intBuffer.capacity());
        }

//        private int mark = -1;
//        private int position = 0;
//        private int limit;
//        private int capacity;

        intBuffer.flip();
        intBuffer.position(2);

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }

}
