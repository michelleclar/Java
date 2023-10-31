package org.carl.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.carl.bytebuffer.ByteBufferUtil.debugAll;

@Slf4j
public class AioFileChannel {
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
