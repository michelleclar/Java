package org.carl.fix.buffer;

import lombok.extern.slf4j.Slf4j;
import org.carl.comment.client.Client;

// 可以解决粘包
@Slf4j
public class ShoutLink {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Client.send();
        }
    }

}
