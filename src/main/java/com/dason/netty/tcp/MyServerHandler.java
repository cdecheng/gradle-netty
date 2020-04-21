package com.dason.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        //将buffer转成字符串
        String message = new String(buffer, Charset.forName("utf-8"));

        System.out.println("服务器接收到数据 " + message);
        System.out.println("服务器接收到消息量=" + (++this.count));

        //netty服务端中转测试-----start
        //new 一个 Bootstrap对象
//        Bootstrap bootstrap = new Bootstrap();
//        bootstrap.group(ctx.channel().eventLoop()).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {//初始化一个匿名的管道对象
//            //给管道添加自定义的handler
//            @Override
//            protected void initChannel(SocketChannel ch) throws Exception {
//                ch.pipeline().addLast(new NettyTransitClientHandler());
//            }
//        });
//        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8091).sync();
//        channelFuture.channel().closeFuture().sync();
        //netty服务端中转测试-------end

        //服务器回送数据给客户端, 回送一个随机id ,
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString() + " ", Charset.forName("utf-8"));
        ctx.writeAndFlush(responseByteBuf);

    }
}
