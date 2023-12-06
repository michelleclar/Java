package org.carl.chat.server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.carl.chat.message.LoginRequestMessage;
import org.carl.chat.protocol.ProtocolFrameDecoder;
import org.carl.chat.server.handler.*;
import org.carl.comment.server.Server;
import org.carl.protocol.CodecSharable;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
        GroupCreateRequestMessageHandler GROUP_CREATE_HANDLER = new GroupCreateRequestMessageHandler();
        GroupJoinRequestMessageHandler GROUP_JOIN_HANDLER = new GroupJoinRequestMessageHandler();
        GroupMembersRequestMessageHandler GROUP_MEMBERS_HANDLER = new GroupMembersRequestMessageHandler();
        GroupQuitRequestMessageHandler GROUP_QUIT_HANDLER = new GroupQuitRequestMessageHandler();
        GroupChatRequestMessageHandler GROUP_CHAT_HANDLER = new GroupChatRequestMessageHandler();
        QuitHandler QUIT_HANDLER = new QuitHandler();
        Server.start(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                ch.pipeline().addLast(new CodecSharable());
                // 5s 内如果没有收到 channel 的数据，会触发一个 IdleState#READER_IDLE 事件
                ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
                // ChannelDuplexHandler 可以同时作为入站和出站处理器
                ch.pipeline().addLast(new ChannelDuplexHandler() {
                    // 用来触发特殊事件
                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                        IdleStateEvent event = (IdleStateEvent) evt;
                        // 触发了读空闲事件
                        if (event.state() == IdleState.READER_IDLE) {
                            log.debug("已经 5s 没有读到数据了");
                            ctx.channel().close();
                        }
                    }
                });
                ch.pipeline().addLast(LOGIN_HANDLER);
                ch.pipeline().addLast(CHAT_HANDLER);
                ch.pipeline().addLast(GROUP_CREATE_HANDLER);
                ch.pipeline().addLast(GROUP_JOIN_HANDLER);
                ch.pipeline().addLast(GROUP_MEMBERS_HANDLER);
                ch.pipeline().addLast(GROUP_QUIT_HANDLER);
                ch.pipeline().addLast(GROUP_CHAT_HANDLER);
                ch.pipeline().addLast(QUIT_HANDLER);
            }
        });
    }

}