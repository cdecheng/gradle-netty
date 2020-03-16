package com.dason.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 新nio服务端，使用零拷贝的服务端
 *
 * @author chendecheng
 * @since 2020-03-16 09:38
 */
public class NewNIoServer {

    public static void main(String[] args) throws IOException {
        System.out.println("来了老哥");
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(9900);
        socket.setReuseAddress(true);
        socket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int readCount = 0;
            while (-1 != readCount) {
                try {
                    readCount = socketChannel.read(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byteBuffer.rewind();
            }

        }

    }

}
