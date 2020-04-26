package com.dason.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    private int count;

    /**
     * 建立连接后，就发十个数据到服务器端，发十次，但是服务器端分几次获取，这个有随机性，所以会存在合并获取也存在一个数据多次获取
     * 这样就会导致服务器端处理数据（反过来客户端也是一样，也会需要接收数据的时候），我们每次传输一个数据过去，但是不是接收端直接接收
     * 因为存在缓冲区，所以是随机获取，就会导致获取的数据不完整，数据可能是一次发送，但是接收就会多次了
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //使用客户端发送10条数据  这里是没有使用编码器，所以需要我们手动创建ByteBuf对象传输
//        for(int i= 0; i< 10; ++i) {
//            ByteBuf buffer = Unpooled.copyLong(i + 1l);
//            ctx.writeAndFlush(buffer);
//        }

        //这是有编码器，自动帮我们编码
        ctx.writeAndFlush(8888888l);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("客户端接收到消息=" + msg);
        System.out.println("客户端接收消息数量=" + (++this.count));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
