package org.carl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.carl.config.JedisPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisTool {
  static JedisPool jedisPool;
  static final Logger logger = LoggerFactory.getLogger(RedisTool.class);
  static {

    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    List<Jedis> minIdleJedisList = new ArrayList<Jedis>(jedisPoolConfig.getMinIdle());
    String redisHost = "localhost";
    int port = 16379;
    jedisPool = new JedisPool(jedisPoolConfig, redisHost, port);

    for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
      Jedis jedis = null;
      try {
        jedis = jedisPool.getResource();
        minIdleJedisList.add(jedis);
        jedis.ping();
      } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      } finally {
      }
    }
    minIdleJedisList.forEach(Jedis::close);
  }

  public static String get(String key) {
    return executor(j -> {
      return j.get(key);
    }).orElseGet(() -> {
      // Cache miss,get value from db
      return "v";
    });
  }

  public static <T> Optional<T> executor(Function<Jedis, T> function) {
    try (Jedis j = jedisPool.getResource()) {
      return Optional.ofNullable(function.apply(j));
    } catch (JedisConnectionException e) {
      logger.error("Redis connection error: {}", e.getMessage(), e);
    } catch (Exception e) {
      logger.error("Unexpected error: {}", e.getMessage(), e);
    }
    return Optional.empty();
  }

  public static void put(String k, String v) {
    executor(j -> {
      return j.set(k, v);
    }).ifPresentOrElse(_v -> {
      // log successful set cache
      logger.info("Cache set success for key: {}", k);
    }, () -> {
      // faild to cache , can throw error
      logger.error("Failed to cache key: {}", k);
    });;

  }

  public static void remove(String k) {
    executor(j -> {
      return j.del(k);
    }).ifPresentOrElse(v -> {
      if (v == 1) {
        logger.info("Successfully remove for key: {}", k);
      } else {
        logger.warn("key is remove: {}", k);
      }
    }, () -> {
      // not execute ,maby redis die
      logger.error("Failed to remove for key: {}", k);
    });;
  }

  public static void expire(String k, int seconds) {
    executor(j -> j.expire(k, seconds)).ifPresentOrElse(v -> {
      if (v == 1) {
        logger.info("Successfully set expiration for key: {} to {} seconds", k, seconds);
      } else {
        logger.warn("Failed to set expiration for key: {}", k);
      }
    }, () -> logger.error("Failed to execute expire operation for key: {}", k));
  }
}
