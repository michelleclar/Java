package org.carl.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * Written by Mr. Carl
 *
 * @description: TODO
 * @version: 1.0
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws InterruptedException {
//        syncClient();
        asyncClient();
    }

    static void asyncClient() throws InterruptedException {
        ChannelFuture channelFuture = getChannelFuture();
        channelFuture.sync();
        channelFuture.addListener((ChannelFutureListener) future -> {
            Channel channel = future.channel();
            log.info("{}", channel);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    channel.close();
                    log.info("关闭channel");
                    break;
                }
                channel.writeAndFlush(line);
            }
        });
    }

    static void syncClient() throws InterruptedException {
        Channel channel = getChannel();
        channel.writeAndFlush("hi");
    }

    static Channel getChannel() throws InterruptedException {

        return new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override //连接建立后被调用 会触发 连接连接事件 accept
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
//                .connect("127.0.0.1", 8080)
                .connect(new InetSocketAddress("localhost", 8080))
                .sync() // 阻塞方法 直到连接建立
                .channel();
    }

    static ChannelFuture getChannelFuture() throws InterruptedException {
        // 带有Future Promise 的类型都是和异步方法配套使用，用来处理结果的
        return new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override //连接建立后被调用 会触发 连接连接事件 accept
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                //异步非阻塞（nio线程）
                .connect(new InetSocketAddress("localhost", 8080));
    }

}
