package org.carl.bytebuffer;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.carl.bytebuffer.ByteBufferUtil.debugAll;

public class TestByteBuffer {
    public static void main(String[] args) {
        read();
    }

    public static void test() {
        //FileChannle 阻塞模式 不能使用selector
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

    public static void read() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        debugAll(buffer);
        buffer.flip();
        debugAll(buffer);
    }

    public static void api(String path) throws IOException {
        //java 堆内存 gc会清理
        System.out.println(ByteBuffer.allocate(16).getClass());
        // 直接内存 （少一次数据拷贝）gc不会清理 分配内存效率低 可能引发内存泄漏
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
        ByteBuffer buf = ByteBuffer.allocateDirect(16);
        //写数据
        FileChannel channel = new FileInputStream(path).getChannel();
        channel.read(buf);
        buf.put((byte) 127);
        //读
        channel.write(buf);
        byte b = buf.get();
        buf.rewind();//position = 0
        buf.get(1); // 不改变 position
        buf.get(new byte[4]); // byte
        //position
        buf.mark(); //记录position位置
        buf.reset(); //将position重置到mark位置

        //字符串和ByteBuffer 互相转换
        //String ----> ByteBuffer
        buf = ByteBuffer.allocate(16);
        buf.put("hello".getBytes());

        //2. Charset
        Charset.defaultCharset();// 操作系统默认
        ByteBuffer bf = StandardCharsets.UTF_8.encode("hello");

        //3.wrap
        ByteBuffer.wrap("hello".getBytes());
        //ByteBuffer ----> String
        String string = StandardCharsets.UTF_8.decode(bf).toString();
        Charset.defaultCharset().encode("hello");

    }

    public static void scattering(String path) {
        try (RandomAccessFile file = new RandomAccessFile(path, "rw")) {
            FileChannel channel = file.getChannel();
            ByteBuffer a = ByteBuffer.allocate(3);
            ByteBuffer b = ByteBuffer.allocate(3);
            ByteBuffer c = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{a, b, c});
            a.flip();
            b.flip();
            c.flip();
        } catch (IOException e) {
        }
    }

    public static void gathering(String path) {
        ByteBuffer a = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer c = StandardCharsets.UTF_8.encode("hello");

        try (FileChannel channel = new RandomAccessFile(path, "rw").getChannel()) {
            channel.write(new ByteBuffer[]{a, b, c});
        } catch (IOException e) {
        }

    }
}
