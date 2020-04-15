package com.dason.netty.dnetty.netty.heartbeat;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import static io.netty.handler.timeout.IdleState.*;

/**
 * 心跳事件的handler
 * ps:注意ChannelInboundHandlerAdapter跟ChannelOutboundHandlerAdapter的区别
 * 入站出站都是相对而言的，对于客户端而言流入就是入站，流出就出站，目前我们写的客户端的handler都是处理回来的数据的，所以一般是入站handler
 * 然后同理对于服务器端，进来数据处理就是入站，出来就是出站，一般我们处理的数据都是客户端传过来的，起码目前位置都是接收到数据处理，所以都用入站
 *
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    /**
     * 这个方法是用户事件触发的方法，按理可以用于各种事件触发的方法的
     * 有个入参，是上一个handler处理或者获取后传过来的对应的事件，进行相关的操作的时候判断是否就是我们想要的事件
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //做一个过滤保证这个事件不会处理错，实际的对应事件处理应该在这里处理的，根据事件类型的值做处理要么通知客户端之类
        if (evt instanceof IdleStateEvent) {

            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(READER_IDLE)) {
                System.out.println(ctx.channel().remoteAddress() + "--没有读事件,可以进行相关业务处理--");
            }
            if (event.state().equals(WRITER_IDLE)) {
                System.out.println(ctx.channel().remoteAddress() + "--没有写事件,可以进行相关业务处理--");
            }
            if (event.state().equals(ALL_IDLE)) {
                System.out.println(ctx.channel().remoteAddress() + "--没有读写事件,可以进行相关业务处理--");
            }
        }
        //
    }
}
