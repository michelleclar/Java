package org.carl.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    static MessageDigest md5;
    static String salt = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(String str) {
        byte[] digest = md5.digest((str + salt).getBytes(StandardCharsets.UTF_8));
        return new String(digest);
    }
}
