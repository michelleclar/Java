package org.carl.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.carl.chat.protocol.CodecSharable;
import org.carl.chat.protocol.ProtocolFrameDecoder;
import org.carl.chat.server.handler.RpcRequestMessageHandler;
import org.carl.comment.server.Server;

public class RpcServer {
    public static void main(String[] args) {
        Server.start(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(new CodecSharable());
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                ch.pipeline().addLast(new RpcRequestMessageHandler());
            }
        });
    }
}
