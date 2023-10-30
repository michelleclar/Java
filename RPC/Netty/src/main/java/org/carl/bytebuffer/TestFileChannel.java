package org.carl.bytebuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFileChannel {
    public static void main(String[] args) throws IOException {
        walkFileTree();
    }

    public static void walkFileTree() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("/home/carl/.jdks/corretto-17.0.8.1"),new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("------>"+dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(dirCount.get());
        System.out.println(fileCount.get());
    }

    public static void transferTo() {
        try (FileChannel from = new FileInputStream("data.txt").getChannel();
             FileChannel to = new FileOutputStream("to.txt").getChannel();) {
            //效率高 0 拷贝 优化 文件数据有上限（2G）
            long size = from.size();
            long len = size;
            while (len>0){
                len -= from.transferTo(size-len, len, to);
            }
        } catch (IOException e) {
        }
    }

    public static void path(){
        //支持. ..
        Paths.get("data.txt"); //相对路径 user.dir
        Paths.get("Netty","projects"); //相对路径 user.dir
    }
}
