package org.carl.bytebuffer;

import java.nio.ByteBuffer;

public class train {

    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);

    }

    public static void split(ByteBuffer source) {
        source.flip();
        int len = source.limit();
        StringBuilder sb = new StringBuilder();
        int mark = 0;
        for (int i = 0; i < len; i++) {

            if (source.get(i) == '\n') {
                System.out.println(sb.toString());
                sb.delete(0, i);
                mark = i + 1;
            } else sb.append((char) source.get(i));
        }
        source.position(mark);
        source.compact();
    }

}
