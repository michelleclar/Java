package org.carl.netty.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import org.carl.netty.BufUtil;

public class Composite {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1,2,3,4,5});
        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{1,2,3,4,5});


        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        // 带Boolean 为改变读写指针位置
//        buffer.addComponents(true,buf1,buf2);
        buffer.addComponents(buf1,buf2);
        BufUtil.log(buffer);

    }
}
