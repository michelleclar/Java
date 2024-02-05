package org.carl._chat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.carl.protocol.common.Proto;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<Proto.Message> {
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Proto.Message message) {
    log.info("client message: {}", message);
    // channelHandlerContext.writeAndFlush(message);
  }
}
