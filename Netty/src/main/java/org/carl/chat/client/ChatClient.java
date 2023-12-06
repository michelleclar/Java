package org.carl.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.carl.chat.message.LoginRequestMessage;
import org.carl.chat.message.PingMessage;
import org.carl.chat.protocol.ProtocolFrameDecoder;
import org.carl.comment.client.Client;
import org.carl.protocol.CodecSharable;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ChatClient {
    public static void main(String[] args) {

        AtomicBoolean EXIT = new AtomicBoolean(false);
        Client.send(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                ch.pipeline().addLast(new CodecSharable());
                // 3s 内如果没有向服务器写数据，会触发一个 IdleState#WRITER_IDLE 事件
                ch.pipeline().addLast(new IdleStateHandler(0, 3, 0));
                // ChannelDuplexHandler 可以同时作为入站和出站处理器
                ch.pipeline().addLast(new ChannelDuplexHandler() {
                    // 用来触发特殊事件
                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                        IdleStateEvent event = (IdleStateEvent) evt;
                        // 触发了写空闲事件
                        if (event.state() == IdleState.WRITER_IDLE) {
//                                log.debug("3s 没有写数据了，发送一个心跳包");
                            ctx.writeAndFlush(new PingMessage());
                        }
                    }
                });
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
                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        log.debug("连接已经断开，按任意键退出..");
                        EXIT.set(true);
                    }

                    // 在出现异常时触发
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        log.debug("连接已经断开，按任意键退出..{}", cause.getMessage());
                        EXIT.set(true);
                    }
                });
            }
        });
    }

}