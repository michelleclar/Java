package org.carl.train.echo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
  public static void main(String[] args) throws InterruptedException {
    asyncClient();
    // moreClient();
  }

  private static void moreClient() throws InterruptedException {
    NioEventLoopGroup group = new NioEventLoopGroup();
    Channel channel = new Bootstrap().group(group).channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<NioSocketChannel>() {
          @Override
          protected void initChannel(NioSocketChannel ch) throws Exception {
            ch.pipeline().addLast(new StringEncoder());

            // ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) {

                ByteBuf buffer = (ByteBuf) msg;
                System.out.println(buffer.toString(Charset.defaultCharset()));
                // 思考：需要释放 buffer 吗
              }
            });
          }
        }).connect("127.0.0.1", 8080).sync().channel();

    channel.closeFuture().addListener(future -> {
      group.shutdownGracefully();
    });

    new Thread(() -> {
      Scanner scanner = new Scanner(System.in);
      while (true) {
        String line = scanner.nextLine();
        if ("q".equals(line)) {
          channel.close();
          break;
        }
        channel.writeAndFlush(line);
      }
    }).start();
  }

  static void asyncClient() throws InterruptedException {
    ChannelFuture channelFuture = getChannelFuture();
    // 会阻塞线程
    channelFuture.addListener((ChannelFutureListener) future -> {
      Channel channel = future.channel();
      log.info("{}", channel);
      new Thread(() -> {
        Scanner scanner = new Scanner(System.in);
        while (true) {
          String line = scanner.nextLine();
          if ("q".equals(line)) {
            channel.close();
            break;
          }
          channel.writeAndFlush(line);
        }
      }).start();
    });
  }

  static ChannelFuture getChannelFuture() throws InterruptedException {
    // 带有Future Promise 的类型都是和异步方法配套使用，用来处理结果的
    NioEventLoopGroup group = new NioEventLoopGroup();
    return new Bootstrap().group(group).channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<NioSocketChannel>() {
          @Override // 连接建立后被调用 会触发 连接连接事件 accept
          protected void initChannel(NioSocketChannel ch) throws Exception {

            ch.pipeline().addLast(new StringEncoder());
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
              @Override
              public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                System.out.println(buf);
                super.channelRead(ctx, msg);
              }
            });
            ch.closeFuture().addListener(future -> {
              group.shutdownGracefully();
            });
          }
        })
        // 异步非阻塞（nio线程）
        .connect(new InetSocketAddress("localhost", 8080));
  }
}
