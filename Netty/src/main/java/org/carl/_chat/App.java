package org.carl._chat;

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
import io.netty.util.concurrent.GlobalEventExecutor;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.carl._chat.handler.MessageHandler;
import org.carl._chat.handler.PingHandler;
import org.carl._chat.handler.PongHandler;
import org.carl._chat.protocol.Codec;
import org.carl.protocol.common.Proto;

@Slf4j
public class App {

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

                      pipeline.addLast("编解码", new Codec());

                      pipeline.addLast(new NettyClientHandler());

                      pipeline.addLast("pong handle", new PongHandler());
                    }
                  });

      ChannelFuture future =
          client.connect(ChatConfig.getHost(), ChatConfig.getServerPort()).sync();
      future.channel().closeFuture().sync();
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      group.shutdownGracefully();
    }
  }

  private static void server() {
    EventLoopGroup boss = new NioEventLoopGroup();

    EventLoopGroup work = new NioEventLoopGroup(ChatConfig.getMaxConnect());

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
                      pipeline.addLast("编解码", new Codec());
                      pipeline.addLast("message handle", new MessageHandler());
                      pipeline.addLast("ping handle", new PingHandler());
                      // ch.pipeline().addLast(new NettyServerHandler());
                    }
                  });
      ChannelFuture future = server.bind(ChatConfig.getServerPort()).sync();
      log.info("listen port:{}", ChatConfig.getServerPort());
      future.channel().closeFuture().sync();

    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      boss.shutdownGracefully();
      work.shutdownGracefully();
    }
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
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR =
        Executors.newSingleThreadScheduledExecutor();
    public static final ChannelGroup channels =
        new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /** 通道就绪触发该方法 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      log.info("Client Active");
      // 发送 User POJO 对象到服务器
      // Proto.User user =
      // Proto.User.newBuilder().setUsername("client").setPassword("root").build();
      // Proto.Message message =
      // Proto.Message.newBuilder().setData("hello").setTo("server").setFrom("client").build();
      Proto.Ping ping = Proto.Ping.newBuilder().setData("ping").build();
      ctx.writeAndFlush(ping);
//      ScheduledFuture<?> scheduleAtFixedRate =
//          SCHEDULED_EXECUTOR.scheduleAtFixedRate(
//              () -> {
//                ctx.writeAndFlush(ping);
//              },
//              1,
//              3,
//              TimeUnit.SECONDS);
//
      TimeUnit.SECONDS.sleep(8);
//      scheduleAtFixedRate.cancel(true);
    }
  }
}
