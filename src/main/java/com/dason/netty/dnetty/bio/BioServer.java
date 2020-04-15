package com.dason.netty.dnetty.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) throws Exception {
        //创建线程池连接池
        ExecutorService newCacheThreadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("服务器启动了");
        //循环等待客户端请求
        while (true) {
            System.out.println("线程信息 id111 =" + Thread.currentThread().getId() + " 名字=" + Thread.currentThread().getName());
            //监听，等待客户端连接
            System.out.println("等待连接....");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            newCacheThreadPool.execute(() -> {
                handler(socket);
            });
        }
    }

    public static void handler(Socket socket){

        byte[] bytes = new byte[1024];

        try {
            System.out.println("线程信息222 id =" + Thread.currentThread().getId() +
                    " 名字=" + Thread.currentThread().getName());
            //获取请求的客户单的数据
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("线程信息333 id =" + Thread.currentThread().getId() + " 名字="
                        + Thread.currentThread().getName());
                //获取输入流的长度（指定长度读取）
                int read = inputStream.read(bytes);
                //如果不等-1就是还能读取到数据
                if (read != -1) {
                    //将字节流转换成字符串，打印出来
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭socket
                System.out.println("关闭和client的连接");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
