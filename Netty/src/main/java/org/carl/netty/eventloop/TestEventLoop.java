package org.carl.netty.eventloop;

import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.TimeUnit;

/**
 * Written by Mr. Carl
 *
 * @description: TODO
 * @version: 1.0
 */

public class TestEventLoop {
    private static final Logger log = LoggerFactory.getLogger(TestEventLoop.class);
    public static void main(String[] args) {
        //创建事件循环组 （io事件 普通任务 定时任务）
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        //获取下一个事件循环对象
        System.out.println(group.next());
        //执行普通任务(异步处理)
//        group.next().submit(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            log.info("ok");
//        });
//        log.info("-------");
        //执行定时任务 任务 延迟启动时间 时间间隔 时间单位
        group.next().scheduleAtFixedRate(()->{
            log.info("ok");
        },1,1, TimeUnit.SECONDS);

        //普通任务 定时任务
//        NioEventLoopGroup group = new DefaultEventLoopGroup();

    }
}
