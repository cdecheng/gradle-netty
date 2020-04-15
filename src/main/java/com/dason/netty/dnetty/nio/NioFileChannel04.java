package com.dason.netty.dnetty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 直接操作两个通道，将a通道的数据传输到b通道中，进行复制操作
 */
public class NioFileChannel04 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("/Users/Dason/4study/01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/Dason/4study/03.txt");

        FileChannel channel = fileInputStream.getChannel();
        FileChannel channel1 = fileOutputStream.getChannel();

        channel1.transferFrom(channel, 0, channel.size());

        channel.close();
        channel1.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

}
