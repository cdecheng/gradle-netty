package com.dason.netty.dnetty.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 一个简单的netty案例，用于理解netty的整个Reactor的架构
 * 1》一个MainReactor（BossLoopGroup，主要用于创建客户端的连接事件）
 * 2》多个WorkReactor (一组WorkLoopGroup，用于处理实际的读写业务的分发问题)
 * 3》一组workLoop分发的请求到到管道中执行，管道包含很多我们需要的信息，而且可以支持再handler中使用线程池中执行，不过一般看业务需求
 * 在根据业务情况分析是扩张bossloopGroup还是扩展WorkerLoopGroup，都是可以扩展指定大小的，应付高并发要求
 * 4》这样一来，mainReactor不是单个，worker也不是单个，都是可以扩展的，甚至处理的handler也可以使用线程池优化
 * 5》这就是netyy对io的扩展模型的优化，根据基于可伸缩的io模式设计
 */
public class NettyServer {

    public static void main(String[] args) throws Exception {

        //创建两个EventLoopGroup 一个是主的进行连接accept，一个是worker的，整个new NioEventLoopGroup 默认会根据当前系统的线程数*2
        //来进行创建该循环的数量，我们也可以指定，创建的入参指定即可
//        默认实际 cpu核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();//启动器
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//指定的这个NioServerSockerChannel作为通道的实现，也可以指定别的
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置连接线程状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//初始化一个匿名的管道对象
                        //给管道添加自定义的handler
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            //绑定端口，启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(6669).sync();

            //给netty的启动添加异步返回数据，主要是通过Future-listener，返回结果通过监听器获取并且异步执行相关操作，感觉用处不大
            //给cf 注册监听器，监控我们关心的事件

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("监听端口 6668 成功");
                    } else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });

            //监控连接关闭，关闭的时候才会触发
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
