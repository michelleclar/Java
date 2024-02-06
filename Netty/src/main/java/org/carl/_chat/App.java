package org.carl._chat;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;
import org.carl._chat.handler.MessageHandler;
import org.carl._chat.protocol.Codec;
import org.carl.protocol.common.Proto;

@Slf4j
public class App {
  static final ChatConfig chatConfig = init();

  public static void main(String[] args) throws InterruptedException {
    new Thread(App::server).start();
    Thread.sleep(2);
    new Thread(App::client).start();
    // new Thread(App::client).start();
  }

  private static void client() {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap client =
          new Bootstrap()
              .group(group)
              .channel(NioSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY, true)
              .handler(
                  new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                      ChannelPipeline pipeline = ch.pipeline();
                      // pipeline.addLast("encoder", new ProtobufEncoder()); // protobuf 编码器

                      pipeline.addLast(new Codec());
                      // pipeline.addLast(
                      // "decoder user", new ProtobufDecoder(Proto.User.getDefaultInstance()));
                      // pipeline.addLast(
                      // "decoder message",
                      // new ProtobufDecoder(Proto.Message.getDefaultInstance()));
                      // pipeline.addLast(
                      // "decoder ping", new ProtobufDecoder(Proto.Ping.getDefaultInstance()));
                      // pipeline.addLast(
                      // "decoder pong", new ProtobufDecoder(Proto.pong.getDefaultInstance()));
                      // pipeline.addLast("message handle", new MessageHandler());

                      pipeline.addLast(new NettyClientHandler());
                    }
                  });

      ChannelFuture future = client.connect(chatConfig.getHost(), chatConfig.getPort()).sync();
      future.channel().closeFuture().sync();
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      group.shutdownGracefully();
    }
  }

  private static void server() {
    // FIXME: need load config to init
    EventLoopGroup boss = new NioEventLoopGroup();

    EventLoopGroup work = new NioEventLoopGroup(chatConfig.getMaxConnect());

    try {
      // 创建启动器对象
      ServerBootstrap server =
          new ServerBootstrap()
              .group(boss, work)
              .channel(NioServerSocketChannel.class)
              .childHandler(
                  new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {

                      ChannelPipeline pipeline = ch.pipeline();
                     pipeline.addLast(new Codec());
                      // pipeline.addLast("encoder", new ProtobufEncoder());

                       // pipeline.addLast("decoder", new ProtobufDecoder(Proto.User.getDefaultInstance()));
                      // ch.pipeline()
                      // .addLast(
                      // "decoder message",
                      // new ProtobufDecoder(Proto.Message.getDefaultInstance()));
                      // ch.pipeline()
                      // .addLast(
                      // "decoder ping", new ProtobufDecoder(Proto.Ping.getDefaultInstance()));
                      // ch.pipeline()
                      // .addLast(
                      // "decoder pong", new ProtobufDecoder(Proto.pong.getDefaultInstance()));

                      ch.pipeline().addLast("message handle", new MessageHandler());
                      // ch.pipeline().addLast(new NettyServerHandler());
                    }
                  });
      ChannelFuture future = server.bind(chatConfig.getPort()).sync();
      log.info("listen port:{}", chatConfig.getPort());
      future.channel().closeFuture().sync();

    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      boss.shutdownGracefully();
      work.shutdownGracefully();
    }
  }

  public static ChatConfig init() {
    int port = 8080;
    int maxConnect = 5;
    String host = "127.0.0.1";
    return new ChatConfig(port, maxConnect, host);
  }

  // TODO:进行Handle拆分，暂定MessageHandle,FileHandel.
  public static class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
      String clientIP = insocket.getAddress().getHostAddress();
      String clientPort = String.valueOf(insocket.getPort());
      log.info("与新的客户端建立连接,ip:{},port:{}", clientIP, clientPort);
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
      log.info("server channelRead...");
      // 读取客户端发送的数据
      Proto.User user = (Proto.User) msg;
      log.info("客户端发送的数据: {} -- {}", user.getUsername(), user.getPassword());
    }

    /** 数据读取完毕 */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      Proto.User user = Proto.User.newBuilder().setUsername("server").setPassword("root").build();
      // 写入数据给客户端
      ctx.writeAndFlush(user);
    }

    /** 处理异常，关闭通道 */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      ctx.channel().close();
      log.error(cause.getMessage());
    }
  }

  public static class NettyClientHandler extends ChannelInboundHandlerAdapter {
    public static final ChannelGroup channels =
        new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /** 通道就绪触发该方法 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      log.info("Client Active");
      // 发送 User POJO 对象到服务器
      // Proto.User user =
      // Proto.User.newBuilder().setUsername("client").setPassword("root").build();
      Proto.Message message =
          Proto.Message.newBuilder().setData("hello").setTo("server").setFrom("client").build();
      ctx.writeAndFlush(message);
    }

    /** 当通道有读取事件时触发该方法 */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      // 读取服务器发送的数据
      Proto.Message message = (Proto.Message) msg;

      log.info("收到服务器响应: {}", message.toString());
    }
  }
}
