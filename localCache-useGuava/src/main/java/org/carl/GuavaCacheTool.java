package org.carl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCacheTool {
    static Cache<String, String> cache;

    static {
        cache = CacheBuilder.newBuilder()
                //cache的初始容量
                .initialCapacity(5)
                //cache最大缓存数
                .maximumSize(10)
                //设置写缓存后n秒钟过期
                .expireAfterWrite(17, TimeUnit.SECONDS)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();

    }

    public static String get(String key) {
        String value = null;
        try {
            value = cache.get(key, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return getValueFromDB(key);
                }
            });
        } catch (ExecutionException e) {
            System.out.println("not fount key");
        }
        return value;
    }

    static String getValueFromDB(String key) {
        return "value";
    }

    public void remove(String key) {
        cache.invalidate(key);
    }


}
