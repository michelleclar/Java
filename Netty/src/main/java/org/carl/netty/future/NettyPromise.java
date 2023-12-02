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
                int i = 1/0;
                Thread.sleep(1000);

                promise.setSuccess(80);
            } catch (InterruptedException e) {
               e.printStackTrace();
               promise.setFailure(e);

            }
        }).start();

        log.info("wait result");
        log.info("result: {} ",promise.get());
    }
}
