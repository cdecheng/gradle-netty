package com.dason.nio.simpledemo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio服务器端
 * 连接过程
 * 1》预先工作，先是服务器端创建一个通道用于给客户端连接，然后也需要将这个创建通道注册到Selector中
 * 2》服务器端ServerSocketChannel创建一个客户端socketChannel
 * 3》客户端的通道socketChannel的register方法注册到Selector中，生成一个selectedKey，这个是跟通道关联起来的
 * 4》调用Selector的select方法，获取现在发生的事件的通道个数
 * 5》如果事件发生的数量大于0说明有事件发生
 * 6》通过selector的selectedKeys方法获取所有的(有事件发生的的通道)连接到selector的通道的selectedKey
 * 7》遍历selectedKeys获取通道，然后进行对应的操作，根据对应类型进行对应操作
 * */
public class NioServer {

    public static void main(String[] args) throws Exception {

        //创建ServerSockerChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //创建Selector
        Selector selector = Selector.open();

        //绑定ServerSocketChannel的端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置为非阻塞模式，因为使用的nio的ServerScoketChannel所以需要配置成非阻塞
        serverSocketChannel.configureBlocking(false);

        //这个整个流程的ServerSocketChannel也需要注册到Selector中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("注册后的当前的SelectionKey的数量为：" + selector.keys().size());

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("等待1秒，没有连接的事件发生");
                continue;
            }
            //如果存在事件，获取该Selector所有有事件发生的selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("获取的selectionKeys 数量 = " + selectionKeys.size());
            //添加set的迭代器，方便下面遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            //遍历所有的selectionKeys
            while (keyIterator.hasNext()) {

                SelectionKey next = keyIterator.next();

                //当前的SelectionKey如果是连接事件，注意Acceptable是指连接事件，read是读取事件，write是写入事件
                if (next.isAcceptable()) {
                    //通过ServerSocketChannel创建该事件的socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成了一个 socketChannel " + socketChannel.hashCode());
                    //设置非阻塞
                    socketChannel.configureBlocking(false);
                    //注册该socketChannel到该线程selector中，指定为读取类型，并且创建一个字节缓冲块
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后 ，注册的selectionkey 数量=" + selector.keys().size()); //2,3,4..
                }

                //如果是读取的事件
                if (next.isReadable()) {
                    //通过当前的SelectionKey 获取通过
                    SocketChannel channel = (SocketChannel)next.channel();
                    //通过当前的SelectionKey 获取客户端发过来的缓冲字节块
                    ByteBuffer byteBuffer = (ByteBuffer) next.attachment();
                    //将数据写入该通道中
                    channel.read(byteBuffer);
                    System.out.println("form 客户端 " + new String(byteBuffer.array()));

                }
                //迭代器中移除该SelectionKey，避免重复遍历
                keyIterator.remove();
            }
        }
    }
}
