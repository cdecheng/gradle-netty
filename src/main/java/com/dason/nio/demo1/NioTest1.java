package com.dason.nio.demo1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 一个简单的nio的服务端代码
 *
 * @author chendecheng
 * @since 2020/3/8
 */
public class NioTest1 {

    public static void main(String[] args) throws Exception {

        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        //创建Selector
        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            //创建ServerSockerChannel 用于给客户端连接的端口（中转，要么创建连接的socketChannel要么就是获取连接）
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            //设置为非阻塞模式，因为使用的nio的ServerScoketChannel所以需要配置成非阻塞
            serverSocketChannel.configureBlocking(false);

            //这个整个流程的ServerSocketChannel也需要注册到Selector中
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //绑定ServerSocketChannel的端口
            serverSocketChannel.socket().bind(new InetSocketAddress(ports[i]));

            System.out.println("注册后的当前的SelectionKey的数量为：" + selector.keys().size());
        }

        while (true) {

//            if (selector.select(1000) == 0) {
//                System.out.println("等待1秒，没有连接的事件发生");
//                continue;
//            }

            int number = selector.select();
            System.out.println("当前所有的事件数量："+number);

            //如果存在事件，获取所有的发生的事件的Selector所有的的selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("获取的selectionKeys 数量 = " + selectionKeys.size());

            //添加set的迭代器，方便下面遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            //遍历所有的selectionKeys
            while (keyIterator.hasNext()) {

                SelectionKey next = keyIterator.next();

                //当前的SelectionKey如果是连接事件，注意Acceptable是指连接事件，read是读取事件，write是写入事件
                if (next.isAcceptable()) {
                    //在selectionKey中获取当前的通道(客户端连接的时候通过的创建的连接channel)
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) next.channel();
                    //通过连接的channel获取客户端的socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成了一个 socketChannel ,对应地址" + socketChannel.getRemoteAddress());
                    //设置非阻塞
                    socketChannel.configureBlocking(false);
                    //注册该socketChannel到该线程selector中，指定为读取类型，并且创建一个字节缓冲块
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后 ，注册的selectionkey 数量=" + selector.keys().size()); //2,3,4..
                }

                //如果是读取的事件（注意的是，前面创建连接的事件，是使用的我们定义的那个几个连接端口，到了这里读取的时候，使用的是
                // 创建的连接的之后分配的端口，都是注册到Selector上面的,二次数据交互都是客户端直接通过随机的端口连接的）
                if (next.isReadable()) {

                    //通过当前的SelectionKey 获取通过
                    SocketChannel socketChannel = (SocketChannel) next.channel();

                    System.out.println("发送消息的客户端地址："+socketChannel.getRemoteAddress());

                    //通过当前的SelectionKey 获取客户端发过来的缓冲字节块(一次全拿)
//                    ByteBuffer byteBuffer = (ByteBuffer) next.attachment();

                    //分开用传统的io循环读取的方式取客户端传过来的数据
                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        byteBuffer.clear();//清空一下
                        //读取值到缓冲区中
                        int read = socketChannel.read(byteBuffer);
                        if (read <= 0) {
                            break;
                        }
                        System.out.println("form 客户端 " + new String(byteBuffer.array())+"；总长度："+read);

                        byteBuffer.flip();

                        while (byteBuffer.hasRemaining()) {
                            socketChannel.write(byteBuffer);
                        }

                    }

                }

                //迭代器中移除该SelectionKey，避免重复遍历
                keyIterator.remove();
            }
        }
    }

}
