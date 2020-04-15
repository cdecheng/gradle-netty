package com.dason.netty.dnetty.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main(String[] args) throws Exception{

        //获取一个SocketChannel
        SocketChannel socketChannel = SocketChannel.open();

        //设置非阻塞
        socketChannel.configureBlocking(false);

        //指定服务器端的地址以及端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //判断如果是还没有连接上
        if (!socketChannel.connect(inetSocketAddress)) {
            //如果没有完成连接这里还是可以进行其他的事情操作的证明
            while (!socketChannel.finishConnect()) {
                System.out.println("在等待连接，这里也是可以利用线程做其他的事情");
            }
        }

        String sendData = "张震测试";
        //使用ByteBuffer的wrap的方法来根据实际的数据长度以及值创建字节流
        ByteBuffer byteBuffer = ByteBuffer.wrap(sendData.getBytes());
        //写到服务器
        socketChannel.write(byteBuffer);

        //执行的时候暂时停留在这个位置
        System.in.read();
    }

}
