package org.carl.scheduler;

import java.time.LocalTime;
import java.util.concurrent.*;
//TODO:实现时间轮进行任务调度
public class ScheduledTask {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        // 设置任务执行时间（每天的8点）
        LocalTime executionTime = LocalTime.of(8, 0);

        // 执行定时任务
        scheduleTask(
                executionTime,
                () -> {
                    // 这里写定时任务的具体逻辑
                    System.out.println("定时任务执行了，当前时间：" + LocalTime.now());

                    // 模拟任务执行失败的情况
                    if (Math.random() < 0.5) {
                        throw new RuntimeException("任务执行失败");
                    }
                },
                3); // 设置重试次数为3次
    }

    private static void scheduleTask(LocalTime executionTime, Runnable task, int maxRetries) {
        long initialDelay = calculateInitialDelay(executionTime);

        // 通过ScheduledExecutorService执行定时任务
        ScheduledFuture<?> future =
                scheduler.scheduleWithFixedDelay(
                        () -> {
                            try {
                                task.run();
                                // 如果任务执行成功，则取消重试
                                future.cancel(false);
                            } catch (Exception e) {
                                if (maxRetries > 0) {
                                    // 如果任务执行失败且还有重试次数，则重试
                                    System.out.println("任务执行失败，重试中...");
                                    maxRetries--;
                                } else {
                                    // 如果重试次数用尽，则执行失败回调
                                    System.out.println("任务执行失败，重试次数已用尽，执行失败回调");
                                    // 这里可以添加失败回调的具体逻辑，例如发送通知等

                                    // 取消任务的继续重试
                                    future.cancel(false);
                                }
                            }
                        },
                        initialDelay,
                        1,
                        TimeUnit.DAYS);
    }

    private static long calculateInitialDelay(LocalTime executionTime) {
        // 计算当前时间与下一个执行时间的时间差
        long currentTime = LocalTime.now().toSecondOfDay();
        long targetTime = executionTime.toSecondOfDay();
        long initialDelay = targetTime - currentTime;
        if (initialDelay < 0) {
            // 如果当前时间已经超过了执行时间，则取下一个执行时间的时间差（即一天后的执行时间）
            initialDelay += TimeUnit.DAYS.toSeconds(1);
        }
        return initialDelay;
    }
}
