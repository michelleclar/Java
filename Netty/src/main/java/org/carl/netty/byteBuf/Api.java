package org.carl.netty.byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class Api {
    public static void main(String[] args) {
        io.netty.buffer.ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeBoolean(true)
                .writeByte(3)
                .writeShort(3)
                .writeInt(3)
                .writeIntLE(3)
                .writeLong(3L)
                .writeChar(3)
                .writeChar(3);
        //write 会改变写指针位置 set不会改变写指针位置

    }
}
