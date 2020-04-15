package com.dason.netty.dnetty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 接入的范形这里用的Stirng，这样的话，重写的方法的信息不是默认的Object而是String了
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //因为是群聊，所以服务端要发信息到所有的注册的client也就是channel中，这里使用netty一个一个channel组对象
//    GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
    //这个对象，封装了一些方法，在遍历输出信息的到每一个通道的时候，会帮我们自动遍历，具体看下面使用
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //jdk8的时间处理api
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");

    /**
     * 客户端添加到服务端的Selector的时候会触发的事件，第一个发生的事
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //客户端启动连接的时候，y也就是客户端开始加入聊天，通知其他的客户端，然后添加到注册的组中
        Channel channel = ctx.channel();
        //这里直接通知其他客户端，不需要我们手动遍历，这个group会直接遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天" + formatter.format(LocalDateTime.now()) + " \n");
        channelGroup.add(channel);
    }

    /**
     * 开始活动的时候，就会触发这个method，上面的added是触发的连接方法，客户端启动的时候触发，客户端启动之后才会触发该方法，也就是有活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    /**
     * 这个是通用方法，也就是获取服务端传过来的消息，一个要注意的ChannelGroup中的当前的客户端接收信息的格式跟其他的不一样的
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel == (ch)) {
                channel.writeAndFlush("[自己]发了信息：" + msg);
            } else {
                ch.writeAndFlush("【客户】"+channel.remoteAddress() + "发送了消息：" + msg);
            }
        });
    }

    /**
     * 这个method跟前面的刚好相反，是停止活动的时候触发的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线了~");
    }

    /**
     * 这个方法跟前面的added的方法相反，这个是客户端开始移除的时候触发的method
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //通知其他客户端，当前客户端离线了,这里不需要我们手动在分组里面移除，会自动移除，写消息到组内的channel也是会自动遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");
        System.out.println("channelGroup size 当前的活跃客户端个数：" + channelGroup.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
