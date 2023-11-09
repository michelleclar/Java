package org.carl.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Written by Mr. Carl
 *
 * @description: TODO
 * @version: 1.0
 */
public class EventLoopServer {
    private static final Logger log = LoggerFactory.getLogger(EventLoopServer.class);

    public static void main(String[] args) {
        // 处理业务时间长的任务
        EventLoopGroup group = new DefaultEventLoop();

        new ServerBootstrap()
                //boss(ServerSocketChannel accept) worker(socketChannel read,write) 细化执行
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        ch.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("server receive msg:" + buf.toString((Charset.defaultCharset())));
                                ctx.fireChannelRead(msg); // 消息传递给下一个handler
                            }
                        }).addLast(group, "handler2", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("server receive msg:" + buf.toString((Charset.defaultCharset())));
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
