package com.dason.nio.demo2chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 聊天服务器端
 * 1。接受请求，判断是连接请求还是读取数据请求,将serverSocketChannel注册到selector
 * 2。如果是连接请求，创建一个客户端连接socket，注册Socket到Selector
 * 3。如果是读取数据请求，通过SelectorKey获取通道socketchannel，然后读取数据
 * 4。将数据回写到各个注册的客户端（这里用一个map来记录）
 * 5。记得清空事件集
 *
 * @author chendecheng
 * @since 2020-03-12 09:27
 */
public class ChatServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(9999));

        try {
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            clientMap.put("["+ UUID.randomUUID() +"]", client);
                            System.out.println("注册数量："+ clientMap.size());
                        }else if (selectionKey.isReadable()) {
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int read = client.read(readBuffer);
                            String receveMsg = null;
                            if (read > 0) {
                                readBuffer.flip();
                                Charset charset = Charset.forName("utf-8");
                                receveMsg = String.copyValueOf(charset.decode(readBuffer).array());
                                System.out.println(client + ":" + receveMsg);

                                String sendKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    if (client == entry.getValue()) {
                                        entry.getKey();
                                        sendKey = entry.getKey();
                                        break;
                                    }
                                }
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    SocketChannel socketChannel = entry.getValue();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((sendKey + ":" + receveMsg).getBytes());
                                    writeBuffer.flip();
                                    socketChannel.write(writeBuffer);
                                }

                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
