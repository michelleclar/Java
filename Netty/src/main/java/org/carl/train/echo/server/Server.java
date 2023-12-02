package org.carl.train.echo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
    public static void main(String[] args) {
        // 创建启动器对象
        new ServerBootstrap()
                // 事件处理 accept read
                .group(new NioEventLoopGroup())
                // OIO BIO NIO
                .channel(NioServerSocketChannel.class)
                // 具体实现 能执行那些操作
                .childHandler(
                        // 和客户端进行数据读写的通道 负责添加其他handle
                        new ChannelInitializer<NioSocketChannel>() {
                            //连接后的操作
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                // 解码 （ByteBuf --- > str）
                                ch.pipeline().addLast(new StringDecoder());
                                // 自定义
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    // 读事件
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = ctx.alloc().buffer();
                                        log.info("client msg :{}",msg);
                                        String _msg = "server:" + msg;
                                        buf.writeBytes(_msg.getBytes());
                                        ctx.writeAndFlush(buf);
                                    }
                                });
//                                ch.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
//                                    @Override
//                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//
//                                        super.write(ctx, msg, promise);
//                                    }
//                                })
                            }
                        })
                //绑定端口
                .bind(8080);
    }
}
