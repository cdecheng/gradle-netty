package com.dason.netty.dnetty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用一个buffer完成文件读取跟写入（简单以一个文件复制案例实现）
 */
public class NioFileChannel03 {

    public static void main(String[] args) throws Exception{
        //读取数据
        FileInputStream fileInputStream = new FileInputStream("/Users/Dason/4study/01.txt");
        FileChannel fileChannel1 = fileInputStream.getChannel();

        //写入数据路径
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/Dason/4study/02.txt");
        FileChannel fileChannel2 = fileOutputStream.getChannel();

        //缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {
            byteBuffer.clear();//非常重要的一步，将缓冲区的几个属性归位，不然就会无限循环，因为flip之后，读取数据没有的时候，
            // read=0，只有在clear之后读取不到才是-1，因为一直不等-1就会一直循环读取已有数据

            //通道获取数据到缓冲区中
            int read = fileChannel1.read(byteBuffer);
            if (-1 == read) {
                break;
            }
            //读写切换
            byteBuffer.flip();
            fileChannel2.write(byteBuffer);
        }
        //关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }

}
