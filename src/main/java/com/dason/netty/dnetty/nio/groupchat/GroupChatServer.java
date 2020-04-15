package com.dason.netty.dnetty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用nio实现一个群聊服务器端
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenSocketChannel;
    private static final int PORT = 6969;

    public GroupChatServer(){
        try {
            selector = Selector.open();
            listenSocketChannel = ServerSocketChannel.open();
            listenSocketChannel.socket().bind(new InetSocketAddress(PORT));
            listenSocketChannel.configureBlocking(false);
            listenSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听客户端的请求，获取请求创建连接，读取数据，然后将数据输出到各个客户端
     */
    public void listen(){

        try {
            System.out.println("正在监听是否有请求进来，当前线程" + Thread.currentThread().getName());

            //需要循环等待处理
            while (true) {
                //阻塞等待事件进来（不阻塞的话，线程跑完就结束停止了）
                int select = selector.select();
                if (select > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        //如果事件是连接事件，就注册到selector中，也就是有客户端连接来了
                        if (next.isAcceptable()) {
                            SocketChannel sc = listenSocketChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println("客户端上线了，上线的ip地址："+sc.getRemoteAddress());
                        }
                        //如果是读取事件，就是有客户端传输信息过来，需要获取信息，然后将信息发送给其他客户端
                        if (next.isReadable()) {
                            //读取信息
                            readMsg(next);
                        }
                        //事件处理完之后，在迭代器移除该事件
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    private void readMsg(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel)selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(byteBuffer);
            if (count > 0) {
                String clientMsg = new String(byteBuffer.array());
                System.out.println(socketChannel.getRemoteAddress()+"客户端发送过来的信息是："+clientMsg.trim());
                //发送信息到注册到服务器的各个客户端
                sendGroupMsg(clientMsg,socketChannel);
            }

        } catch (IOException e) {
            try {
                //todo 不懂为啥客户端离线这里会收到读的类型信息
                System.out.println(socketChannel.getRemoteAddress()+"离线了");
                //取消注册
                selectionKey.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendGroupMsg(String msg,SocketChannel socketChannel) throws IOException {

        Set<SelectionKey> selectionKeys = selector.keys();
        for (SelectionKey selectionKey : selectionKeys) {

            Channel targetChannel = selectionKey.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != socketChannel) {
                SocketChannel dest = (SocketChannel)targetChannel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
