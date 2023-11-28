package org.carl.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

@Slf4j
public class NettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop next = new NioEventLoopGroup().next();
        //
        DefaultPromise<Integer> promise = new DefaultPromise<>(next);


        new Thread(() ->
        {
            log.info("do");
            try {
                Thread.sleep(1000);

            } catch (InterruptedException ignored) {
               ignored.printStackTrace();
            }
            promise.setSuccess(80);
        }).start();

        log.info("wait result");
        log.info("result: {} ",promise.get());
    }
}
