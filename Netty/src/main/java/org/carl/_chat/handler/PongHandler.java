package org.carl._chat.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;
import org.carl.protocol.common.Proto.Pong;

@Slf4j
@ChannelHandler.Sharable
public class PongHandler extends SimpleChannelInboundHandler<Pong> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Pong message) {
    InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
    String clientIP = insocket.getAddress().getHostAddress();
    String clientPort = String.valueOf(insocket.getPort());

    log.info("Message from ==> ip:{}:{}", clientIP, clientPort);
    log.info("message body: {}", message.toString());
    // channelHandlerContext.writeAndFlush(message);
  }
}
