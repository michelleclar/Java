package org.carl.netty.byteBuf;

import io.netty.buffer.ByteBufAllocator;
import org.carl.netty.BufUtil;

public class ByteBuf {
    public static void main(String[] args) {
        io.netty.buffer.ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();

        BufUtil.log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append('a');
        }
        buf.writeBytes(sb.toString().getBytes());
        BufUtil.log(buf);
    }
}
