package org.carl.base.aio;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.carl.base.bytebuffer.ByteBufferUtil.debugAll;

public class AioFileChannel {
    private static final Logger log = LoggerFactory.getLogger(AioFileChannel.class);
    public static void main(String[] args) {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("data.txt"), StandardOpenOption.READ)) {
            //1ByteBuffer 2读取位置 3附件 4回调对象
            ByteBuffer bf = ByteBuffer.allocate(16);
            log.info("read begin...");
            channel.read(bf, 0, bf, new CompletionHandler<Integer, ByteBuffer>() {
                @Override//read success
                public void completed(Integer result, ByteBuffer attachment) {
                    log.info("read completed");
                    attachment.flip();
                    debugAll(attachment);
                }

                @Override//read fail
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();

                }
            });

            log.info("read end");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.yield();
    }
}
