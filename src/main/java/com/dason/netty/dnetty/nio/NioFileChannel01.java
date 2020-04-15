package com.dason.netty.dnetty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * nio的基础操作有三个基础组件，现在先来介绍Channel的组件
 * 其中Channel有四个子对象 都是Channel的子类，FileChannel，ServerSocketChannel,SocketChannel,DatagramChannel
 * File是文件操作Channel，ServerSocketCHannel是服务端channel基于tcp，SocketChannel是客户端的基于tcp，DatagramChannel是基于udp的
 * 相对而言的几个基础操作，channel的read跟wirte
 */
public class NioFileChannel01 {

    public static void main(String[] args) throws Exception {

        String outputString = "张震最帅";
        //文件操作流包含管道的文件操作通道，是FileOutpusStream的属性
        FileOutputStream outputStream = new FileOutputStream("/Users/Dason/4study/01.txt");
        //创建管道
        FileChannel channel = outputStream.getChannel();
        //创建字节流缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //数据放进去缓冲区
        byteBuffer.put(outputString.getBytes());
        //先将缓冲区的position反转，读写之间都需要这一步操作的，因为放进去数据，然后再读取所以需要flip
        byteBuffer.flip();
        //写入管道中
        int write = channel.write(byteBuffer);
        //关闭流
        outputStream.close();
    }

}
