package org.carl.redis;

import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.string.StringCommands;
import io.smallrye.mutiny.Uni;
import io.vertx.redis.client.impl.RedisClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class IncrementService {

  private ReactiveKeyCommands<String> keys;
  private final StringCommands<String, Integer> counter;

  public IncrementService(RedisDataSource redisDS, ReactiveRedisDataSource reactiveRedisDS) {
    keys = reactiveRedisDS.key();
    counter = redisDS.string(Integer.class);
  }

  Uni<Void> del(String key) {
    return keys.del(key).replaceWithVoid();
  }

  int get(String key) {
    return counter.get(key);
  }

  void set(String key, int value) {
    counter.set(key, value);
  }

  void increment(String key, int incrementBy) {
    counter.incrby(key, incrementBy);
  }

  Uni<List<String>> keys() {
    return keys.keys("*");
  }
}
