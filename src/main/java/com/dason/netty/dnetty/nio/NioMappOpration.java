package com.dason.netty.dnetty.nio;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可让文件直接在内存(堆外内存)修改, 操作系统不需要拷贝一次
 *
 */
public class NioMappOpration {

    public static void main(String[] args) throws Exception{

        //获取文件访问，指定模式为读写
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/Dason/4study/01.txt","rw");
        //获取通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1: FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2： 0 ： 可以直接修改的起始位置
         * 参数3:  5: 是映射到内存的大小(不是索引位置) ,即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         * 实际类型 DirectByteBuffer
         */
        //专程mappedByteBuffer缓冲buffer，并指定操作模式以及起始位置结束长度，这个效率会比较高，少了操作系统那一套
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0,5);

        map.put(0, (byte) 'h');
        map.put(1, (byte) 'h');
        map.put(2, (byte) 'h');

        randomAccessFile.close();

        System.out.println("搞定");

    }

}
