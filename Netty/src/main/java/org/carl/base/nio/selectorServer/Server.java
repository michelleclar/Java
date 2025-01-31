package org.carl.base.nio.selectorServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.carl.base.bytebuffer.ByteBufferUtil.debugRead;


public class Server {
    static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        //管理channel
        Selector sr = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//默认是true false 非阻塞 accept read 没有连接accept会直接返回空 read 返回0
        //建立联系 通过key可以知道那个时间发生的事件
        //事件类型 accept：连接请求事件 connect：客户端连接建立 read：可读 write：可写
        SelectionKey sscKey = ssc.register(sr, 0, null);

        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));
        log.info("register key:{}", sscKey);
        List<SocketChannel> channelList = new ArrayList<>();
        while (true) {
            sr.select(); //没有事件发生 线程阻塞 有事件 线程才会恢复
            Iterator<SelectionKey> ite = sr.selectedKeys().iterator();

            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                ite.remove();
                int ops = key.readyOps();
                log.info("key:{}", key);
                switch (ops) {
                    case SelectionKey.OP_ACCEPT -> {

                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel sc = channel.accept();
                        log.info("{}", sc);
                        sc.configureBlocking(false);
                        ByteBuffer bf = ByteBuffer.allocate(16);
                        SelectionKey scKey = sc.register(sr, 0, bf);
                        scKey.interestOps(SelectionKey.OP_READ);

                        // 向客户端发送内容
                        String msg = getMessage();
                        ByteBuffer buffer = Charset.defaultCharset().encode(msg);
                        int write = sc.write(buffer); //返回实际写入的字节数
                        if (buffer.hasRemaining()) {
                            //关注可写事件
                            System.out.println(scKey.interestOpsOr(SelectionKey.OP_WRITE));
                            scKey.attach(buffer);
                        }

                    }
                    case SelectionKey.OP_READ -> {
                        try {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer bf = (ByteBuffer) key.attachment(); // 获取附件 key.attach():替换附件

                            channel.read(bf); // 正常断开 返回值是 -1
                            bf.flip();
                            debugRead(bf);
                        } catch (IOException e) {
                            log.error("{}", e.getMessage());
                            key.cancel();
                        }
                    }

                    case SelectionKey.OP_WRITE -> {
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        SocketChannel sc = (SocketChannel) key.channel();
                        int write = sc.write(buffer);
                        if (!buffer.hasRemaining()) {
                            key.attach(null);
                            key.interestOpsAnd(key.interestOps() - SelectionKey.OP_WRITE);
                        }
                    }
                }


            }

        }
    }

    private static String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("a".repeat(300000000));
        return sb.toString();
    }
}
