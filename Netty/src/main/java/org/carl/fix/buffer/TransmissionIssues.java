package org.carl.fix.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.carl.comment.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 粘包和半包 问题
 */

public class TransmissionIssues {

    private static final Logger log = LoggerFactory.getLogger(TransmissionIssues.class);

    public static void main(String[] args) {
        Client.send(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                log.debug("connetted...");
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        log.debug("sending...");
//                            Random r = new Random();
//                            char c = 'a';
                        for (int i = 0; i < 10; i++) {
                            ByteBuf buffer = ctx.alloc().buffer();
                            buffer.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                            ctx.writeAndFlush(buffer);
                        }
                    }
                });
            }
        });
    }


}
