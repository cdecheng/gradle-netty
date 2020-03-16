package com.dason.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 新的nio测试零拷贝服务端
 * （ 其实现在是不是零拷贝都没什么，因为是单个客户端请求，如果不用零拷贝，这种情况下nio其实速度优势不大，甚至更慢？ ）
 *
 * @author chendecheng
 * @since 2020-03-16 09:39
 */
public class NewNioClent {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(9900));
        socketChannel.configureBlocking(true);

        String fileDir = "/Users/Dason/software/VSCode-darwin-stable.zip";

        FileChannel fileChannel = new FileInputStream(fileDir).getChannel();

        long startTime = System.currentTimeMillis();

        long tranCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送总字节：" + tranCount + ",总耗时" + (System.currentTimeMillis() - startTime));
        fileChannel.close();

    }

}
