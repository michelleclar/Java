package org.carl.netty.future;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.*;
@Slf4j
public class JdkFuture{
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 1.线程池
    ExecutorService service = Executors.newFixedThreadPool(2);

    Future<Integer> future = service.submit(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        log.info("do....");
        Thread.sleep(1000);
        return 50;
      }
    });
    log.info("wait result");
    log.info("result {}",future.get());
  }
}
