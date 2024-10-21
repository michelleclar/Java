package org.carl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class CaffeineTool {
    public static Cache<String,String> localCache;
    static {
         localCache = Caffeine.newBuilder()
                //cache的初始容量
                .initialCapacity(5)
                //cache最大缓存数
                .maximumSize(10)
                //设置写缓存后n秒钟过期
                .expireAfterWrite(17, TimeUnit.SECONDS)
                //设置读写缓存后n秒钟过期
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();

    }
    public static String get(String key){
     return localCache.get(key,CaffeineTool::getValueFromDB);
    }
    static String getValueFromDB(String key) {
        return "v";
    }

    public void remove(String key){
        localCache.invalidate(key);
    }
}
