package com.dason.netty.dnetty.netty.tcp.protocol;

//协议包

/**
 * 主要的用处是封装一下传输的数据，传输的数据除了，每一个之外加上了改数据的长度属性也记录下来
 * 也就是客户端发送数据的时候进行一下编码，将传输的数据进行封装，不直接传输字节流对象，传输这个对象，加上每一个完整数据的大小
 * 然后服务器端获取到这个数据的时候，根据这个字节长度对该数据进行解码，获取的数据每一次都是完整的，简单来说就是进行数据编码解码
 * 保持数据完整性
 */
public class MessageProtocol {
    private int len; //关键
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
