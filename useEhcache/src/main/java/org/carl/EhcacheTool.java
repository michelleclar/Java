package org.carl;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class EhcacheTool {
  static CacheManager localCacheManage;
  static Cache<String,String> localCache;
  static {
    localCacheManage = CacheManagerBuilder.newCacheManagerBuilder()
        .withCache("encacheInstance", CacheConfigurationBuilder
            // 声明一个容量为20的堆内缓存
            .newCacheConfigurationBuilder(String.class, String.class,
                ResourcePoolsBuilder.heap(20)))
        .build(true);

    localCache = localCacheManage.getCache("encacheInstance", String.class, String.class);
    localCache.put("key","v");
  }

  public static String get(String key) {
    return localCache.get(key);
  }
}
