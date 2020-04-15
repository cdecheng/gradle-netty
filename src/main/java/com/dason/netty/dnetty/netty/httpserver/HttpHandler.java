package com.dason.netty.dnetty.netty.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter的子类
 * HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 * 这个主要处理请求的handler，需要注意一下，这边需要处理一下过滤，因为除了浏览器的我们的请求之外
 * 还会存在一些相关的请求，例如突变的请求，这个实际业务中我们需要在静态或者指定的目录中获取封装成http响应返回
 * 这里就不做处理，直接过滤掉这个请求
 * 然后我们实际的请求，就返回一句话，就是能接收到浏览器的请求，也能响应即可（如果真的做一个web服务器还要处理很多，需要将对应的请求调用到实际的
 * 应用，还要将该应用用线程跑起来，然后进行请求，然后静态文件的返回也要分开处理引入缓存等，相当复杂）
 *
 */
public class HttpHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            //这个消息实际上就是一个客户端的请求，也就是一个httpRequest对象，可以在这个对象中获取到所有的请求的信息
            HttpRequest httpRequest = (HttpRequest) msg;
            /**
             * 这里过滤网页请求会自动请求favicon.ico的请求，简单粗暴处理
             */
            URI uri = new URI(httpRequest.uri());
            if (uri.getPath().equals("/favicon.ico")) {
                System.out.println("这个请求不作响应");
                return;
            }
            //实际是应该获取请求信息，然后处理之后，然后将数据返回的，这里就直接返回一句话
            ByteBuf byteBuf = Unpooled.copiedBuffer("为啥中文不行", CharsetUtil.UTF_8);
            //封装返回信息
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            //设置请求头
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            ctx.writeAndFlush(httpResponse);
        }
    }
}
