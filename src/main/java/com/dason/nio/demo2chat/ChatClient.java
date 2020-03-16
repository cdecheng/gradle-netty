package com.dason.nio.demo2chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 聊天客户端
 * 1。连接服务端，然后获取连接SocketChannel（服务端就会收到请求）
 * 2。创建Selector ，然后将连接的SocketChannel注册上去（这个有点小疑惑，客户端也需要单独维护一个Selector对象吗，还是跟服务端公用一个？----基本确定了，客户端自己维护一个，因为还需要注册上去）
 * 3。然后主线程，先是判断当前连接是不是连接成功事件，是的话就返回连接成功，然后创建子线程来监控客户端的输入，获取输入将其发送到服务端；然后将该连接事件注册到selector中
 * 4。如果不是连接事件，就判断是不是读取事件，是的话，就读取服务端返回的数据，控制台输出；
 * 4。子线程循环阻塞，使用selector.select();获取服务端的回写事件，然后输出
 * @author chendecheng
 * @since 2020-03-12 09:31
 */
public class ChatClient {

    public static void main(String[] args) {

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ);

            while (true) {
                int count = selector.select(3000);
                if (count < 1) {
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                if (selectionKeys.size() < 1) {
//                    continue;
//                }
                for (SelectionKey selectionKey : selectionKeys) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()) {
                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                        writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                        writeBuffer.flip();
//                        client.write(writeBuffer);  ？？？为啥无法写
//                        client.write(writeBuffer);

                        ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                        executorService.submit(() -> {
                            while (true) {
                                try {
                                    writeBuffer.clear();
                                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                    BufferedReader br = new BufferedReader(inputStreamReader);
                                    String printMessage = br.readLine();
                                    writeBuffer.put(printMessage.getBytes());
                                    writeBuffer.flip();
                                    client.write(writeBuffer);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int read = channel.read(readBuffer);
                        String receveMsg = null;
                        if (read > 0) {
                            readBuffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            receveMsg = String.copyValueOf(charset.decode(readBuffer).array());
                            System.out.println(client + ":" + receveMsg);
                        }
                    }
                }
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
