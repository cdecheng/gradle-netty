package com.dason.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统非零拷贝数据传输服务端
 *
 * @author chendecheng
 * @since 2020-03-16 09:36
 */
public class OldServer {

    public static void main(String[] args) throws IOException {
        System.out.println("进来l吗");
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try {
                byte[] byteArray = new byte[4096];
                //这里需要死循环一直读取socket传过来的DataInputStream对象的值，因为要多次才能读取完
                while (true) {
                    int read = dataInputStream.read(byteArray,0,byteArray.length);
                    if (-1 == read) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        ServerSocket serverSocket = new ServerSocket(7001);
//
//        while (true) {
//            Socket socket = serverSocket.accept();
//            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//
//            try {
//                byte[] byteArray = new byte[4096];
//
//                while (true) {
//                    int readCount = dataInputStream.read(byteArray, 0, byteArray.length);
//
//                    if (-1 == readCount) {
//                        break;
//                    }
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }

    }

}
