package org.carl.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.carl.comment.client.Client;
import org.carl.comment.server.Server;
import org.carl.protocol.message.LoginRequestMessage;

/**
 * 魔数，用来在第一时间判定是否是无效数据包
 * 版本号，可以支持协议的升级
 * 序列化算法，消息正文到底采用哪种序列化反序列化方式，可以由此扩展，例如：json、protobuf、hessian、jdk
 * 指令类型，是登录、注册、单聊、群聊... 跟业务相关
 * 请求序号，为了双工通信，提供异步能力
 * 正文长度
 * 消息正文
 */
@Slf4j
public class MyProtocol {
    public static void main(String[] args) {
        new Thread(() -> {
            Server.start(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 解决粘包 半包
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new Codec());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("{}", msg);

                        }
                    });
                }
            });
        }).start();
        new Thread(() -> {
            Client.send(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new Codec());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {

                            LoginRequestMessage message = new LoginRequestMessage("aaa", "123");
                            ctx.writeAndFlush(message);
                        }
                    });
                }
            });
        }).start();
    }
}
