package org.carl.netty.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.carl.netty.BufUtil;

public class Slice {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes(new byte[]{'a','b','c','d','e'});
        BufUtil.log(buffer);

        ByteBuf s1 = buffer.slice(0, 2);
        s1.retain(); // 引用加 1 将内存释放交给 slice自己
        ByteBuf s2 = buffer.slice(2, 3);

        BufUtil.log(s1);
        BufUtil.log(s2);
        //切片完的buf 不能在添加数据
        buffer.release(); // 释放内存

    }
}
