package org.carl.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.carl.chat.message.LoginRequestMessage;
import org.carl.chat.protocol.ProtocolFrameDecoder;
import org.carl.comment.server.Server;
import org.carl.protocol.CodecSharable;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        Server.start(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                ch.pipeline().addLast(new CodecSharable());
//                ch.pipeline().addLast(new MessageCodecSharable());
                ch.pipeline().addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {

                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
                        log.debug("{}", loginRequestMessage);
                    }
                });
            }
        });
    }

}