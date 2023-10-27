package org.carl.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        //FileChannle
        //1 . 输入流 输出流
        try (FileChannel channel = new FileInputStream("RPC/Netty/src/main/java/org/carl/bytebuffer/data.txt").getChannel()) {
            // 从内存中划分
            ByteBuffer bf = ByteBuffer.allocate(10);
            while (channel.read(bf) != -1) {
                //切换至读模式
                bf.flip();
                System.out.println(bytesToString(bf));
                //切换到写模式
                bf.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //2. RandomAccessFile
    }

    public static String bytesToString(ByteBuffer bf) {
        StringBuilder sb = new StringBuilder();
        while (bf.hasRemaining()) {
            sb.append((char) bf.get());

        }
        return sb.toString();
    }
}
