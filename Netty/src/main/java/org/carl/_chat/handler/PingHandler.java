package org.carl._chat.handler;

import java.net.InetSocketAddress;
import org.carl.protocol.common.Proto.Ping;
import org.carl.protocol.common.Proto.Pong;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class PingHandler extends SimpleChannelInboundHandler<Ping> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Ping message) {
    InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
    String clientIP = insocket.getAddress().getHostAddress();
    String clientPort = String.valueOf(insocket.getPort());

    log.info("Message from ==> ip:{}:{}", clientIP, clientPort);
    log.info("message body: {}", message.toString());

    Pong ping = Pong.newBuilder().setData("pong").build();

    ctx.writeAndFlush(ping);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent idleStateEvent) {
      // 该事件需要配合 io.netty.handler.timeout.IdleStateHandler使用
      if (idleStateEvent.state() == IdleState.READER_IDLE) {
        // 超过指定时间没有读事件,关闭连接
        log.info("超过心跳时间,关闭和服务端的连接:{}", ctx.channel().remoteAddress());
        // ctx.channel().close();
      }
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }
}
