package com.dason.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * 传统客户端
 *
 * @author chendecheng
 * @since 2020-03-16 09:37
 */
public class OldClient {

    public static void main(String[] args) throws Exception {
        System.out.println("进来吗");
        Socket socket = new Socket("127.0.0.1",9999);

        String fileDir = "/Users/Dason/software/VSCode-darwin-stable.zip";
        InputStream inputStream = new FileInputStream(fileDir);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();
        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
//            System.out.println("正在遍历 ,readCount值是："+readCount+",总字节："+total);
//            if (total > 40960) {
//                System.out.println("zheli");
//                break;
//            }
        }
        System.out.println("发送总字节数：" + total + ",耗时" + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }

}
