package com.dason.netty.dnetty.netty.httpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 给处理管道添加handler的方法，注意继承的类对象，这里的pipeline可以链式编程也可以这种对象方式
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //可以直接链式编程，也可以给该netty默认的处理http请求的对象，编码或者解码的处理器
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());
        //添加作为一个http服务接受到请求我们处理获取的数据以及返回给浏览器端的数据的自定义的handler
        pipeline.addLast("myHandler", new HttpHandler());
    }
}
