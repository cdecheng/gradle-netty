package com.dason.netty.dnetty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 群聊的客户端
 * 1》连接服务端
 * 2》客户端输入数据传输到服务端
 * 3》获取服务端传回来的数据
 */
public class GroupChatClient {

    private Selector selector;
    private SocketChannel socketChannel;
    private final String IPADDRESS = "127.0.0.1";
    private final Integer PORT = 6969;
    private String username;

    public GroupChatClient() throws IOException {

        selector = Selector.open();
        socketChannel = socketChannel.open(new InetSocketAddress(IPADDRESS, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getRemoteAddress().toString().substring(1);
        System.out.println("客户端" + username + " ---上线啦");

    }

    private void sendInfo(String msg){
        msg = username + "说" + msg;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMsg(){

        try {
            int count = selector.select();
            if (count > 0) {
                //todo 不懂这里为啥是获取所有的SelectionKeys,不是应该只获取当前的客户端的通道的吗
                // have prove 这里确实是只有当前的通道
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        SocketChannel channel = (SocketChannel)next.channel();
                        channel.read(byteBuffer);
                        String msg = new String(byteBuffer.array());
                        System.out.println("收到服务端传过来的信息：" + msg);
                    }
                }
                iterator.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        GroupChatClient groupChatClient = new GroupChatClient();

        //创建一个线程，3秒去请求一次服务端发到通过的信息
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    groupChatClient.readMsg();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //获取控制台的数据输入
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            groupChatClient.sendInfo(s);
        }

    }


}
