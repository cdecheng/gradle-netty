package com.dason.netty.definedprotocol;

/**
 * 自定义的协议对象
 *
 * @author chendecheng
 * @since 2020-05-02 11:19
 */
//@Data
public class MyProtocol {

    private int contentLength;

    private String body;

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
