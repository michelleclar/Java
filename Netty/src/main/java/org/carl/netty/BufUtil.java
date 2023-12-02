package org.carl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.SystemPropertyUtil;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;

public class BufUtil {

    public static final String NEWLINE = SystemPropertyUtil.get("line.separator", "\n");


    public static void log(ByteBuf byteBuf){
        int i = byteBuf.readableBytes();
        int rows = i / 16 + (i%15 == 0 ? 0:1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(byteBuf.readerIndex()) .append(" write index:").append(byteBuf.writerIndex()) .append(" capacity:").append(byteBuf.capacity()) .append(NEWLINE);
        appendPrettyHexDump(buf,byteBuf);
        System.out.println(buf);
    }


}
