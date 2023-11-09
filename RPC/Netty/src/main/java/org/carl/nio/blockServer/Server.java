package org.carl.nio.blockServer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static org.carl.bytebuffer.ByteBufferUtil.debugRead;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        //阻塞模式 单线程
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); //默认是true false 非阻塞 accept read 没有连接accept会直接返回空 read 返回0
        ssc.bind(new InetSocketAddress(8080));
        ByteBuffer bf = ByteBuffer.allocate(16);
        List<SocketChannel> channelList = new ArrayList<>();
        while (true) {
            //tcp
            log.info("connecting");
            SocketChannel sc = ssc.accept();
            log.info("connected{}", sc);
            channelList.add(sc);
            channelList.forEach(o ->
                    {
                        try {
                            log.info("before...read...{}", o);
                            o.read(bf);
                            bf.flip();
                            debugRead(bf);
                            bf.clear();
                            log.info("after...read...{}", o);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }

    }
}
