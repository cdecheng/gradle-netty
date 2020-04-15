package com.dason.netty.dnetty.nio;


import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel02 {

    public static void main(String[] args) throws Exception {
        //file文件读取
        File file = new File("/Users/Dason/4study/01.txt");
        //获取输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取FileChannel
        FileChannel channel = fileInputStream.getChannel();
        //创建对应长度的文件channel
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //读取channel中的字节流到缓冲区中（因为只是读取到缓冲区，没没有对）
        channel.read(byteBuffer);
        //将缓冲区的数据直接输出，没有去用channel再进行写操作，所以不需要flip
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }

}
