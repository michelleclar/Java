package org.carl.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.carl.chat.message.LoginRequestMessage;
import org.carl.chat.protocol.ProtocolFrameDecoder;
import org.carl.comment.client.Client;
import org.carl.protocol.CodecSharable;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        Client.send(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                ch.pipeline().addLast(new CodecSharable());
                ch.pipeline().addLast("client handler", new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        new Thread(() -> {
                            var scanner = new Scanner(System.in);
                            System.out.println("put username");
                            var name = scanner.nextLine();
                            System.out.println("put password");
                            var password = scanner.nextLine();
                            LoginRequestMessage message = new LoginRequestMessage(name, password);
                            ctx.writeAndFlush(message);
                            try {
                                System.in.read();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }, "system in").start();
                    }

                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("s->c:{}", msg);
                    }
                });
            }
//        client();
        });
    }

}